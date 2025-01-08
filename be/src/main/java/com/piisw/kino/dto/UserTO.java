package com.piisw.kino.dto;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class UserTO {
    // W razie czego mimo tego że oba robią to samo, to zostawiam dwa.
    // Jakby sie coś kiedyś zmieniło, nie psułoby to innej logiki opartej na 
    // nie swoim DTO.
    @Data
    @Getter
    @Setter
    public static class RegisterUserTO {
        @Email(message = "Not a propper email")
        private String email;

        @NotBlank
        @Length(min = 8, max = 32, message = "Invalid password length")
        private String password;
    }

    @Data
    @Getter
    @Setter
    public static class LoginUserTO {
        @NotBlank
        private String email;

        @NotBlank
        private String password;
    }

    @Data
    @Getter
    @Setter
    public static class LoginResponseTO {
        @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
        private String token;

        @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
        private Long expiresIn;

        @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
        private String username;
    }
}
