package com.IronTrack.IronTrackBE.Services;

import com.IronTrack.IronTrackBE.Models.*;
import com.IronTrack.IronTrackBE.Repository.Entities.*;
import com.IronTrack.IronTrackBE.Repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.IronTrack.IronTrackBE.Repository.UserRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final UserRepo userRepo;
    private final RoutineRepo routineRepo;
    private final ExerciseRepo exerciseRepo;
    private final RoutineExercisesRepo routineExercisesRepo;
    private final RoutineExerciseHistoryRepo routineExerciseHistoryRepo;

    public List<Routine> getRoutines() {
        UserEntity user = getUserEntity();

        return getUserRoutines(user);
    }

    public Routine getRoutine(Long routineId) throws NullPointerException, SecurityException {
        UserEntity user = getUserEntity();
        RoutineEntity routineEntity = routineRepo.findById(routineId);
        Routine routine = new Routine();
        routine.setId(routineId);
        try {
            routine.setName(routineEntity.getName());
        } catch (NullPointerException e) {
            // catch routine being null
            throw new NullPointerException("Routine could not be found");
        }

        if (routineEntity.getUserEntity() != user) {
            throw new SecurityException("Routine does not belong to this user");
        }

        List<RoutineExercise> routineExercises = getRoutineExercises(routineEntity);
        routine.setExercises(routineExercises);

        return routine;
    }

    public String createRoutine(CreateRoutineRequest request) throws SecurityException {
        UserEntity user = getUserEntity();
        List<ExerciseEntity> exercises = getExerciseEntities(request.getExercises());
        RoutineEntity routine = saveRoutine(request.getName(), user);
        List<RoutineExercisesEntity> routineExercises = saveRoutineExerciseEntities(request.getExercises(), routine, exercises);
        saveRoutineExerciseHistoryEntities(routineExercises);

        return "Successfully created new routine";
    }

    private UserEntity getUserEntity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {

            return userRepo.findByEmail(auth.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("Invalid user email"));
        }

        throw new SecurityException("User is not authenticated");
    }

    private List<RoutineExercise> getRoutineExercises(RoutineEntity routineEntity) {
        List<RoutineExercisesEntity> routineExercisesEntities = routineExercisesRepo.findAllByRoutineEntity(routineEntity);
        List<RoutineExercise> routineExercises = new ArrayList<>();
        for (RoutineExercisesEntity routineExercisesEntity: routineExercisesEntities) {
            RoutineExercise routineExercise = mapRoutineExerciseEntityToRoutineExercise(routineExercisesEntity);
            routineExercises.add(routineExercise);
        }

        return routineExercises;
    }

    private List<ExerciseEntity> getExerciseEntities(List<RoutineExercise> routineExercises) {
        List<ExerciseEntity> exercises = new ArrayList<>();
        for (RoutineExercise routineExercise : routineExercises) {
            Exercise exercise = routineExercise.getExercise();

            Optional<ExerciseEntity> exerciseEntity = exerciseRepo
                .findByName(exercise.getName());

            if (exerciseEntity.isPresent()) {
                exercises.add(exerciseEntity.orElse(null));
            } else {
                ExerciseEntity newExercise = new ExerciseEntity();
                newExercise.setName(exercise.name);
                newExercise.setEquipment(exercise.equipment);
                newExercise.setDifficulty(exercise.difficulty);
                newExercise.setType(exercise.type);
                newExercise.setMuscle(exercise.muscle);
                newExercise.setInstructions(exercise.instructions);

                ExerciseEntity savedExercise = exerciseRepo.save(newExercise);
                exercises.add(savedExercise);
            }
        }

        return exercises;
    }

    private RoutineEntity saveRoutine(String name, UserEntity user) {
        RoutineEntity routineEntity = new RoutineEntity();
        routineEntity.setName(name);
        routineEntity.setUserEntity(user);
        return routineRepo.save(routineEntity);
    }

    private List<RoutineExercisesEntity> saveRoutineExerciseEntities(List<RoutineExercise> routineExercises, RoutineEntity routine, List<ExerciseEntity> exercises) {
        List<RoutineExercisesEntity> savedRoutineExercises = new ArrayList<>();

        for (int i = 0; i < routineExercises.size(); i++) {
            RoutineExercise routineExercise = routineExercises.get(i);
            ExerciseEntity exercise = exercises.get(i);

            RoutineExercisesEntity routineExercisesEntity = new RoutineExercisesEntity();
            routineExercisesEntity.setExerciseEntity(exercise);
            routineExercisesEntity.setRoutineEntity(routine);
            routineExercisesEntity.setWeight(routineExercise.getWeight());
            routineExercisesEntity.setSets(routineExercise.getSets());
            routineExercisesEntity.setQuantity(routineExercise.getQuantity());
            routineExercisesEntity.setQuantityUnit(routineExercise.getQuantityUnit());
            routineExercisesEntity.setIterations(0);

            savedRoutineExercises.add(routineExercisesRepo.save(routineExercisesEntity));
        }

        return savedRoutineExercises;
    }

    private void saveRoutineExerciseHistoryEntities(List<RoutineExercisesEntity> routineExerciseEntities) {
        for (RoutineExercisesEntity routineExerciseEntity : routineExerciseEntities) {

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
    }

    private List<Routine> getUserRoutines(UserEntity user) {
        List<Routine> routines = new ArrayList<>();
        List<RoutineEntity> routineEntities = routineRepo.findAllByUserEntity(user).orElse(new ArrayList<>());
        for (RoutineEntity routineEntity: routineEntities) {
            Routine routine = new Routine();
            routine.setName(routineEntity.getName());
            routine.setId(routineEntity.getId());
            routines.add(routine);
        }

        return routines;
    }

    private Exercise mapExerciseEntityToExercise(ExerciseEntity exerciseEntity) {

        Exercise exercise = new Exercise();
        exercise.setId(exerciseEntity.getId());
        exercise.setDifficulty(exerciseEntity.getDifficulty());
        exercise.setEquipment(exerciseEntity.getEquipment());
        exercise.setName(exerciseEntity.getName());
        exercise.setType(exerciseEntity.getType());
        exercise.setMuscle(exerciseEntity.getMuscle());
        exercise.setInstructions(exerciseEntity.getInstructions());

        return exercise;
    }

    private RoutineExercise mapRoutineExerciseEntityToRoutineExercise(RoutineExercisesEntity routineExerciseEntity) {
        Exercise exercise = mapExerciseEntityToExercise(routineExerciseEntity.getExerciseEntity());
        RoutineExercise routineExercise = new RoutineExercise();
        routineExercise.setId(routineExerciseEntity.getId());
        routineExercise.setExercise(exercise);
        routineExercise.setQuantity(routineExerciseEntity.getQuantity());
        routineExercise.setWeight(routineExerciseEntity.getWeight());
        routineExercise.setSets(routineExerciseEntity.getSets());
        routineExercise.setQuantityUnit(routineExerciseEntity.getQuantityUnit());

        return routineExercise;
    }
}

