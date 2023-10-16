package com.IronTrack.IronTrackBE.Controllers;


import com.IronTrack.IronTrackBE.Models.AuthenticationResponse;
import com.IronTrack.IronTrackBE.Models.LoginRequest;
import com.IronTrack.IronTrackBE.Models.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AuthenticationResponse> signup(
            @RequestBody SignupRequest request
    ) {
        return ResponseEntity.ok(service.signup(request));
    };

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> signup(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(service.login(request));
    }
}