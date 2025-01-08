package com.piisw.kino.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.piisw.kino.service.BookSerivce;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TicketTO {

    @Data
    public static class TicketBookingRequest {

        // Custom validator in serivce
        @NotNull(message = "No airing id")
        private Long airingId;

        @NotBlank(message = "No first name")
        private String firstName;

        @NotBlank(message = "No last name")
        private String lastName;

        @NotBlank(message = "No phone number")
        @Length()
        private String phoneNumber;

        // Custom validator in serivce
        @NotEmpty
        @Size(min = 1, max = BookSerivce.MAX_NUMBER_OF_SEAT_RESERVATIONS)
        private List<Integer> seats;
    }

    @Data
    public static class TicketBookingResponse {
        
        @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
        private String code;
    }

    @Data
    public static class TicketResponse {
        
        @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
        private Long id;
        
        @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
        private String code;
        
        @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
        private MovieShortTO movie;
        
        @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
        private LocalDateTime screeningDate;
        
        @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
        private List<Integer> seats;
        
        @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
        private Integer hall;
        
        @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
        private Integer status;
        
        @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
        private AiringTO airing;
    }
}
