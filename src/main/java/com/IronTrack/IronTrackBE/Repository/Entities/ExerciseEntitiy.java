package com.IronTrack.IronTrackBE.Repository.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Exercises")
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseEntitiy {
    @Id
    @GeneratedValue
    private String id;
    @Column
    private String emaill;
    @Column
    private String type;
    @Column
    private String muscle;
    @Column
    private String instructions;
    //Add Weight, Sets, Reps, time and instructions

}
