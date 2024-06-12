package com.frogkim93.stationsystemapi.station.dto;

import com.frogkim93.stationsystemapi.model.Drone;
import com.frogkim93.stationsystemapi.model.Station;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StationDto {
    private int seq;
    private String name;
    private int status;
    private double latitude;
    private double longitude;
    private DroneDto drone;
    private LocalDateTime createdAt;

    @Builder
    private StationDto(Station stationEntity, Drone droneEntity) {
        seq = stationEntity.getSeq();
        name = stationEntity.getName();
        status = stationEntity.getStatus().ordinal();
        latitude = stationEntity.getLatitude();
        longitude = stationEntity.getLongitude();
        createdAt = stationEntity.getCreatedAt();
        drone = DroneDto.builder()
                .droneEntity(droneEntity)
                .build();
    }
}
