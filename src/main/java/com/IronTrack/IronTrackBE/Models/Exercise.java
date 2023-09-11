package com.IronTrack.IronTrackBE.Models;

import lombok.Data;

@Data
public class Exercise {
    private String name;
    private Double weight;
    private Integer sets;
    private Integer quantity;
    private String unit;
}
