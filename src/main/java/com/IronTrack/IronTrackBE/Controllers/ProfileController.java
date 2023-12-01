package com.IronTrack.IronTrackBE.Controllers;

import com.IronTrack.IronTrackBE.Models.User;
import com.IronTrack.IronTrackBE.Services.ProfileService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
@RestController
public class ProfileController {

    @Value("${SECRET_KEY}")
    private String SECRET_KEY;
@Autowired
ProfileService profileService;
    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfileInfo(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);

        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();

        Map<String, String> response = new HashMap<>();
        response.put("username", username);

    return ResponseEntity.ok().body(profileService.findbyEmail(username));
    }

    @PutMapping("/profile/edit")
    public void editUserProfileInfo(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);

        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();

        Map<String, String> response = new HashMap<>();
        response.put("username", username);


    }

    @DeleteMapping("/profile/delete")
    public void deleteUserById(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);

        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();

        Map<String, String> response = new HashMap<>();
        response.put("username", username);
        profileService.deleteByEmail(username);
    }
}
