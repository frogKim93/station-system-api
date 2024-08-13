package com.frogkim93.stationsystemapi.station.dto;

import com.frogkim93.stationsystemapi.mission.dto.DetailMissionDto;
import com.frogkim93.stationsystemapi.model.Drone;
import com.frogkim93.stationsystemapi.model.Mission;
import com.frogkim93.stationsystemapi.model.Station;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RunningStationDto extends StationDto {
    private DetailMissionDto currentMission;

    @Builder(builderMethodName = "rsBuilder", buildMethodName = "rsBuild")
    private RunningStationDto(Station stationEntity, Drone droneEntity, Mission mission) {
        setSeq(stationEntity.getSeq());
        setName(stationEntity.getName());
        setStatus(stationEntity.getStatus().ordinal());
        setLatitude(stationEntity.getLatitude());
        setLongitude(stationEntity.getLongitude());
        setDrone(DroneDto.builder()
                .droneEntity(droneEntity)
                .build());

        currentMission = DetailMissionDto.DetailMissionBuilder()
                .entity(mission)
                .build();
    }
}
