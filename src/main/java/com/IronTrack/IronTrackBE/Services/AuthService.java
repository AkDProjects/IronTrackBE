package com.IronTrack.IronTrackBE.Services;

import com.IronTrack.IronTrackBE.Models.AuthenticationResponse;
import com.IronTrack.IronTrackBE.Models.LoginRequest;
import com.IronTrack.IronTrackBE.Models.SignupRequest;
import com.IronTrack.IronTrackBE.Repository.Entities.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.IronTrack.IronTrackBE.Repository.UserRepo;
import com.IronTrack.IronTrackBE.Repository.Entities.UserEntity;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse signup(SignupRequest request) {

        try {
            var user = UserEntity.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
            userRepo.save(user);
            var jwtToken = jwtService.generateToken(user);

            return AuthenticationResponse.builder().token(jwtToken).build();
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Email address is already registered");
        }
    }

    public AuthenticationResponse login(LoginRequest request) {

        var user = userRepo.findByEmail(request.getEmail()).orElseThrow(() -> new BadCredentialsException("Invalid Credentials. Please try again"));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } else {
            throw new BadCredentialsException("Invalid Credentials. Please try again");
        }
    }
}