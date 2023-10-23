package com.IronTrack.IronTrackBE.Services;

import com.IronTrack.IronTrackBE.Models.Exercise;
import com.IronTrack.IronTrackBE.Models.RoutineExercise;
import com.IronTrack.IronTrackBE.Repository.Entities.ExerciseEntity;
import com.IronTrack.IronTrackBE.Repository.Entities.RoutineEntity;
import com.IronTrack.IronTrackBE.Repository.Entities.RoutineExercisesEntity;
import com.IronTrack.IronTrackBE.Repository.Entities.UserEntity;
import com.IronTrack.IronTrackBE.Repository.RoutineExercisesRepo;
import com.IronTrack.IronTrackBE.Repository.RoutineRepo;
import com.IronTrack.IronTrackBE.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RoutineService {

    private final UserRepo userRepo;
    private final RoutineRepo routineRepo;
    private final RoutineExercisesRepo routineExercisesRepo;

    public RoutineExercise getRoutineExercise(Long routineId, Long routineExerciseId) throws NullPointerException, SecurityException {
        RoutineEntity routine = getRoutineEntity(routineId);
        RoutineExercisesEntity routineExercises = getRoutineExercisesEntity(routine, routineExerciseId);

        return mapRoutineExercisesEntityToRoutineExercise(routineExercises);
    }

    public void deleteRoutineExercise(Long routineId, Long routineExerciseId) throws NullPointerException, SecurityException {
        RoutineEntity routine = getRoutineEntity(routineId);
        RoutineExercisesEntity routineExercises = getRoutineExercisesEntity(routine, routineExerciseId);
        routineExercisesRepo.delete(routineExercises);
    }

    private UserEntity getUserEntity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {

            return userRepo.findByEmail(auth.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("Invalid user email"));
        }

        throw new SecurityException("User is not authenticated");
    }

    private RoutineEntity getRoutineEntity(Long routineId) throws NoSuchElementException, SecurityException {

        UserEntity user = getUserEntity();
        UserEntity routineUser;
        RoutineEntity routine = routineRepo.findById(routineId);

        if (routine == null) {
            throw new NoSuchElementException("Routine was not found");
        }

        routineUser = routine.getUserEntity();

        if (routineUser != user) {
            throw new SecurityException("User does not own routine");
        }

        return routine;
    }

    private RoutineExercisesEntity getRoutineExercisesEntity(RoutineEntity routine, Long routineExerciseId) throws NoSuchElementException, IllegalArgumentException {
        RoutineExercisesEntity routineExercise = routineExercisesRepo.findById(routineExerciseId);
        RoutineEntity routineRef;

        if (routineExercise == null) {
            throw new NoSuchElementException("Routine Exercise not found");
        }

        routineRef = routineExercise.getRoutineEntity();

        if (routineRef != routine) {
            throw new IllegalArgumentException("Exercise belongs to different routine");
        }

        return routineExercise;
    }

    private Exercise mapExerciseEntityToExercise(ExerciseEntity exerciseEntity) {
        Exercise exercise = new Exercise();
        exercise.setInstructions(exerciseEntity.getInstructions());
        exercise.setEquipment(exerciseEntity.getEquipment());
        exercise.setName(exerciseEntity.getName());
        exercise.setType(exerciseEntity.getType());
        exercise.setMuscle(exerciseEntity.getMuscle());
        exercise.setDifficulty(exerciseEntity.getDifficulty());
        exercise.setId(exerciseEntity.getId());

        return exercise;
    }

    private RoutineExercise mapRoutineExercisesEntityToRoutineExercise(RoutineExercisesEntity routineExercises) {
        RoutineExercise routineExercise = new RoutineExercise();
        routineExercise.setWeight(routineExercises.getWeight());
        routineExercise.setSets(routineExercises.getSets());
        routineExercise.setQuantity(routineExercises.getQuantity());
        routineExercise.setQuantityUnit(routineExercises.getQuantityUnit());
        ExerciseEntity exercise = routineExercises.getExerciseEntity();
        routineExercise.setExercise(mapExerciseEntityToExercise(exercise));
        routineExercise.setId(routineExercises.getId());

        return routineExercise;
    }
}
