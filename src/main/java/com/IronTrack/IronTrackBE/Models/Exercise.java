package com.IronTrack.IronTrackBE.Models;

import com.IronTrack.IronTrackBE.Repository.Entities.ExerciseEntitiy;
import com.IronTrack.IronTrackBE.Repository.Entities.RoutineExercisesEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Exercise {

    private String name;
    private Integer weight;
    private Integer sets;
    private Integer quantity;
    private String quantityUnit;

    public Exercise(ExerciseEntitiy entity, RoutineExercisesEntity rtEntity) {
        name = entity.getName();
        weight = rtEntity.getWeight();
        sets = rtEntity.getSets();
        quantity = rtEntity.getReps();
        quantityUnit = entity.getType();


    }
}
