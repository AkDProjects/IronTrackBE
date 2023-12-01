package com.IronTrack.IronTrackBE.Controllers;

import com.IronTrack.IronTrackBE.Models.WorkoutSet;
import com.IronTrack.IronTrackBE.Services.WorkoutSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/workout/{workout_id}")
@RequiredArgsConstructor
public class WorkoutSetController {

    private final WorkoutSetService service;

    @PutMapping
    public ResponseEntity<?> endWorkoutSetSession(@RequestBody WorkoutSet workoutSet) {
        try {
            service.endWorkoutSetSession(workoutSet);
            return ResponseEntity.noContent().build();
        } catch (NullPointerException e) {
            ErrorResponse response = new ErrorResponse();
            response.setMessage(e.getMessage());
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (SecurityException e) {
            ErrorResponse response = new ErrorResponse();
            response.setMessage(e.getMessage());
            response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (ExceptionInInitializerError e) {
            ErrorResponse response = new ErrorResponse();
            response.setMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<?> createWorkoutSetSession(@PathVariable Long workout_id, @RequestBody WorkoutSet workoutSet) {
        try {
            WorkoutSet response = service.createWorkoutSetSession(workout_id, workoutSet);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (NullPointerException e) {
            ErrorResponse response = new ErrorResponse();
            response.setMessage(e.getMessage());
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (SecurityException e) {
            ErrorResponse response = new ErrorResponse();
            response.setMessage(e.getMessage());
            response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (ExceptionInInitializerError e) {
            ErrorResponse response = new ErrorResponse();
            response.setMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
    }
}
