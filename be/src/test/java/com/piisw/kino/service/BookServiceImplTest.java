package com.piisw.kino.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.piisw.kino.dto.TicketTO.TicketBookingRequest;
import com.piisw.kino.dto.TicketTO.TicketBookingResponse;
import com.piisw.kino.persistence.repositories.TicketRepository;
import com.piisw.kino.rest.exceptions.DataValidationException;
import com.piisw.kino.rest.exceptions.EntityNotFoundException;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class BookServiceImplTest {
    /*  
     * Wiem, że na chama wrzucone wartości tutaj to słaby pomysł na testowanie (ale nie wiem jak to zrobić poprawniej),
     * dlatego pomyślałem, że wytłumacze czemu w ten sposób a nie inny.
     * Dla:
     * - airing_d % 3 == 1
     *   hala mała (czyli 30 miejsc), wszystkie miejsca zostały zajęte (od 0 do 29)
     *   !!! wyjątek dla 13 !!! tam brakuje 29tki
     *
     * 
     * - airing_d % 3 == 2
     *   hala średnia (czyli 40 miejsc). pierwsze 14 miejsc zostało zajęte (od 0 do 13; od 14 do 39 jest wolne)
     * 
     * - airing_d % 3 == 3
     *   hala duża (czyli 70 miejsc), pierwsze 7 miejsc zostało zajęte (od 0 do 6; od 7 do 60 (nice) jest wolne)
     *
     * 
     * Te wartości sie nie zmienią raczej w tym projekcie. Jeśli jednak sie zmienią to prosze się upewnić w
     * {@link com.piisw.kino.persistence.enums.CinemaHall}
     */

    @Autowired
    private BookSerivce bookSerivce;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private MoviesService moviesService;

    private static TicketBookingRequest getTicketBookingRequest(Number airingId, List<Integer> seats) {
        TicketBookingRequest request = new TicketBookingRequest();
        request.setAiringId(airingId.longValue());
        request.setFirstName("test");
        request.setLastName("czy to wazne");
        request.setPhoneNumber("123456789");
        request.setSeats(seats);
        return request;
    }

    @Test
    public void testSeatTakenDataValidation() {
        // Given
        List<Integer> seatsToBeTaken = new ArrayList<>(Arrays.asList(1, 2, 3));
        TicketBookingRequest request = BookServiceImplTest.getTicketBookingRequest(1, seatsToBeTaken);

        // When

        // Then
        assertThrows(DataValidationException.class, () -> bookSerivce.bookTicket(request));
    }

    @Test
    public void testInvalidSeatDataValidation() {
        // Given
        List<Integer> seatsToBeTakenNegative = new ArrayList<>(Arrays.asList(-3));
        TicketBookingRequest requestNegative = BookServiceImplTest.getTicketBookingRequest(3, seatsToBeTakenNegative);
        // And
        List<Integer> seatsToBeTakenPositive = new ArrayList<>(Arrays.asList(99));
        TicketBookingRequest requestPositive = BookServiceImplTest.getTicketBookingRequest(3, seatsToBeTakenPositive);

        // When

        // Then
        assertThrows(DataValidationException.class, () -> bookSerivce.bookTicket(requestNegative));
        assertThrows(DataValidationException.class, () -> bookSerivce.bookTicket(requestPositive));
    }

    @Test
    public void testHallSizeValidation() {
        // Given
        List<Integer> seatsToBeTaken = new ArrayList<>(Arrays.asList(40));
        TicketBookingRequest request = BookServiceImplTest.getTicketBookingRequest(1, seatsToBeTaken);

        // Then
        assertThrows(DataValidationException.class, () -> bookSerivce.bookTicket(request));
    }

    @Test
    public void testAiringExists() {
        // Given
        List<Integer> seatsToBeTaken = new ArrayList<>(Arrays.asList(40));
        TicketBookingRequest request = BookServiceImplTest.getTicketBookingRequest(789, seatsToBeTaken);

        // Then
        assertThrows(EntityNotFoundException.class, () -> bookSerivce.bookTicket(request));
    }

    @Test
    public void testTooManyTakenSeats() {
        // Given
        List<Integer> seatsToBeTaken = new ArrayList<Integer>();
        for(int i = 0; i <= BookSerivce.MAX_NUMBER_OF_SEAT_RESERVATIONS; i++)
            seatsToBeTaken.add(i + 10);
        TicketBookingRequest request = BookServiceImplTest.getTicketBookingRequest(3, seatsToBeTaken);
        
        // Then
        assertThrows(DataValidationException.class, () -> bookSerivce.bookTicket(request));
    }

    @Test
    public void testEnoughSeats() {
        // Given
        List<Integer> seatsToBeTaken = new ArrayList<>(Arrays.asList(29, 28));
        TicketBookingRequest request = BookServiceImplTest.getTicketBookingRequest(13, seatsToBeTaken);
        
        // Then
        assertThrows(DataValidationException.class, () -> bookSerivce.bookTicket(request));
    }

    @Test
    public void testCodeGeneration() {
        var seats = Arrays.asList(30, 31, 32);
        List<Integer> seatsToBeTaken = new ArrayList<>(seats);
        TicketBookingRequest request = BookServiceImplTest.getTicketBookingRequest(3, seatsToBeTaken);

        // When

        // Then
        var moviePre = moviesService.findById(1L);
        var airingPre = moviePre.getAirings().get(2);
        assertFalse(airingPre.getSeatsTaken().containsAll(seats));

        var ticket = bookSerivce.bookTicket(request);
        assertInstanceOf(TicketBookingResponse.class, ticket);
        assertNotNull(ticket.getCode());
        assertTrue(ticketRepository.findByCode(ticket.getCode()).isPresent());
        
        var moviePost = moviesService.findById(1L);
        var airingPost = moviePost.getAirings().get(2);
        assertTrue(airingPost.getSeatsTaken().containsAll(seats));

    }
}
