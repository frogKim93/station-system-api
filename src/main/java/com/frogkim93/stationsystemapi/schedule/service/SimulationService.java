package com.frogkim93.stationsystemapi.schedule.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.frogkim93.stationsystemapi.mission.dto.PointDto;
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
import com.frogkim93.stationsystemapi.utils.JsonConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class SimulationService {
    private final ScheduleRepository scheduleRepository;
    private final StationRepository stationRepository;
    private final DroneRepository droneRepository;
    private final MissionRepository missionRepository;

    @Async
    public void simulate(Schedule schedule) {
        Mission mission = missionRepository.findById(schedule.getMissionSeq()).get();
        Station station = stationRepository.findById(schedule.getStationSeq()).get();
        Drone drone = droneRepository.findByStationSeq(station.getSeq());

        ArrayList<PointDto> ways = JsonConverter.convertStringToObject(mission.getWays(), new TypeReference<>() {
        });

        station.setStatus(RunningState.RUNNING);
        station = stationRepository.saveAndFlush(station);

        double speed = 10;
        double targetHeight = ways.get(0).getHeight();
        PointDto homePoint = new PointDto(station.getLatitude(), station.getLongitude(), 0);

        for (double i = targetHeight; i > 0; i -= 5) {
            PointDto takeOffPoint = new PointDto(station.getLatitude(), station.getLongitude(), i);
            ways.addFirst(takeOffPoint);
        }

        ways.addFirst(homePoint);

        for (double i = targetHeight; i > 0; i -= 5) {
            PointDto takeOffPoint = new PointDto(station.getLatitude(), station.getLongitude(), i);
            ways.addLast(takeOffPoint);
        }

        ways.addLast(homePoint);

        int pointIndex = 0;
        while (pointIndex + 1 < ways.size()) {
            PointDto startPoint = ways.get(pointIndex);
            PointDto nextPoint = ways.get(pointIndex + 1);
            double distance = getDistance(startPoint, nextPoint);
            if (distance == 0) {
                distance = speed;
            }
            int needTime = (int) Math.ceil(distance / speed);

            for (int i = 0; i < needTime; i++) {
                double progress = (double) (i + 1) / needTime;
                double newLatitude = startPoint.getLatitude() + (nextPoint.getLatitude() - startPoint.getLatitude()) * progress;
                double newLongitude = startPoint.getLongitude() + (nextPoint.getLongitude() - startPoint.getLongitude()) * progress;
                double newHeight = startPoint.getHeight() + (nextPoint.getHeight() - startPoint.getHeight()) * progress;

                drone.setLatitude(newLatitude);
                drone.setLongitude(newLongitude);
                drone.setHeight(newHeight);

                droneRepository.saveAndFlush(drone);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            pointIndex++;
        }

        station.setStatus(RunningState.IDLE);
        stationRepository.saveAndFlush(station);

        schedule.setStatus(ScheduleStatus.COMPLETED);
        schedule.setCompletedAt(LocalDateTime.now());
        scheduleRepository.saveAndFlush(schedule);
    }

    private double getDistance(PointDto pointA, PointDto pointB) {
        double lat1Rad = Math.toRadians(pointA.getLatitude());
        double lon1Rad = Math.toRadians(pointA.getLongitude());
        double lat2Rad = Math.toRadians(pointB.getLatitude());
        double lon2Rad = Math.toRadians(pointB.getLongitude());

        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;
        double radius = 6371e3;

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return radius * c;
    }
}
