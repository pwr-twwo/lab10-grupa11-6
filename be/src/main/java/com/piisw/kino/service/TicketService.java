package com.piisw.kino.service;

import com.piisw.kino.dto.TicketTO.TicketResponse;
import com.piisw.kino.mapper.TicketMapper;
import com.piisw.kino.persistence.entities.TicketEntity;
import com.piisw.kino.persistence.enums.TicketStatus;
import com.piisw.kino.persistence.repositories.TicketRepository;
import com.piisw.kino.rest.exceptions.CodeEntityNotFoundException;
import com.piisw.kino.rest.exceptions.InvalidTicketException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public TicketResponse findTicketByCode(String code) {
        Optional<TicketEntity> ticketEntity = ticketRepository.findByCode(code);

        return ticketEntity
                .map((e) -> TicketMapper.mapToResponseTO(e))
                .orElseThrow(() -> new CodeEntityNotFoundException(code));
    }

    public void punchTicket(String code) {
        // znajdz bilet; jak nie ma to 404
        TicketEntity ticketEntity = ticketRepository.findByCode(code)
            .orElseThrow(() -> new CodeEntityNotFoundException(code));

        // jesli bilet juz jest zuzyty to 400;
        // jesli nie jest zuzyty, ale czas jest invalid, to tez 400
        if(!isTicketValid(ticketEntity))
            throw new InvalidTicketException();

        ticketEntity.setStatus(TicketStatus.PUNCHED);
        ticketRepository.save(ticketEntity);
    }

    public boolean isTicketValid(TicketEntity ticketEntity) {
        if(ticketEntity.getStatus() != TicketStatus.VALID)
            return false;
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime screeningTime = ticketEntity.getAiring().getTime();
        Integer duration = ticketEntity.getAiring().getMovie().getLength();

        LocalDateTime startValidityPeriod = screeningTime.minusMinutes(15);
        LocalDateTime endValidityPeriod = screeningTime.plusSeconds(duration);

        return currentTime.isAfter(startValidityPeriod) && currentTime.isBefore(endValidityPeriod);
    }
}

