package com.IronTrack.IronTrackBE.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoutineExercise {
    private Optional<Long> id;
    private Exercise exercise;
    private String weight;
    private Integer sets;
    private Integer quantity;
    private String quantityUnit;
}
