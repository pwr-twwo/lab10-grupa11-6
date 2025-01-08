package com.piisw.kino.mapper;

import java.util.ArrayList;
import java.util.List;

import com.piisw.kino.dto.AiringTO;
import com.piisw.kino.persistence.entities.AiringEntity;
import com.piisw.kino.persistence.entities.TicketEntity;

public final class AiringMapper {
    public static List<AiringTO> mapToTOs(List<AiringEntity> airingEntities) {
        var TOs = new ArrayList<AiringTO>();

        for(AiringEntity airingEntity : airingEntities)
            TOs.add(AiringMapper.mapToTO(airingEntity));

        return TOs;
    }

    public static AiringTO mapToTO(AiringEntity airingEntity) {
        if(airingEntity == null)
            return null;

        AiringTO airingTO = new AiringTO();
        
        airingTO.setId(airingEntity.getId());
        airingTO.setTime(airingEntity.getTime());
        airingTO.setType(airingEntity.getType().ordinal());

        final List<Integer> seatsTaken = new ArrayList<>();
        for(TicketEntity t : airingEntity.getTickets())
            seatsTaken.addAll(t.getSeatsTaken());

        airingTO.setSeatsTaken(seatsTaken);

        return airingTO;
    }
}
