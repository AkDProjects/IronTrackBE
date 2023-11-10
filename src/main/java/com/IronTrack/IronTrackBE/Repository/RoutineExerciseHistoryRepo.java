package com.IronTrack.IronTrackBE.Repository;

import com.IronTrack.IronTrackBE.Repository.Entities.RoutineExerciseHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineExerciseHistoryRepo extends JpaRepository<RoutineExerciseHistoryEntity, Long> {

    RoutineExerciseHistoryEntity findByRoutineExerciseAndIteration(Long routineExerciseId, Integer iteration);
}
