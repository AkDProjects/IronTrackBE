package com.IronTrack.IronTrackBE.Services;

import com.IronTrack.IronTrackBE.Models.*;
import com.IronTrack.IronTrackBE.Repository.*;
import com.IronTrack.IronTrackBE.Repository.Entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class WorkoutSetService {
    private final UserRepo userRepo;
    private final RoutineRepo routineRepo;
    private final RoutineExercisesRepo routineExerciseRepo;
    private final RoutineExerciseHistoryRepo routineExerciseHistoryEntity;
    private final WorkoutRepo workoutRepo;
    private final WorkoutSetRepo workoutSetRepo;
    public WorkoutSet createWorkoutSetSession(WorkoutSet workoutSet) throws NullPointerException, SecurityException, ExceptionInInitializerError {
        if (workoutSet.getRoutineExercise()== null || workoutSet.getSessionStart() == null) {
            throw new ExceptionInInitializerError("Either exercise or session start are not defined");
        }
        WorkoutSetEntity workoutSetEntity = new WorkoutSetEntity();
        RoutineExercisesEntity routineExerciseEntity = routineExerciseRepo.findById(workoutSet.getRoutineExercise().getId());
        if (routineExerciseEntity == null) {
            throw new NullPointerException("Routine Exercise with ID " + workoutSet.getRoutineExercise().getId() + " does not exist");
        } else if (routineExerciseEntity.getRoutineEntity().getUserEntity() != getUserEntity()) {
            throw new SecurityException("Routine with ID " + workoutSet.getRoutineExercise().getId() + " does not belong to User with ID " + getUserEntity().getId());
        }

        RoutineExerciseHistoryEntity routineExerciseHistoryEntity = this.routineExerciseHistoryEntity
            .findByRoutineExerciseAndIteration(routineExerciseEntity.getId(), routineExerciseEntity.getIterations());

        workoutSetEntity.setRoutineExerciseHistoryEntity(routineExerciseHistoryEntity);
        workoutSetEntity.setSessionStart(workoutSet.getSessionStart());

        WorkoutSetEntity savedWorkoutSetEntity = workoutSetRepo.save(workoutSetEntity);

        return mapWorkoutSetEntityToWorkoutSet(savedWorkoutSetEntity);
    }

    private UserEntity getUserEntity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {

            return userRepo.findByEmail(auth.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("Invalid user email"));
        }

        throw new SecurityException("User is not authenticated");
    }

    private WorkoutSet mapWorkoutSetEntityToWorkoutSet(WorkoutSetEntity workoutSetEntity) {
        WorkoutSet workoutSet = new WorkoutSet();
        workoutSet.setId(workoutSetEntity.getId());
        workoutSet.setRoutineExercise(mapRoutineExerciseHistoryEntityToRoutineExercise(workoutSetEntity.getRoutineExerciseHistoryEntity()));
        workoutSet.setSessionStart(workoutSetEntity.getSessionStart());

        return workoutSet;
    }


    private RoutineExercise mapRoutineExerciseHistoryEntityToRoutineExercise(RoutineExerciseHistoryEntity routineExerciseHistoryEntity) {
        RoutineExercise routineExercise = new RoutineExercise();
        Exercise exercise = mapExerciseEntitytoExercise(routineExerciseHistoryEntity.getExerciseEntity());
        routineExercise.setExercise(exercise);
        routineExercise.setSets(routineExerciseHistoryEntity.getSets());
        routineExercise.setWeight(routineExerciseHistoryEntity.getWeight());
        routineExercise.setId(routineExerciseHistoryEntity.getId());
        routineExercise.setQuantityUnit(routineExerciseHistoryEntity.getQuantityUnit());
        routineExercise.setQuantity(routineExerciseHistoryEntity.getQuantity());

        return routineExercise;
    }

    private Exercise mapExerciseEntitytoExercise(ExerciseEntity exerciseEntity) {
        Exercise exercise = new Exercise();
        exercise.setId(exerciseEntity.getId());
        exercise.setName(exerciseEntity.getName());
        exercise.setType(exerciseEntity.getType());
        exercise.setMuscle(exerciseEntity.getMuscle());
        exercise.setDifficulty(exerciseEntity.getDifficulty());
        exercise.setEquipment(exerciseEntity.getEquipment());
        exercise.setInstructions(exerciseEntity.getInstructions());

        return exercise;
    }
}
