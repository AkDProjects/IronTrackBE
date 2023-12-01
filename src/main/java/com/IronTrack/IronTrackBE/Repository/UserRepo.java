package com.IronTrack.IronTrackBE.Repository;

import com.IronTrack.IronTrackBE.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.IronTrack.IronTrackBE.Repository.Entities.UserEntity;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    // Define custom query methods if needed
    Optional<UserEntity> findByEmail(String email);

    void deleteByEmail(String email);



}