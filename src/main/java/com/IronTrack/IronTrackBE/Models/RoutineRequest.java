package com.IronTrack.IronTrackBE.Models;

import com.IronTrack.IronTrackBE.Models.Exercise;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoutineRequest {
    private Integer userId;
    private String name;

}
