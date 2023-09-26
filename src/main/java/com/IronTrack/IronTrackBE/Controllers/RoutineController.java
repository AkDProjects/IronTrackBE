package com.IronTrack.IronTrackBE.Controllers;

import com.IronTrack.IronTrackBE.Models.Exercise;
import com.IronTrack.IronTrackBE.Models.RoutineCreation;
import com.IronTrack.IronTrackBE.Models.RoutineRequest;
import com.IronTrack.IronTrackBE.Repository.Entities.ExerciseEntitiy;
import com.IronTrack.IronTrackBE.Repository.Entities.RoutineEntitiy;
import com.IronTrack.IronTrackBE.Repository.Entities.RoutineExercisesEntity;
import com.IronTrack.IronTrackBE.Repository.ExerciseRepo;
import com.IronTrack.IronTrackBE.Repository.RoutineExerciseRepo;
import com.IronTrack.IronTrackBE.Repository.RoutineRepo;
import com.IronTrack.IronTrackBE.Services.IronService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Controller
//@ControllerAdvice
public class RoutineController {
    @Autowired
    RoutineRepo routineRepo;
    @Autowired
    RoutineExerciseRepo routineExerciseRepo;
    @Autowired
    ExerciseRepo exerciseRepo;
    @Autowired
    IronService ironService;


    @PostMapping("/createRoutine")
    //@CrossOrigin(origins = "http://localhost:4200")
    @ResponseBody
    public Boolean createRoutine(@RequestBody RoutineCreation creation, @ModelAttribute("addExerciseForm") RoutineEntitiy routinesFromUser) throws IOException {
        Optional<RoutineEntitiy> isCreationInDB = routineRepo.findFirstByName(creation.getName());
        if (isCreationInDB.isPresent()) {
            return false;
        } else {
            List<Exercise> exercisesFromUser = creation.getExercises();
            for (Exercise individualExerciseFromUser : exercisesFromUser
            ) {
                boolean exists = exerciseRepo.existsById(individualExerciseFromUser.getName());
                if (!exists) {

                    ironService.updateExercises(individualExerciseFromUser.getName());


                }

            }
            return true;
        }
    }

    @PostMapping("/home")
    @ResponseBody
    //@CrossOrigin(origins = "http://localhost:4200")
    public List<Exercise> getRoutineExercises(@RequestBody RoutineRequest request) {
        Optional<RoutineEntitiy> isRoutine = routineRepo.findFirstByName(request.getName());
        if (isRoutine.isPresent()) {
            RoutineEntitiy routine = isRoutine.get();
            List<String> exercise_id = routineExerciseRepo.findExercisesByRoutineID(routine.getId());
            List<ExerciseEntitiy> exercises = exerciseRepo.findAllById(exercise_id);
            List<RoutineExercisesEntity> routineExercises = routineExerciseRepo.findAllByRoutineId(routine.getId());
            List<Exercise> exerciseList = new ArrayList<>();
            for (RoutineExercisesEntity rt : routineExercises) {
                ExerciseEntitiy b = exercises.stream().filter(a -> Objects.equals(a.getName(), rt.getExercise_id())).findFirst().get();
                exerciseList.add(new Exercise(b, rt));

            }
            return exerciseList;

        }
        return Collections.emptyList();


    }
    @ResponseBody
    @GetMapping("/testRoutine")
    public void test() throws JsonProcessingException {
        //pass list of exercises from the user
         ironService.updateExercises("barbell curl");
    }
}
