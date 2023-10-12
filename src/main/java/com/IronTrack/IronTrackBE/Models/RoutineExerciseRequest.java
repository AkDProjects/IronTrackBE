package com.IronTrack.IronTrackBE.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoutineExerciseRequest {
    private String name;
    private String weight;
    private Integer sets;
    private Integer quantity;
    private String quantityUnit;
}
