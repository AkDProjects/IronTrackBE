package com.IronTrack.IronTrackBE.Repository;

import com.IronTrack.IronTrackBE.Repository.Entities.RoutineExerciseHistoryEntity;
import com.IronTrack.IronTrackBE.Repository.Entities.RoutineExercisesEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.function.Function;

@Repository
public interface RoutineExerciseHistoryRepo extends JpaRepository<RoutineExerciseHistoryEntity, Long> {

    RoutineExerciseHistoryEntity findByRoutineExerciseAndIteration(Long routineExerciseId, Integer iteration);
}
