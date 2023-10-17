package com.IronTrack.IronTrackBE.Services;

import com.IronTrack.IronTrackBE.Models.*;
import com.IronTrack.IronTrackBE.Repository.Entities.ExerciseEntity;
import com.IronTrack.IronTrackBE.Repository.Entities.RoutineEntity;
import com.IronTrack.IronTrackBE.Repository.Entities.RoutineExercisesEntity;
import com.IronTrack.IronTrackBE.Repository.Entities.UserEntity;
import com.IronTrack.IronTrackBE.Repository.ExerciseRepo;
import com.IronTrack.IronTrackBE.Repository.RoutineExercisesRepo;
import com.IronTrack.IronTrackBE.Repository.RoutineRepo;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.IronTrack.IronTrackBE.Repository.UserRepo;

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
    private final ApiNinjasService apiNinjas;

    public List<Routine> getRoutines() {
        UserEntity user = getUserEntity();

        return getUserRoutines(user);
    }

    public String createRoutine(CreateRoutineRequest request) throws SecurityException {
        UserEntity user = getUserEntity();
        List<ExerciseEntity> exercises = getExerciseEntities(request.getExercises());
        RoutineEntity routine = saveRoutine(request.getName(), user);
        saveRoutineExerciseEntities(request.getExercises(), routine, exercises);

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

    private void saveRoutineExerciseEntities(List<RoutineExercise> routineExercises, RoutineEntity routine, List<ExerciseEntity> exercises) {
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

            routineExercisesRepo.save(routineExercisesEntity);
        }
    }

    private List<Routine> getUserRoutines(UserEntity user) {
        List<Routine> routines = new ArrayList<>();
        List<RoutineEntity> routineEntities = routineRepo.findAllByUserEntity(user).orElse(new ArrayList<>());
        for (RoutineEntity routineEntity: routineEntities) {
            Routine routine = new Routine();
            List<RoutineExercise> exercises = new ArrayList<>();
            List<RoutineExercisesEntity> routineExerciseEntities = routineExercisesRepo.findAllByRoutineEntity(routineEntity);
            for (RoutineExercisesEntity routineExerciseEntity : routineExerciseEntities) {
                RoutineExercise exercise = mapRoutineExerciseEntityToRoutineExercise(routineExerciseEntity);
                exercises.add(exercise);
            }

            routine.setName(routineEntity.getName());
            routine.setId(Optional.ofNullable(routineEntity.getId()));
            routine.setExercises(exercises);
            routines.add(routine);
        }

        return routines;
    }

    private Exercise mapExerciseEntityToExercise(ExerciseEntity exerciseEntity) {

        Exercise exercise = new Exercise();
        exercise.setId(Optional.ofNullable(exerciseEntity.getId()));
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
        routineExercise.setId(Optional.ofNullable(routineExerciseEntity.getId()));
        routineExercise.setExercise(exercise);
        routineExercise.setQuantity(routineExerciseEntity.getQuantity());
        routineExercise.setWeight(routineExerciseEntity.getWeight());
        routineExercise.setSets(routineExerciseEntity.getSets());
        routineExercise.setQuantityUnit(routineExerciseEntity.getQuantityUnit());

        return routineExercise;
    }
}

