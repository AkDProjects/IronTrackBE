package com.IronTrack.IronTrackBE.Services;

import com.IronTrack.IronTrackBE.Models.AuthenticationResponse;
import com.IronTrack.IronTrackBE.Models.User;
import com.IronTrack.IronTrackBE.Repository.Entities.UserEntity;
import com.IronTrack.IronTrackBE.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public ProfileService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    //Does this has cascading effects
    public void deleteByEmail(String email) {
        userRepo.deleteByEmail(email);
    }

    public User findbyEmail(String email) {
        Optional<UserEntity> userEntity = userRepo.findByEmail(email);
        return new User(userEntity.get());
    }


    public User editUserName(String email, String newName) {
        Optional<UserEntity> userEntity = userRepo.findByEmail(email);
        User thisUser = new User(userEntity.get());
        thisUser.setEmail(newName);
        userRepo.save(new UserEntity(thisUser));
        return thisUser;
    }

    public AuthenticationResponse editEmail(String email, String newEmail) {
        Optional<UserEntity> userEntity = userRepo.findByEmail(email);
        User thisUser = new User(userEntity.get());
        thisUser.setEmail(newEmail);
        return null;

    }


    public void editPassword(String email, String oldPassword, String newPassword) {
        Optional<UserEntity> userEntity = userRepo.findByEmail(email);
        User thisUser = new User(userEntity.get());
        String storedPassword = thisUser.getPassword();
        if(passwordEncoder.matches(oldPassword,storedPassword)){
            thisUser.setPassword(newPassword);
        }


    }
}

