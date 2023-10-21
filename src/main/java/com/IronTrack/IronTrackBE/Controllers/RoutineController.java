package com.IronTrack.IronTrackBE.Controllers;

import com.IronTrack.IronTrackBE.Repository.Entities.RoutineExercisesEntity;
import com.IronTrack.IronTrackBE.Services.RoutineService
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/routines/{routine_id}")
@RequiredArgsConstructor
public class RoutineController {

    private final RoutineService service;

    @GetMapping("/edit/{routine_exercise_id}")
    public ResponseEntity<?> getRoutineExercise(
            @PathVariable("routine_id") Long routineId,
            @PathVariable("routine_exercise_id") Long routineExerciseId
    ) {
        ErrorResponse errorResponse= new ErrorResponse();
        RoutineExercisesEntity response;
        try {
            response = service.getRoutineExercise(routineId, routineExerciseId);
            return ResponseEntity.ok(response);
        } catch (NullPointerException e) {
            errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            errorResponse.setMessage(e.getMessage());
        } catch (SecurityException e) {
            errorResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            errorResponse.setMessage(e.getMessage());
        }

        return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
    }
}
