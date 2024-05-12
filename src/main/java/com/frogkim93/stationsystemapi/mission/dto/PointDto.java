package com.frogkim93.stationsystemapi.mission.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PointDto {
    private double latitude;
    private double longitude;
    private double height;
}
