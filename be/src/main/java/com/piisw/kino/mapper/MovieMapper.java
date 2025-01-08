package com.piisw.kino.mapper;

import java.util.ArrayList;
import java.util.List;

import com.piisw.kino.dto.AiringTO;
import com.piisw.kino.dto.MovieShortTO;
import com.piisw.kino.dto.MovieTO;
import com.piisw.kino.persistence.entities.MovieEntity;

public final class MovieMapper {

    public static List<MovieTO> mapToTOs(final List<MovieEntity> movieEntities) {
        var TOs = new ArrayList<MovieTO>();

        for (MovieEntity e : movieEntities)
            TOs.add(MovieMapper.mapToTO(e));
        return TOs;
    }

    public static MovieTO mapToTO(final MovieEntity movieEntity) {
        if(movieEntity == null)
            return null;
        
        final MovieTO movieTO = new MovieTO();
        movieTO.setId(movieEntity.getId());
        movieTO.setTitle(movieEntity.getTitle());
        movieTO.setDescription(movieEntity.getDescription());
        movieTO.setLength(movieEntity.getLength());
        movieTO.setDirector(movieEntity.getDirector());
        movieTO.setYear(movieEntity.getYear());
        movieTO.setCategories(movieEntity.getCategories());
        movieTO.setImgMain(movieEntity.getImgMain());

        final List<AiringTO> airingsTO = AiringMapper.mapToTOs(movieEntity.getAirings());
        movieTO.setAirings(airingsTO);

        return movieTO;
    }

    public static MovieShortTO mapToShortTO(final MovieEntity movieEntity) {
        if(movieEntity == null)
            return null;
        
        final MovieShortTO movieShortTO = new MovieShortTO();
        movieShortTO.setId(movieEntity.getId());
        movieShortTO.setTitle(movieEntity.getTitle());
        movieShortTO.setDescription(movieEntity.getDescription());
        movieShortTO.setLength(movieEntity.getLength());
        movieShortTO.setDirector(movieEntity.getDirector());
        movieShortTO.setYear(movieEntity.getYear());

        return movieShortTO;
    }
}
