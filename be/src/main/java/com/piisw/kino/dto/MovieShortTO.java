package com.piisw.kino.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MovieShortTO implements Serializable {
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer length;

    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private String director;

    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer year;
}
