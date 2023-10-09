package com.IronTrack.IronTrackBE.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoutineRequest {
    private Integer userId;
    //Maybe make a list of strings
    private String name;

}
