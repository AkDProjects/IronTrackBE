package com.IronTrack.IronTrackBE.Controllers;

import com.IronTrack.IronTrackBE.Models.TokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {
    @GetMapping
    public ResponseEntity<TokenResponse> checkToken() {
        TokenResponse response = new TokenResponse();
        response.setAuthenticated(true);
        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    };
}
