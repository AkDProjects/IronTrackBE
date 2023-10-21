package com.IronTrack.IronTrackBE.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoutineExercise {
    public Long id;
    private Exercise exercise;
    private String weight;
    private Integer sets;
    private Integer quantity;
    private String quantityUnit;
}
