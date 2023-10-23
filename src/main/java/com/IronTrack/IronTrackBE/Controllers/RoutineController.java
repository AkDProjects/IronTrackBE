package com.IronTrack.IronTrackBE.Controllers;

import com.IronTrack.IronTrackBE.Models.RoutineExercise;
import com.IronTrack.IronTrackBE.Services.RoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/routines/{routine_id}")
@RequiredArgsConstructor
public class RoutineController {

    private final RoutineService service;

    @PutMapping("/edit/{routine_exercise_id}")
    public ResponseEntity<?> updateRoutineExercise(
            @PathVariable("routine_id") Long routineId,
            @PathVariable("routine_exercise_id") Long routineExerciseId,
            @RequestBody RoutineExercise routineExercise
    ) {
        ErrorResponse errorResponse= new ErrorResponse();

        try {
            service.updateRoutineExercise(routineId, routineExerciseId, routineExercise);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            errorResponse.setMessage(e.getMessage());
        } catch (IllegalArgumentException e) {
            errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            errorResponse.setMessage(e.getMessage());
        } catch (SecurityException e) {
            errorResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            errorResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.setMessage(e.getMessage());
        }

        return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);


    }
}
