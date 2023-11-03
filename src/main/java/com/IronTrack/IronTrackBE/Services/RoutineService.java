package com.IronTrack.IronTrackBE.Services;

import com.IronTrack.IronTrackBE.Models.Exercise;
import com.IronTrack.IronTrackBE.Models.RoutineExercise;
import com.IronTrack.IronTrackBE.Repository.*;
import com.IronTrack.IronTrackBE.Repository.Entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoutineService {

    private final UserRepo userRepo;
    private final RoutineRepo routineRepo;
    private final RoutineExercisesRepo routineExercisesRepo;
    private final ExerciseRepo exerciseRepo;
    private final RoutineExerciseHistoryRepo routineExerciseHistoryRepo;

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

    public void addRoutineExercise(Long routineId, RoutineExercise routineExercise) throws NullPointerException, SecurityException {
        RoutineEntity routine = getRoutineEntity(routineId);
        ExerciseEntity exercise = getExerciseEntity(routineExercise);

        RoutineExercisesEntity routineExercisesEntity = new RoutineExercisesEntity();
        routineExercisesEntity.setRoutineEntity(routine);
        routineExercisesEntity.setExerciseEntity(exercise);
        routineExercisesEntity.setSets(routineExercise.getSets());
        routineExercisesEntity.setQuantity(routineExercise.getQuantity());
        routineExercisesEntity.setWeight(routineExercise.getWeight());
        routineExercisesEntity.setQuantityUnit(routineExercise.getQuantityUnit());
        routineExercisesEntity.setIterations(0);
        RoutineExercisesEntity result = routineExercisesRepo.save(routineExercisesEntity);

        addRoutineExerciseHistory(result);
    }

    public void addRoutineExerciseHistory(RoutineExercisesEntity routineExerciseEntity) {
        RoutineExerciseHistoryEntity routineExerciseHistoryEntity = new RoutineExerciseHistoryEntity();
        routineExerciseHistoryEntity.setExerciseEntity(routineExerciseEntity.getExerciseEntity());
        routineExerciseHistoryEntity.setRoutineExerciseEntity(routineExerciseEntity);
        routineExerciseHistoryEntity.setWeight(routineExerciseEntity.getWeight());
        routineExerciseHistoryEntity.setSets(routineExerciseEntity.getSets());
        routineExerciseHistoryEntity.setQuantity(routineExerciseEntity.getQuantity());
        routineExerciseHistoryEntity.setQuantityUnit(routineExerciseEntity.getQuantityUnit());
        routineExerciseHistoryEntity.setIteration(routineExerciseEntity.getIterations());
        routineExerciseHistoryEntity.setUpdatedAt(LocalDateTime.now());

        routineExerciseHistoryRepo.save(routineExerciseHistoryEntity);
    }

    public void updateRoutineExercise(Long routineId, Long routineExerciseId, RoutineExercise routineExercise) throws Exception {
        RoutineEntity routine = getRoutineEntity(routineId);
        RoutineExercisesEntity newRoutineExercise = getRoutineExercisesEntity(routine, routineExerciseId);
        ExerciseEntity exerciseEntity = getExerciseEntity(routineExercise);
        try {
            newRoutineExercise.setExerciseEntity(exerciseEntity);
            newRoutineExercise.setQuantityUnit(routineExercise.getQuantityUnit());
            newRoutineExercise.setQuantity(routineExercise.getQuantity());
            newRoutineExercise.setSets(routineExercise.getSets());
            newRoutineExercise.setWeight(routineExercise.getWeight());
            newRoutineExercise.setIterations(newRoutineExercise.getIterations() + 1);
            RoutineExercisesEntity result = routineExercisesRepo.save(newRoutineExercise);
            addRoutineExerciseHistory(result);

        } catch (Exception ex) {
            throw new Exception("There was a problem updating the routine exercise");
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

    private ExerciseEntity getExerciseEntity(RoutineExercise routineExercise) {
        Exercise exercise = routineExercise.getExercise();
        Optional<ExerciseEntity> exerciseEntity = exerciseRepo.findByName(exercise.getName());

        if (exerciseEntity.isPresent()) {
            return exerciseEntity.get();
        } else {
            ExerciseEntity newExerciseEntity = new ExerciseEntity();
            newExerciseEntity.setMuscle(exercise.getMuscle());
            newExerciseEntity.setName(exercise.getName());
            newExerciseEntity.setType(exercise.getType());
            newExerciseEntity.setEquipment(exercise.getEquipment());
            newExerciseEntity.setDifficulty(exercise.getDifficulty());
            newExerciseEntity.setInstructions(exercise.getInstructions());
            return exerciseRepo.save(newExerciseEntity);
        }

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
