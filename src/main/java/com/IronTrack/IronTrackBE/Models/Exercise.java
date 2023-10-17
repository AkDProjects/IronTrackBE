package com.IronTrack.IronTrackBE.Models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Exercise {
    private Optional<Long> id;
    public String name;
    public String type;
    public String muscle;
    public String equipment;
    public String difficulty;
    public String instructions;
}
