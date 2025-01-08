package com.piisw.kino.service;

import static org.junit.jupiter.api.Assertions.*;

import com.piisw.kino.dto.UserTO.RegisterUserTO;
import com.piisw.kino.dto.UserTO.LoginUserTO;
import com.piisw.kino.persistence.repositories.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class AuthenticationServiceTest {
    // Szczerze na ten moment nie mam pomysłu na testy do tego

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenSignUp_thenSuccess() {
        // Given
        RegisterUserTO mockRegisterUserTO = new RegisterUserTO();
        mockRegisterUserTO.setEmail("user@email.com");
        mockRegisterUserTO.setPassword("password");
        // When
        // Then
        assertNotNull(authenticationService.signup(mockRegisterUserTO));
        assertTrue(userRepository.findByEmail("user@email.com").isPresent());
    }

    @Test
    public void whenAuthenticate_thenSuccess() {
        // Given
        RegisterUserTO mockRegisterUserTO = new RegisterUserTO();
        mockRegisterUserTO.setEmail("user12@email.com");
        mockRegisterUserTO.setPassword("password");
        
        LoginUserTO user = new LoginUserTO();
        user.setEmail("user12@email.com");
        user.setPassword("password");

        // When
        authenticationService.signup(mockRegisterUserTO);

        // Then
        assertNotNull(authenticationService.authenticate(user));
    }

}
