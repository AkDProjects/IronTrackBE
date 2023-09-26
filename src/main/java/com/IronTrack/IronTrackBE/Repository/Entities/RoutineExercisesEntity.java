package com.IronTrack.IronTrackBE.Repository.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "routine_exercises")

@AllArgsConstructor
public class RoutineExercisesEntity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    //primary key
    @Column
    private Integer id;
    @Column(name = "routine_id")
    private Integer routineId;
    @Column
    private String exercise_id;
    @Column
    private Integer weight;
    @Column
    private Integer sets;
    @Column
    private Integer reps;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RoutineExercisesEntity that = (RoutineExercisesEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
