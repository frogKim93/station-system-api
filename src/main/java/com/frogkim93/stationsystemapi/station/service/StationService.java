package com.frogkim93.stationsystemapi.station.service;

import com.frogkim93.stationsystemapi.model.Drone;
import com.frogkim93.stationsystemapi.model.Mission;
import com.frogkim93.stationsystemapi.model.Schedule;
import com.frogkim93.stationsystemapi.model.Station;
import com.frogkim93.stationsystemapi.repository.DroneRepository;
import com.frogkim93.stationsystemapi.repository.MissionRepository;
import com.frogkim93.stationsystemapi.repository.ScheduleRepository;
import com.frogkim93.stationsystemapi.repository.StationRepository;
import com.frogkim93.stationsystemapi.schedule.constants.ScheduleStatus;
import com.frogkim93.stationsystemapi.station.constants.RunningState;
import com.frogkim93.stationsystemapi.station.dto.RunningStationDto;
import com.frogkim93.stationsystemapi.station.dto.StationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StationService {
    private final StationRepository stationRepository;
    private final DroneRepository droneRepository;
    private final ScheduleRepository scheduleRepository;
    private final MissionRepository missionRepository;

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

    public ResponseEntity<Void> create(int memberSeq, StationDto stationDto) {
        Station station = Station.builder()
                .name(stationDto.getName())
                .memberSeq(memberSeq)
                .latitude(stationDto.getLatitude())
                .longitude(stationDto.getLongitude())
                .status(RunningState.IDLE)
                .createdAt(LocalDateTime.now())
                .build();

        station = stationRepository.saveAndFlush(station);

        Drone drone = Drone.builder()
                .stationSeq(station.getSeq())
                .latitude(station.getLatitude())
                .longitude(station.getLongitude())
                .name(stationDto.getDrone().getName())
                .build();

        droneRepository.saveAndFlush(drone);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<RunningStationDto>> getRunningStations(int memberSeq) {
        List<Station> foundStations = stationRepository.findByMemberSeq(memberSeq);
        List<RunningStationDto> stations = new ArrayList<>();

        for (Station station : foundStations) {
            if (station.getStatus() == RunningState.RUNNING) {
                Drone drone = droneRepository.findByStationSeq(station.getSeq());
                Schedule currentSchedule = scheduleRepository.findByStationSeqAndStatus(station.getSeq(), ScheduleStatus.STARTED);

                if (currentSchedule != null) {
                    Mission currentMission = missionRepository.findById(currentSchedule.getMissionSeq()).get();

                    RunningStationDto runningStation = RunningStationDto.rsBuilder()
                            .stationEntity(station)
                            .droneEntity(drone)
                            .mission(currentMission)
                            .rsBuild();

                    stations.add(runningStation);
                }
            }
        }

        return ResponseEntity.ok(stations);
    }
}
