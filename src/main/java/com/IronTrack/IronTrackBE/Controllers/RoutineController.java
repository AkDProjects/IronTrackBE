package com.IronTrack.IronTrackBE.Controllers;

import com.IronTrack.IronTrackBE.Models.Exercise;
import com.IronTrack.IronTrackBE.Repository.Entities.ExerciseEntitiy;
import com.IronTrack.IronTrackBE.Repository.Entities.RoutineEntitiy;
import com.IronTrack.IronTrackBE.Repository.Entities.RoutineExercisesEntity;
import com.IronTrack.IronTrackBE.Repository.ExerciseRepo;
import com.IronTrack.IronTrackBE.Repository.RoutineExerciseRepo;
import com.IronTrack.IronTrackBE.Repository.RoutineRepo;
import com.IronTrack.IronTrackBE.Models.RoutineCreation;
import com.IronTrack.IronTrackBE.Models.RoutineRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@ControllerAdvice
public class RoutineController {
    @Autowired
    RoutineRepo routine;
    @Autowired
    RoutineExerciseRepo routineExercise;
    @Autowired
    ExerciseRepo exerciseRepo;

    //whatever the create routine page is
    @PostMapping("/createRoutine")
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseBody
    public Boolean createRoutine(@RequestBody RoutineCreation request) {


        return false;
    }
    @PostMapping("/home")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Exercise> getRoutineExercises(@RequestBody RoutineRequest request) {
        Optional<RoutineEntitiy> isRoutine = routine.findFirstByName(request.getName());
        if (isRoutine.isPresent()) {
            RoutineEntitiy routine = isRoutine.get();
            List<Integer> exercise_id = routineExercise.findExercisesByRoutineID(routine.getId());
            List<ExerciseEntitiy> exercises = exerciseRepo.findAllById(exercise_id);
            List<RoutineExercisesEntity> rtExercises = routineExercise.findAllByRoutine_id(routine.getId());
            List<Exercise> exerciseList = new ArrayList<>();
            for (RoutineExercisesEntity rt : rtExercises) {
                ExerciseEntitiy b = exercises.stream().filter(a -> Objects.equals(a.getId(), rt.getExercise_id())).findFirst().get();
                exerciseList.add(new Exercise(b, rt));

            }


        }
        return exerciseList;

    }


}
