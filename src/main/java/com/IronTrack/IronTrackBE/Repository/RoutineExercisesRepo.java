package com.IronTrack.IronTrackBE.Repository;

import com.IronTrack.IronTrackBE.Repository.Entities.RoutineExercisesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoutineExerciseRepo extends JpaRepository<RoutineExercisesEntity, Integer> {
    @Query("select a.exercise_id from RoutineExercisesEntity a where a.routineId  = :routine_id")
    List<String> findExercisesByRoutineID(Integer routine_id);

    List<RoutineExercisesEntity> findAllByRoutineId(Integer id);

}
