package com.piisw.kino.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MovieTO implements Serializable {
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

    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> categories;

    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private String imgMain;

    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private List<AiringTO> airings = new ArrayList<>();
}
