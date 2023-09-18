package com.IronTrack.IronTrackBE.Models;

import com.IronTrack.IronTrackBE.Models.Exercise;
import lombok.Data;

import java.util.List;

@Data
public class RoutineCreation {
    private Integer userId;
    private String name;
    List<Exercise> exercises;
}
