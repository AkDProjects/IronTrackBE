package com.IronTrack.IronTrackBE.Controllers;

import com.IronTrack.IronTrackBE.Models.Exercise;
import com.IronTrack.IronTrackBE.Repository.Entities.ExerciseEntitiy;
import com.IronTrack.IronTrackBE.Repository.UserRepo;
import com.IronTrack.IronTrackBE.Routinerequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

@Controller
@ControllerAdvice
public class RoutineController {
    //whatever the create routine page is
    @PostMapping("/createRoutine")
    public Boolean createRoutine(@RequestBody Routinerequest request){
    //take info from body, add to routine_exercises table

    }


}
