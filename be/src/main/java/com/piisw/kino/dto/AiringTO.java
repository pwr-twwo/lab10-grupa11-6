package com.piisw.kino.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AiringTO implements Serializable {

    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime time;

    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer type;

    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Integer> seatsTaken = new ArrayList<>();
}
