package com.piisw.kino.persistence.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String title;

    @Column(length = 2500)
    private String description;

    @Column
    private Integer length;

    @Column
    private String director;

    @Column(name = "premiere_year")
    private Integer year;

    @Column
    private List<String> categories = new ArrayList<String>();

    @Column
    private String imgMain;

    @OneToMany
    private List<AiringEntity> airings;
}
