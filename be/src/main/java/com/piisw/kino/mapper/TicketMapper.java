package com.piisw.kino.mapper;

import com.piisw.kino.dto.TicketTO.TicketBookingResponse;
import com.piisw.kino.dto.TicketTO.TicketResponse;
import com.piisw.kino.dto.TicketTO.TicketBookingRequest;
import com.piisw.kino.persistence.entities.AiringEntity;
import com.piisw.kino.persistence.entities.TicketEntity;

public final class TicketMapper {

    public static TicketEntity mapToObj(
        final TicketBookingRequest booking,
        final AiringEntity airingEntity,
        final String code
    ) {
        final var ticketEntity = new TicketEntity();
        ticketEntity.setAiring(airingEntity);
        ticketEntity.setCode(code);
        ticketEntity.setFirstName(booking.getFirstName());
        ticketEntity.setLastName(booking.getLastName());
        ticketEntity.setPhoneNumber(booking.getPhoneNumber());
        ticketEntity.setSeatsTaken(booking.getSeats());
        // Status domyślnie na VALID

        return ticketEntity;
    }

    public static TicketBookingResponse mapToTO(TicketEntity ticketEntity) {
        final var ticketTO = new TicketBookingResponse();
        ticketTO.setCode(ticketEntity.getCode());

        return ticketTO;
    }

    public static TicketResponse mapToResponseTO(TicketEntity ticketEntity){
        final var ticketTO = new TicketResponse();
        var ticketAiringEntity = ticketEntity.getAiring();
        var ticketMovieShortTO = MovieMapper.mapToShortTO(ticketAiringEntity.getMovie());
        var ticketAiringTO = AiringMapper.mapToTO(ticketEntity.getAiring());

        ticketTO.setId(ticketEntity.getId());
        ticketTO.setCode(ticketEntity.getCode());
        ticketTO.setMovie(ticketMovieShortTO);
        ticketTO.setScreeningDate(ticketAiringEntity.getTime());
        ticketTO.setSeats(ticketEntity.getSeatsTaken());
        ticketTO.setAiring(ticketAiringTO);
        ticketTO.setHall(ticketAiringEntity.getType().ordinal());
        ticketTO.setStatus(ticketEntity.getStatus().ordinal());

        return ticketTO;
    }
}
