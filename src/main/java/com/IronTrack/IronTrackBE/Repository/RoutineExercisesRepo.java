package com.IronTrack.IronTrackBE.Repository;

import com.IronTrack.IronTrackBE.Repository.Entities.RoutineEntity;
import com.IronTrack.IronTrackBE.Repository.Entities.RoutineExercisesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutineExercisesRepo extends JpaRepository<RoutineExercisesEntity, Integer> {
    List<RoutineExercisesEntity> findAllByRoutineEntity(RoutineEntity routineEntity);
    RoutineExercisesEntity findById(Long routineId);
}
