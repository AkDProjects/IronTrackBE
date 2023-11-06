package com.IronTrack.IronTrackBE.Repository;

import com.IronTrack.IronTrackBE.Repository.Entities.WorkoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutRepo extends JpaRepository<WorkoutEntity, Long> {

}
