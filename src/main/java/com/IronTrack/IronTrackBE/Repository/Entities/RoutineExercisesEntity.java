package com.IronTrack.IronTrackBE.Repository.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "routine_exercises")

@AllArgsConstructor
@NoArgsConstructor
public class RoutineExercisesEntity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    //primary key
    @Column
    private Integer id;
    @Column
    private Integer routine_id;
    @Column
    private Integer exercise_id;
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
