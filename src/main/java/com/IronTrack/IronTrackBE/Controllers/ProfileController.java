package com.IronTrack.IronTrackBE.Controllers;

import com.IronTrack.IronTrackBE.Models.AuthenticationResponse;
import com.IronTrack.IronTrackBE.Models.User;
import com.IronTrack.IronTrackBE.Services.ProfileService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        getUsernameFromToken(authorizationHeader);
        String username = getUsernameFromToken(authorizationHeader);
        return ResponseEntity.ok().body(profileService.findbyEmail(username));
    }

    @PutMapping("/profile/editname")
    public void editName(@RequestHeader("Authorization") String authorizationHeader, @RequestBody User newUserName) {
        getUsernameFromToken(authorizationHeader);
        String username = getUsernameFromToken(authorizationHeader);
        profileService.editUserName(username, newUserName.getName());

    }

    @PutMapping("/profile/editemail")
    public AuthenticationResponse editEmail(@RequestHeader("Authorization") String authorizationHeader, @RequestBody User newEmail) {
        getUsernameFromToken(authorizationHeader);
        String username = getUsernameFromToken(authorizationHeader);
        return profileService.editEmail(username, newEmail.getEmail());


    }

    @PutMapping("/profile/editpassword")
    public void editPassword(@RequestHeader("Authorization") String authorizationHeader, @RequestBody User oldPassword, String newPassword) {
        getUsernameFromToken(authorizationHeader);
        String username = getUsernameFromToken(authorizationHeader);
        profileService.editPassword(username, oldPassword.getPassword(), newPassword);

    }

    @DeleteMapping("/profile/delete")
    public void deleteUserById(@RequestHeader("Authorization") String authorizationHeader){
        getUsernameFromToken(authorizationHeader);
    String username = getUsernameFromToken(authorizationHeader);
        profileService.deleteByEmail(username);
    }








    private String getUsernameFromToken(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);

        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();

        Map<String, String> response = new HashMap<>();
        response.put("username", username);
        return username;
    }
}
