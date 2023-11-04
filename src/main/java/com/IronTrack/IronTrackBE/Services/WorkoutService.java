package com.IronTrack.IronTrackBE.Services;

import com.IronTrack.IronTrackBE.Models.Exercise;
import com.IronTrack.IronTrackBE.Models.Routine;
import com.IronTrack.IronTrackBE.Models.RoutineExercise;
import com.IronTrack.IronTrackBE.Models.Workout;
import com.IronTrack.IronTrackBE.Repository.Entities.*;
import com.IronTrack.IronTrackBE.Repository.RoutineRepo;
import com.IronTrack.IronTrackBE.Repository.UserRepo;
import com.IronTrack.IronTrackBE.Repository.WorkoutRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class WorkoutService {
    private final UserRepo userRepo;
    private final RoutineRepo routineRepo;
    private final WorkoutRepo workoutRepo;
    public Workout createWorkoutSession(Workout workout) throws NullPointerException, SecurityException, ExceptionInInitializerError {
        if (workout.getRoutine() == null || workout.getSessionStart() == null) {
            throw new ExceptionInInitializerError("Either routine or session start or not defined");
        }
        WorkoutEntity workoutEntity = new WorkoutEntity();
        RoutineEntity routineEntity = routineRepo.findById(workout.getRoutine().getId());
        if (routineEntity == null) {
            throw new NullPointerException("Routine with ID " + workout.getRoutine().getId() + " does not exist");
        } else if (routineEntity.getUserEntity() != getUserEntity()) {
            throw new SecurityException("Routine with ID " + workout.getRoutine().getId() + " does not belong to User with ID " + getUserEntity().getId());
        }
        workoutEntity.setRoutineEntity(routineEntity);
        workoutEntity.setSessionStart(workout.getSessionStart());

        WorkoutEntity savedWorkoutEntity = workoutRepo.save(workoutEntity);

        return mapWorkoutEntityToWorkout(savedWorkoutEntity);
    };

    public void updateWorkoutSession(Workout workout) throws NullPointerException, SecurityException, ExceptionInInitializerError {
        if (workout.getId() == null || workout.getSessionEnd() == null) {
            throw new ExceptionInInitializerError("Request body missing id or sessionEnd");
        }
        Optional<WorkoutEntity> workoutEntity = workoutRepo.findById(workout.getId());

        if (workoutEntity.isPresent()) {
            WorkoutEntity workoutToUpdate = workoutEntity.get();
            UserEntity workoutOwner = workoutToUpdate.getRoutineEntity().getUserEntity();
            UserEntity user = getUserEntity();

            if (workoutOwner == user) {
                workoutEntity.get().setSessionEnd(workout.getSessionEnd());
                workoutRepo.save(workoutEntity.get());
            } else {
                throw new SecurityException("User with ID " + user.getId() + " does not own workout with ID " + workout.getId());
            }

        } else {
            throw new NullPointerException("Workout with id " + workout.getId() + " does not exist");
        }
    }

    private UserEntity getUserEntity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {

            return userRepo.findByEmail(auth.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("Invalid user email"));
        }

        throw new SecurityException("User is not authenticated");
    }

    private Workout mapWorkoutEntityToWorkout(WorkoutEntity workoutEntity) {
        Workout workout = new Workout();
        workout.setId(workoutEntity.getId());
        workout.setRoutine(mapRoutineEntityToRoutine(workoutEntity.getRoutineEntity()));
        workout.setSessionStart(workoutEntity.getSessionStart());

        return workout;
    }

    private Routine mapRoutineEntityToRoutine(RoutineEntity routineEntity) {
        Routine routine = new Routine();
        List<RoutineExercise> routineExercises = mapRoutineExerciseEntitiestoRoutineExercises(routineEntity.getRoutineExercisesEntities());
        routine.setExercises(routineExercises);
        routine.setId(routineEntity.getId());
        routine.setName(routineEntity.getName());

        return routine;
    }

    private List<RoutineExercise> mapRoutineExerciseEntitiestoRoutineExercises(List<RoutineExercisesEntity> routineExercisesEntities) {
        List<RoutineExercise> routineExercises = new ArrayList<>();
        for (RoutineExercisesEntity routineExercisesEntity : routineExercisesEntities) {
            RoutineExercise routineExercise = new RoutineExercise();
            Exercise exercise = mapExerciseEntitytoExercise(routineExercisesEntity.getExerciseEntity());
            routineExercise.setExercise(exercise);
            routineExercise.setSets(routineExercisesEntity.getSets());
            routineExercise.setWeight(routineExercisesEntity.getWeight());
            routineExercise.setId(routineExercisesEntity.getId());
            routineExercise.setQuantityUnit(routineExercisesEntity.getQuantityUnit());
            routineExercise.setQuantity(routineExercisesEntity.getQuantity());

            routineExercises.add(routineExercise);
        }

        return routineExercises;
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
