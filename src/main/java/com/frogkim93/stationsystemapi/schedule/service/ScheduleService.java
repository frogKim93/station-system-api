package com.frogkim93.stationsystemapi.schedule.service;

import com.frogkim93.stationsystemapi.model.Drone;
import com.frogkim93.stationsystemapi.model.Schedule;
import com.frogkim93.stationsystemapi.model.Station;
import com.frogkim93.stationsystemapi.repository.DroneRepository;
import com.frogkim93.stationsystemapi.repository.ScheduleRepository;
import com.frogkim93.stationsystemapi.repository.StationRepository;
import com.frogkim93.stationsystemapi.schedule.dto.ScheduleDto;
import com.frogkim93.stationsystemapi.station.dto.StationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final StationRepository stationRepository;
    private final DroneRepository droneRepository;

    public ResponseEntity<List<ScheduleDto>> getSchedules(int memberSeq) {
        List<Schedule> foundSchedules = scheduleRepository.findByMemberSeq(memberSeq);
        List<ScheduleDto> schedules = new ArrayList<>();

        for (Schedule schedule : foundSchedules) {
            Station foundStation = stationRepository.findById(schedule.getStationSeq()).get();
            Drone foundDrone = droneRepository.findByStationSeq(foundStation.getSeq());

            schedules.add(ScheduleDto.builder()
                    .seq(schedule.getSeq())
                    .name(schedule.getName())
                    .status(schedule.getStatus().ordinal())
                    .station(StationDto.builder()
                            .stationEntity(foundStation)
                            .droneEntity(foundDrone)
                            .build())
                    .startedAt(schedule.getCreatedAt())
                    .completedAt(schedule.getCompletedAt())
                    .build());
        }

        return ResponseEntity.ok(schedules);
    }
}
