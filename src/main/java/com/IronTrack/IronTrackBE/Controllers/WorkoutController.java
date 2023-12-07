package com.IronTrack.IronTrackBE.Controllers;

import com.IronTrack.IronTrackBE.Models.SuccessResponse;
import com.IronTrack.IronTrackBE.Models.Workout;
import com.IronTrack.IronTrackBE.Services.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/workout")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService service;

    @PostMapping
    public ResponseEntity<?> createWorkoutSession(@RequestBody Workout workout) {
        try {
            Workout response = service.createWorkoutSession(workout);
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

    @PutMapping
    public ResponseEntity<?> setEndTime(@RequestBody Workout workout) {
        try {
            service.updateWorkoutSession(workout);
            SuccessResponse response = new SuccessResponse("Workout successfully updated", HttpStatus.NO_CONTENT.value());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (NullPointerException e) {
            ErrorResponse response = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (SecurityException e) {
            ErrorResponse response = new ErrorResponse(e.getMessage(), HttpStatus.UNAUTHORIZED.value());
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (ExceptionInInitializerError e) {
            ErrorResponse response = new ErrorResponse();
            response.setMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
    }
}
