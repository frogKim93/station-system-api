package com.frogkim93.stationsystemapi.station.service;

import com.frogkim93.stationsystemapi.model.Drone;
import com.frogkim93.stationsystemapi.model.Station;
import com.frogkim93.stationsystemapi.repository.DroneRepository;
import com.frogkim93.stationsystemapi.repository.StationRepository;
import com.frogkim93.stationsystemapi.station.dto.StationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StationService {
    private final StationRepository stationRepository;
    private final DroneRepository droneRepository;

    public ResponseEntity<List<StationDto>> getStations(int memberSeq) {
        List<Station> foundStations = stationRepository.findByMemberSeq(memberSeq);
        List<StationDto> stations = new ArrayList<>();

        for (Station station : foundStations) {
            Drone foundDrone = droneRepository.findByStationSeq(station.getSeq());
            stations.add(StationDto.builder()
                    .stationEntity(station)
                    .droneEntity(foundDrone)
                    .build());
        }

        return ResponseEntity.ok(stations);
    }
}
