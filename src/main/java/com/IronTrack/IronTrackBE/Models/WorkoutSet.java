package com.IronTrack.IronTrackBE.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSet {
    private Long id;
    private Long workoutId;
    private RoutineExercise routineExercise;
    private Long sessionStart;
    private Long sessionEnd;
}
