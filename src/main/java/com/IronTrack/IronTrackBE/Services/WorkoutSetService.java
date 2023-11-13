package com.IronTrack.IronTrackBE.Services;

import com.IronTrack.IronTrackBE.Models.*;
import com.IronTrack.IronTrackBE.Repository.*;
import com.IronTrack.IronTrackBE.Repository.Entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class WorkoutSetService {
    private final UserRepo userRepo;
    private final RoutineExercisesRepo routineExerciseRepo;
    private final RoutineExerciseHistoryRepo routineExerciseHistoryEntity;
    private final WorkoutSetRepo workoutSetRepo;
    private final WorkoutRepo workoutRepo;

    public void endWorkoutSetSession(WorkoutSet workoutSet) {
        if (workoutSet.getId()== null || workoutSet.getSessionEnd() == null) {
            throw new ExceptionInInitializerError("Either exercise or session start are not defined");
        }

        Optional<WorkoutSetEntity> workoutSetEntity = workoutSetRepo.findById(workoutSet.getId());
        if (workoutSetEntity.isPresent()) {
            workoutSetEntity.get().setSessionEnd(workoutSet.getSessionEnd());
            workoutSetRepo.save(workoutSetEntity.get());
        } else {
            throw new NullPointerException("Routine Exercise with ID " + workoutSet.getRoutineExercise().getId() + " does not exist");
        }
    }

    public WorkoutSet createWorkoutSetSession(Long workoutId, WorkoutSet workoutSet) throws NullPointerException, SecurityException, ExceptionInInitializerError {
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
            .findByRoutineExerciseIdAndIteration(routineExerciseEntity.getId(), routineExerciseEntity.getIterations());

        workoutSetEntity.setRoutineExerciseHistoryEntity(routineExerciseHistoryEntity);
        workoutSetEntity.setSessionStart(workoutSet.getSessionStart());
        Optional<WorkoutEntity> workoutEntity = workoutRepo.findById(workoutId);

        if (workoutEntity.isPresent()) {
            workoutSetEntity.setWorkoutEntity(workoutEntity.get());
        } else {
            throw new NullPointerException("Workout with ID " + workoutId + " does not exist");
        }


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
