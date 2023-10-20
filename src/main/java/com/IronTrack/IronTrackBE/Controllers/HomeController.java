package com.IronTrack.IronTrackBE.Controllers;

import com.IronTrack.IronTrackBE.Models.*;
import com.IronTrack.IronTrackBE.Services.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routines")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService service;

    @GetMapping
    public ResponseEntity<GetRoutinesResponse> getRoutines() {
        List<Routine> routines = service.getRoutines();
        GetRoutinesResponse response = new GetRoutinesResponse(routines);
        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }

    @GetMapping("/{routineId}")
    public ResponseEntity<?> getRoutine(@PathVariable Long routineId) {
        try {
            Routine routine = service.getRoutine(routineId);
            GetRoutineResponse response = new GetRoutineResponse();
            response.setRoutine(routine);

            return ResponseEntity.ok(response);

        } catch (NullPointerException e) {
            ErrorResponse response = new ErrorResponse();
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Routine with ID: " + routineId + " was not found");

            return ResponseEntity.status(response.getStatusCode()).body(response);

        } catch (SecurityException e) {
            ErrorResponse response = new ErrorResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("Routine with ID: " + routineId + " not accessible");

            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
    }

    @PostMapping("/createRoutine")
    public ResponseEntity<CreateRoutineResponse> createRoutine(@RequestBody CreateRoutineRequest createRoutineRequest) {
        CreateRoutineResponse response = new CreateRoutineResponse();
        try {
            String message = service.createRoutine(createRoutineRequest);
            response.setMessage(message);
            response.setStatusCode(HttpStatus.CREATED.value());

        } catch (SecurityException e) {
            response.setMessage("You are not authorized");
            response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);

    };
}
