package com.IronTrack.IronTrackBE.Controllers;

import com.IronTrack.IronTrackBE.Models.CreateRoutineRequest;
import com.IronTrack.IronTrackBE.Models.CreateRoutineResponse;
import com.IronTrack.IronTrackBE.Services.HomeService;
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

        } catch (SecurityException e) {
            response.setMessage("You are not authorized");
            response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);

    };
}
