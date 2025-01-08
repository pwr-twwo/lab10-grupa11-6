package com.piisw.kino.persistence.enums;

import lombok.Getter;

@Getter
public enum CinemaHall {
    SMALL   (30),
    MEDIUM  (40),
    BIG     (70);

    private final int size;
    private CinemaHall(int size) { this.size = size; }
}
