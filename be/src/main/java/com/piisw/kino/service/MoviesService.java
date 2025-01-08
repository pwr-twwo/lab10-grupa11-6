package com.piisw.kino.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.piisw.kino.dto.MovieTO;
import com.piisw.kino.mapper.MovieMapper;
import com.piisw.kino.persistence.entities.MovieEntity;
import com.piisw.kino.persistence.repositories.MovieRepository;
import com.piisw.kino.rest.exceptions.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MoviesService {

    private final MovieRepository moviesRepository;

    public MovieTO findById(Long id) {
        final Optional<MovieEntity> movieEntity = moviesRepository.findById(id);
        return movieEntity
            .map((e) -> MovieMapper.mapToTO(e))
            .orElseThrow(() -> new EntityNotFoundException(id, MovieEntity.class));
    }

    public List<MovieTO> findAll() {
        final List<MovieEntity> movieEntities = moviesRepository.findAll();
        final List<MovieTO> movieTOs = MovieMapper.mapToTOs(movieEntities);
        return movieTOs;
    }



}
