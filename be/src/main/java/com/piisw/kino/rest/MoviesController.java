package com.piisw.kino.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.piisw.kino.dto.MovieTO;
import com.piisw.kino.service.MoviesService;


import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MoviesController {

    private final MoviesService moviesService;

    @GetMapping("/movies/")
    public List<MovieTO> findAll() {
        return moviesService.findAll().stream()
                .limit(3)
                .collect(Collectors.toList());
        ;
    }

    @GetMapping("/movies/{id}")
    public MovieTO findById(@PathVariable Long id) {
        return moviesService.findById(id);
    }
}
