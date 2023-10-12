package com.IronTrack.IronTrackBE.Controllers;

import com.IronTrack.IronTrackBE.Models.CreateRoutineRequest;
import com.IronTrack.IronTrackBE.Models.CreateRoutineResponse;
import com.IronTrack.IronTrackBE.Services.HomeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService service;

    @PostMapping("/createRoutine")
    public ResponseEntity<CreateRoutineResponse> createRoutine(@RequestBody CreateRoutineRequest createRoutineRequest) {
        CreateRoutineResponse response = new CreateRoutineResponse();
        try {
            String message = service.createRoutine(createRoutineRequest);
            response.setMessage(message);
            response.setStatusCode(HttpStatus.CREATED.value());

        } catch (JsonProcessingException | RuntimeException e) {
            response.setMessage("Routine could not be created");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);

    };
}
