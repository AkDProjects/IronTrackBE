package com.IronTrack.IronTrackBE.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Routine {
    //Maybe make a list of strings
    private Long id;
    private String name;
    private List<RoutineExercise> exercises;

}
