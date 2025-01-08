package com.piisw.kino.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.piisw.kino.dto.TicketTO.TicketBookingRequest;
import com.piisw.kino.dto.TicketTO.TicketBookingResponse;
import com.piisw.kino.mapper.TicketMapper;
import com.piisw.kino.persistence.entities.AiringEntity;
import com.piisw.kino.persistence.entities.TicketEntity;
import com.piisw.kino.persistence.repositories.AiringRepository;
import com.piisw.kino.persistence.repositories.TicketRepository;
import com.piisw.kino.rest.exceptions.DataValidationException;
import com.piisw.kino.rest.exceptions.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookSerivce {
    public static final int MAX_NUMBER_OF_SEAT_RESERVATIONS = 8;

    public static List<Integer> getTakenSeats(AiringEntity airingEntity) {
        Set<Integer> seatsTaken = new HashSet<Integer>();
        for(TicketEntity t : airingEntity.getTickets())
            seatsTaken.addAll(t.getSeatsTaken());
        return new ArrayList<Integer>(seatsTaken);
    }
    
    private final TicketRepository ticketRepository;
    private final AiringRepository airingRepository;

    public TicketBookingResponse bookTicket(final TicketBookingRequest bookInfo) {
        // Validatory dla logiki biznesowej:
        // Czy AiringEntity istnieje?
        AiringEntity airingEntity = airingRepository
            .findById(bookInfo.getAiringId())
            .orElseThrow(() -> new EntityNotFoundException(bookInfo.getAiringId(), AiringEntity.class));

        // Czy ilość miejsc jest ok?
        if(bookInfo.getSeats().size() > MAX_NUMBER_OF_SEAT_RESERVATIONS)
            throw new DataValidationException("Too many taken seats");

        // Czy wszystkie miejsca są wolne?
        // Czy jakiekolwiek z miejsc wychodzi poza dostępne?
        var seats = getTakenSeats(airingEntity);
        for(Integer seatToBeTaken : bookInfo.getSeats()) {
            if(seats.contains(seatToBeTaken))
                throw new DataValidationException("Seat " + seatToBeTaken + " already taken");
            if(seatToBeTaken >= airingEntity.getType().getSize() || seatToBeTaken < 0)
                throw new DataValidationException("Seat " + seatToBeTaken + " is invalid");
        }

        // Wygląda na to że ten test jest redundantny. Ale zostawie go dla przyszłych pokoleń.
        // // Czy typ hali pozwala na tyle miejsc?
        // if(seats.size() + bookInfo.getSeats().size() > airingEntity.getType().getSize())
        //     throw new DataValidationException("Not enough seats");

        // Wszystko jest git, generujemy kod:
        // uzywanie uuid jako kodu (irl byśmy jeszcze tam index przywalili na tabelke) to ponoć zły pomysł ¯\_(ツ)_/¯
        // sauce: https://www.cybertec-postgresql.com/en/unexpected-downsides-of-uuid-keys-in-postgresql/
        String code = UUID.randomUUID().toString();
        TicketEntity ticketEntity = TicketMapper.mapToObj(bookInfo, airingEntity, code);
        TicketEntity obj = ticketRepository.save(ticketEntity);

        airingEntity.getTickets().add(obj);
        airingRepository.save(airingEntity);
        
        return TicketMapper.mapToTO(obj);
    }
}
