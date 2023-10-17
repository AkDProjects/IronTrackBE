package com.IronTrack.IronTrackBE.Controllers;


import com.IronTrack.IronTrackBE.Models.AuthenticationResponse;
import com.IronTrack.IronTrackBE.Models.LoginRequest;
import com.IronTrack.IronTrackBE.Models.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.IronTrack.IronTrackBE.Services.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AccessController {

    private final AuthService service;
    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @RequestBody SignupRequest request
    ) {
        try {
            AuthenticationResponse response = service.signup(request);
            return ResponseEntity.ok(response);
        } catch (DataIntegrityViolationException e) {
            ErrorResponse response = new ErrorResponse();
            response.setMessage(e.getMessage());
            response.setStatusCode(HttpStatus.CONFLICT.value());
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request
    ) {
        try {
            AuthenticationResponse response = service.login(request);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            ErrorResponse response = new ErrorResponse();
            response.setMessage(e.getMessage());
            response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
    }
}