package com.IronTrack.IronTrackBE.Services;

import com.IronTrack.IronTrackBE.Models.User;
import com.IronTrack.IronTrackBE.Repository.Entities.UserEntity;
import com.IronTrack.IronTrackBE.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private UserRepo userRepo;



//Does this has cascading effects
    public void deleteByEmail(String email) {
        userRepo.deleteByEmail(email);
    }

    public User findbyEmail(String email) {
        Optional<UserEntity> userEntity = userRepo.findByEmail(email);
        User thisUser = new User(userEntity.get());
        return thisUser;
    }


    public Object editUserProfileInfo(String username) {
        return null;
    }
}

