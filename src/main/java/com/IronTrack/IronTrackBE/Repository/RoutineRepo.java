package com.IronTrack.IronTrackBE.Repository;

import com.IronTrack.IronTrackBE.Repository.Entities.RoutineEntitiy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoutineRepo extends JpaRepository<RoutineEntitiy, Integer> {
    Optional<RoutineEntitiy> findFirstByName(String name);


}
