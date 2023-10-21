package com.IronTrack.IronTrackBE.Models;

import com.IronTrack.IronTrackBE.Repository.Entities.RoutineExercisesEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetRoutineExerciseResponse {

    private RoutineExercisesEntity routineExericse;
}
