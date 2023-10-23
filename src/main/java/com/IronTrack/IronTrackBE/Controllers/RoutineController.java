package com.IronTrack.IronTrackBE.Controllers;

import com.IronTrack.IronTrackBE.Models.GetRoutineExerciseResponse;
import com.IronTrack.IronTrackBE.Models.RoutineExercise;
import com.IronTrack.IronTrackBE.Models.SuccessResponse;
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

    @GetMapping("/edit/{routine_exercise_id}")
    public ResponseEntity<?> getRoutineExercise(
            @PathVariable("routine_id") Long routineId,
            @PathVariable("routine_exercise_id") Long routineExerciseId
    ) {
        ErrorResponse errorResponse= new ErrorResponse();

        try {
            GetRoutineExerciseResponse response = new GetRoutineExerciseResponse();
            RoutineExercise routineExercise = service.getRoutineExercise(routineId, routineExerciseId);
            response.setRoutineExericse(routineExercise);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            errorResponse.setMessage(e.getMessage());
        } catch (IllegalArgumentException e) {
            errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            errorResponse.setMessage(e.getMessage());
        } catch (SecurityException e) {
            errorResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            errorResponse.setMessage(e.getMessage());
        }

        return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);


    }

    @PostMapping
    public ResponseEntity<?> addRoutineExercise(
            @PathVariable("routine_id") Long routineId,
            @RequestBody RoutineExercise routineExercise
    ) {
        ErrorResponse errorResponse= new ErrorResponse();

        try {
            SuccessResponse response = new SuccessResponse();
            service.addRoutineExercise(routineId, routineExercise);
            response.setMessage("Successfully saved new exercise");
            response.setStatusCode(HttpStatus.CREATED.value());
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (NoSuchElementException e) {
            errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            errorResponse.setMessage(e.getMessage());
        } catch (IllegalArgumentException e) {
            errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            errorResponse.setMessage(e.getMessage());
        } catch (SecurityException e) {
            errorResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            errorResponse.setMessage(e.getMessage());
        }

        return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);


    }
}
