package com.IronTrack.IronTrackBE.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {
    @GetMapping
    public ResponseEntity<String> checkToken() {
        // Either respond with 200 or 403
        return ResponseEntity.ok("Success!");
    };
}
