package com.IronTrack.IronTrackBE.Services;

import com.IronTrack.IronTrackBE.Repository.Entities.ExerciseEntity;
import com.IronTrack.IronTrackBE.Repository.ExerciseRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

@Service
public class ApiNinjasService {
    @Value("${API_KEY}")
    private String API_KEY;
    @Autowired
    ExerciseRepo exerciseRepo;
    public HttpResponse<String> getExercise(String exerciseName) throws JsonProcessingException {
        exerciseName = StringUtils.replace(exerciseName," ","_");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api.api-ninjas.com/v1/exercises?name="+exerciseName))
                .header("X-Api-Key", API_KEY)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return response;

    }


}
