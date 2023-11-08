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
@Table(name = "workout_set")
@AllArgsConstructor
public class WorkoutSetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "workout_id")
    private WorkoutEntity workoutEntity;
    @ManyToOne
    @JoinColumn(name = "routine_exercise_history_id")
    private RoutineExerciseHistoryEntity routineExerciseHistoryEntity;
    @Column
    private Long setStart;
    @Column
    private Long setEnd;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WorkoutSetEntity that = (WorkoutSetEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

