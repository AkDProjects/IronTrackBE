package com.IronTrack.IronTrackBE.Models;

import com.IronTrack.IronTrackBE.Repository.Entities.UserEntity;
import lombok.Data;

@Data
public class User {
    private String name;
    private String email;
    private String password;
    private Long id;

    public User(UserEntity userEntity){
        this.name = userEntity.getName();
        this.email = userEntity.getEmail();
        this.password = userEntity.getPassword();
        this.id = userEntity.getId();
    }
}
