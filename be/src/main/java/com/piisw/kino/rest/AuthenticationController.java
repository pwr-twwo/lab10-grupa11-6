package com.piisw.kino.rest;

import com.piisw.kino.dto.UserTO.LoginResponseTO;
import com.piisw.kino.dto.UserTO.LoginUserTO;
import com.piisw.kino.persistence.entities.UserEntity;
import com.piisw.kino.service.AuthenticationService;
import com.piisw.kino.service.DataValidationService;
//import com.piisw.kino.service.JwtService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    //private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final DataValidationService validator;

    /*
     Nie wiem czy chcemy na razie sie móc rejestrować. Nie taki był plan ogólnie xD.
     Do rejestracji byśmy potrzebowali przynajmniej wysyłania mejli i innych jakichś pierdół;

     Generalnie to _to działa_, ale ¯\_(ツ)_/¯
  
     @PostMapping("/signup")
     public ResponseEntity<UserEntity> register(@RequestBody RegisterUserTO registerUserDto) {
         UserEntity registeredUser = authenticationService.signup(registerUserDto)';'
         return ResponseEntity.ok(registeredUser);
     }
     */

    /* 
    @PostMapping("/login")
    public LoginResponseTO authenticate(@RequestBody LoginUserTO loginUserDto) {
        validator.throwOnInvalidData(loginUserDto);

        UserEntity authenticatedUser = authenticationService.authenticate(loginUserDto);

        LoginResponseTO loginResponse = new LoginResponseTO();
        loginResponse.setToken(jwtService.generateToken(authenticatedUser));
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setUsername(authenticatedUser.getUsername());

        return loginResponse;
    }
    */
    
    @GetMapping("/asd")
    public String testAuth() {
        return "You have to be logged in!";
    }
}
