package com.IronTrack.IronTrackBE.Repository;

import com.IronTrack.IronTrackBE.Repository.Entities.RoutineEntity;
import com.IronTrack.IronTrackBE.Repository.Entities.RoutineExercisesEntity;
import com.IronTrack.IronTrackBE.Repository.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoutineExercisesRepo extends JpaRepository<RoutineExercisesEntity, Integer> {
    List<RoutineExercisesEntity> findAllByRoutineEntity(RoutineEntity routineEntity);
}
