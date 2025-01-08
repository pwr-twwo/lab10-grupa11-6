package com.piisw.kino.service;

import com.piisw.kino.dto.UserTO.LoginUserTO;
import com.piisw.kino.dto.UserTO.RegisterUserTO;
import com.piisw.kino.persistence.entities.UserEntity;
import com.piisw.kino.persistence.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public UserEntity signup(RegisterUserTO input) {
        UserEntity user = new UserEntity();

        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        return userRepository.save(user);
    }

    public UserEntity authenticate(LoginUserTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        try {
//            Optional<UserEntity> user = userRepository.findByEmail(email);
//            if (user.isEmpty()) {
//                throw new UsernameNotFoundException("No user found with username: " + email);
//            }
//            return new User(user.get().getEmail(),
//
//                    user.get().getPassword(), user.get().isEnabled(), user.get().isAccountNonExpired(),
//                    user.get().isCredentialsNonExpired(), user.get().isAccountNonLocked(), user.get().getAuthorities());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//
//        }
//    }
}
