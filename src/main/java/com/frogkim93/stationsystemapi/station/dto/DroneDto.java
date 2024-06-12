package com.frogkim93.stationsystemapi.station.dto;

import com.frogkim93.stationsystemapi.model.Drone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DroneDto {
    private int seq;
    private String name;
    private double latitude;
    private double longitude;

    @Builder
    private DroneDto(Drone droneEntity) {
        seq = droneEntity.getSeq();
        name = droneEntity.getName();
        latitude = droneEntity.getLatitude();
        longitude = droneEntity.getLongitude();
    }
}
