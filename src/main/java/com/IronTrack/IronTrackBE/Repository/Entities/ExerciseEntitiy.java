package com.IronTrack.IronTrackBE.Repository.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "Exercise")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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
    //No longer adding those here, will add them in routine

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ExerciseEntitiy that = (ExerciseEntitiy) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
