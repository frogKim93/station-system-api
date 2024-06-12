package com.frogkim93.stationsystemapi.schedule.service;

import com.frogkim93.stationsystemapi.model.Drone;
import com.frogkim93.stationsystemapi.model.Mission;
import com.frogkim93.stationsystemapi.model.Schedule;
import com.frogkim93.stationsystemapi.model.Station;
import com.frogkim93.stationsystemapi.repository.*;
import com.frogkim93.stationsystemapi.schedule.constants.ScheduleStatus;
import com.frogkim93.stationsystemapi.schedule.dto.CreateScheduleDto;
import com.frogkim93.stationsystemapi.schedule.dto.ScheduleDto;
import com.frogkim93.stationsystemapi.station.constants.RunningState;
import com.frogkim93.stationsystemapi.station.dto.StationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final StationRepository stationRepository;
    private final DroneRepository droneRepository;
    private final MissionRepository missionRepository;

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

    public ResponseEntity<Void> createSchedule(int memberSeq, CreateScheduleDto createScheduleDto) {
        if (memberRepository.findById(memberSeq).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Station> foundStation = stationRepository.findById(createScheduleDto.getStationSeq());
        Optional<Mission> foundMission = missionRepository.findById(createScheduleDto.getMissionSeq());

        if (foundStation.isEmpty() || foundMission.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (foundStation.get().getStatus() == RunningState.RUNNING) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Schedule schedule = Schedule.builder()
                .memberSeq(memberSeq)
                .stationSeq(createScheduleDto.getStationSeq())
                .missionSeq(createScheduleDto.getMissionSeq())
                .name(createScheduleDto.getName())
                .status(ScheduleStatus.STARTED)
                .createdAt(LocalDateTime.now())
                .completedAt(null)
                .build();

        scheduleRepository.saveAndFlush(schedule);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> updateSchedule(int scheduleSeq, CreateScheduleDto createScheduleDto) {
        Optional<Schedule> foundSchedule = scheduleRepository.findById(scheduleSeq);

        if (foundSchedule.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Schedule updatedSchedule = foundSchedule.get();
        updatedSchedule.setName(updatedSchedule.getName());

        scheduleRepository.saveAndFlush(updatedSchedule);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteSchedule(int scheduleSeq) {
        Optional<Schedule> foundSchedule = scheduleRepository.findById(scheduleSeq);

        if (foundSchedule.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Schedule schedule = foundSchedule.get();

        if (schedule.getStatus() == ScheduleStatus.STARTED) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        scheduleRepository.delete(schedule);

        return ResponseEntity.ok().build();
    }
}
