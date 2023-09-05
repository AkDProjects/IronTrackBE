package com.IronTrack.IronTrackBE.Repository.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Routines")
public class RoutineEntitiy {
    @Id
    //Explained, Id will be for the specific object that's created in that table.
    @GeneratedValue
    private Long id;
    private String email;
    @Column(nullable = false, unique = false,length = 20)
    private String routineName;
    @Column(nullable = false, unique = false, length = 15)
    private String routineType;

}
