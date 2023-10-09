package com.IronTrack.IronTrackBE.Repository;

import com.IronTrack.IronTrackBE.Repository.Entities.UserEntitiy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<UserEntitiy, Long> {
    //Dervived Query
    List<UserEntitiy> findByEmailAndPassword(String email, String password);
    Boolean existsByEmailAndPassword(String email, String password);
    Boolean existsByEmail(String email);

    List<UserEntitiy> findByEmail(String email);
}
