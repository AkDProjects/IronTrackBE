package com.IronTrack.IronTrackBE.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Routine {
    //Maybe make a list of strings
    private String name;
    private List<RoutineExercise> exercises;

}
