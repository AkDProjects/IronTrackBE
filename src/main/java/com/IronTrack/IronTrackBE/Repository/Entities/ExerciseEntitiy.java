package com.IronTrack.IronTrackBE.Repository.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "exercises")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ExerciseEntitiy {

    @Id
    @Column
    private String name;
    @Column
    private String type;
    @Column
    private String muscle;
    @Column
    private String instructions;
    @Column
    private String equipment;
    @Column
    private String difficulty;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ExerciseEntitiy that = (ExerciseEntitiy) o;
        return name != null && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
