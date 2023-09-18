package com.IronTrack.IronTrackBE.Repository;

import com.IronTrack.IronTrackBE.Repository.Entities.RoutineExercisesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoutineExerciseRepo extends JpaRepository<RoutineExercisesEntity, Integer> {
    @Query("select a.exercise_id from RoutineExercisesEntity a where a.routine_id  = :routine_id")
    List<Integer> findExercisesByRoutineID(Integer routine_id);

    List<RoutineExercisesEntity> findAllByRoutine_id(Integer routine_id);

}
