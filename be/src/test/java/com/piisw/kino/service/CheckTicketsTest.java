package com.piisw.kino.service;


import com.piisw.kino.dto.TicketTO;
import com.piisw.kino.persistence.entities.AiringEntity;
import com.piisw.kino.persistence.entities.MovieEntity;
import com.piisw.kino.persistence.entities.TicketEntity;
import com.piisw.kino.persistence.enums.TicketStatus;
import com.piisw.kino.persistence.repositories.TicketRepository;
import com.piisw.kino.rest.exceptions.CodeEntityNotFoundException;
import com.piisw.kino.rest.exceptions.InvalidTicketException;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CheckTicketsTest {
    public final static int EXAMPLE_MOVIE_DURATION = 1200;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketRepository ticketRepository;

    private TicketEntity getTicketEntity(LocalDateTime airingTime, Integer movieDuration){
        MovieEntity movie = new MovieEntity();
        movie.setLength(movieDuration);

        AiringEntity airing = new AiringEntity();
        airing.setTime(airingTime);
        airing.setMovie(movie);

        TicketEntity ticket = new TicketEntity();
        ticket.setStatus(TicketStatus.VALID);
        ticket.setAiring(airing);
        return ticket;
    }


    @Test
    public void findTicketByCodeTest(){
        // wyszukiwanie ticketu po kodzie - nie istnieje - wyrzuca błęda
        // wyszukiwanie ticketu po kodzie - jest w bazie - zwracamy obiekt TicketResponse

        var existingTicketCode = "BK93UK82HX";
        var notExistingTicketCode = "bilet2137";

        var ticketExists = ticketService.findTicketByCode(existingTicketCode);

        assertDoesNotThrow(() -> ticketService.findTicketByCode(existingTicketCode));
        assertThrows(
            CodeEntityNotFoundException.class, 
            () -> ticketService.findTicketByCode(notExistingTicketCode)
        );

        assertInstanceOf(TicketTO.TicketResponse.class, ticketExists);

    }

    public static Stream<Arguments> provideDataForValidityCheck() {
        /*
         * Nie da sie podróżować w czasie, więc musimy przesunąć nasz punkt odniesienia.
         * 
         * Plusem zaznaczono miejsca w których chcemy zbadać czy działa:
         * =========+O=====+=====O======+=======O+====>
         *           |           |              ^ koniec filmu
         *           |           ^  start
         *           ^ T-15min 
         * Aby dostać się do plusów musimy dodać/odjąć od airingTime jakąś część długości filmu.
         * 
         *   "O której musi się zacząć film, żeby być w którejś katgorii spoźnienia?"
         * 
         * Idziemy od lewej:
         *     1. teraz + 20 minut
         *     2. teraz + 5 minut
         *     3. teraz - 10 minut
         *     4. teraz - długość filmu (- 10, dla pewności)
         */

        var airingDate = LocalDateTime.now();

        // 1. W kinie za wcześnie (akurat żeby iść po popcorn)
        var tooEarly = airingDate.plusMinutes(20);

        // 2. W kinie akurat przed seansem (idealnie żeby iść na film)
        var onTime = airingDate.plusMinutes(5);

        // 3. W kinie spóźniony (tramwaj sie wywrocławił)
        var onTimeDuringMovie = airingDate.minusMinutes(10);

        // 4. W kinie za późno (ło baben cośty narobił)
        var late = airingDate.minusMinutes(EXAMPLE_MOVIE_DURATION + 1);

        // <czas startu filmu>, <jakie powinno byc>
        return Stream.of(
            Arguments.of(tooEarly,          false),
            Arguments.of(onTime,            true),
            Arguments.of(onTimeDuringMovie, true),
            Arguments.of(late,              false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForValidityCheck")
    public void testIsTicketValid(LocalDateTime airingTime, Boolean shouldBe) {
        var ticket = getTicketEntity(airingTime, EXAMPLE_MOVIE_DURATION);

        assertEquals(ticketService.isTicketValid(ticket), shouldBe);
    }


    @Test
    public void PunchTicketTest(){

        var notExistingTicketCode = "bilet2137";
        var ticketCodeInvalidDate = "BK93UK82HX";

        // wyniki z fakera, nic nie poradzę...
        var ticketCode1 = "TEST213769";
        var ticketCode2 = "TEST692137";

        // punchowanie ticketu - valid
        ticketService.punchTicket(ticketCode1);
        var ticketValidPunched = ticketRepository.findByCode(ticketCode1).get();

        assertThrows(CodeEntityNotFoundException.class, () -> ticketService.findTicketByCode(notExistingTicketCode));
        assertThrows(InvalidTicketException.class, () -> ticketService.punchTicket(ticketCodeInvalidDate));
        assertThrows(CodeEntityNotFoundException.class, () -> ticketService.punchTicket(notExistingTicketCode));
        assertThrows(InvalidTicketException.class, () -> ticketService.punchTicket(ticketCode2));

        assertTrue(ticketValidPunched.getStatus() == TicketStatus.PUNCHED);

    }

}
