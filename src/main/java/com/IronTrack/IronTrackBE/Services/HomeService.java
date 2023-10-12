package com.IronTrack.IronTrackBE.Services;

import com.IronTrack.IronTrackBE.Models.CreateRoutineRequest;
import com.IronTrack.IronTrackBE.Models.RoutineExerciseRequest;
import com.IronTrack.IronTrackBE.Repository.Entities.ExerciseEntity;
import com.IronTrack.IronTrackBE.Repository.Entities.RoutineEntity;
import com.IronTrack.IronTrackBE.Repository.Entities.RoutineExercisesEntity;
import com.IronTrack.IronTrackBE.Repository.Entities.UserEntity;
import com.IronTrack.IronTrackBE.Repository.ExerciseRepo;
import com.IronTrack.IronTrackBE.Repository.RoutineExercisesRepo;
import com.IronTrack.IronTrackBE.Repository.RoutineRepo;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public String createRoutine(CreateRoutineRequest request) throws JsonProcessingException {
        // First Build Routine
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = null;
        RoutineEntity savedRoutineEntity = null; // post save
        RoutineEntity routineEntity = null; // pre save (no id)

        if (auth.isAuthenticated()) {
            user = userRepo.findByEmail(auth.getName()).orElseThrow(() -> new UsernameNotFoundException("Invalid user email"));
            routineEntity = new RoutineEntity();
            routineEntity.setName(request.getName());
            routineEntity.setUserEntity(user);
        }

        // Second build exercise Entities
        boolean error = false;
        List<ExerciseEntity> exercisesToSave = new ArrayList<>();
        List<ExerciseEntity> savedRoutineExercises = new ArrayList<>();
        for (RoutineExerciseRequest exerciseRequest: request.getExercises()) {
            HttpResponse<String> response = null;
            String exerciseName = exerciseRequest.getName();

            // Check if exercise already exists in repo
            Optional<ExerciseEntity> exerciseExists = exerciseRepo.findByName(exerciseName);

            if (exerciseExists.isEmpty()) {
                try {
                    response = apiNinjas.getExercise(exerciseName);
                    if (response.statusCode() == 200) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        ExerciseEntity[] exercises = objectMapper.readValue(response.body(), ExerciseEntity[].class);

                        ExerciseEntity selectedExercise = null;
                        for (ExerciseEntity exercise : exercises) {
                            if (exercise.getName().equals(exerciseName)) {
                                selectedExercise = exercise;
                                break; // Found a matching exercise, exit the loop
                            }
                        }
                        if (selectedExercise != null) {
                            exercisesToSave.add(selectedExercise);
                        } else {
                            error = true;
                            break;
                        }

                    } else {
                        System.out.println("Error response received for exercise: " + exerciseName);
                        exercisesToSave.clear();
                        error = true;
                        break;
                    }
                } catch (IOException e) {
                    System.out.println("There was an error");
                    throw new RuntimeException(e);
                }
            } else {
                savedRoutineExercises.add(exerciseExists.orElse(null));
            }
        }

        if (error) {
            throw new RuntimeException("Routine could not be saved");
        } else if (!exercisesToSave.isEmpty()) {
            List<ExerciseEntity> savedExercises = exerciseRepo.saveAll(exercisesToSave);
            savedRoutineExercises.addAll(savedExercises);
        }

        savedRoutineEntity = routineRepo.save(routineEntity);

        // Third, Build RoutineExercise join table
        List<RoutineExerciseRequest> userExercises = request.getExercises();
        for (RoutineExerciseRequest userExercise : userExercises) {
            String exerciseName = userExercise.getName();
            ExerciseEntity currentExerciseEntity = exerciseRepo.findByName(exerciseName).orElse(null);
            RoutineExercisesEntity routineExercisesEntity = new RoutineExercisesEntity();
            routineExercisesEntity.setExerciseEntity(currentExerciseEntity);
            routineExercisesEntity.setRoutineEntity(savedRoutineEntity);
            routineExercisesEntity.setWeight(userExercise.getWeight());
            routineExercisesEntity.setSets(userExercise.getSets());
            routineExercisesEntity.setQuantity(userExercise.getQuantity());
            routineExercisesEntity.setQuantityUnit(userExercise.getQuantityUnit());
            routineExercisesRepo.save(routineExercisesEntity);
        }

        return "Successfully created new routine";
    }
}
