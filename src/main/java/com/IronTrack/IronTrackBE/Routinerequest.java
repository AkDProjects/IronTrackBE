package com.IronTrack.IronTrackBE;

import com.IronTrack.IronTrackBE.Models.Exercise;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Routinerequest {
    private String name;

    private List<Exercise> exercises;

}
