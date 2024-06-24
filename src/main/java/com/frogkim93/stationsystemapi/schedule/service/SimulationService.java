package com.frogkim93.stationsystemapi.schedule.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.frogkim93.stationsystemapi.mission.dto.PointDto;
import com.frogkim93.stationsystemapi.model.Drone;
import com.frogkim93.stationsystemapi.model.Mission;
import com.frogkim93.stationsystemapi.model.Station;
import com.frogkim93.stationsystemapi.repository.DroneRepository;
import com.frogkim93.stationsystemapi.repository.StationRepository;
import com.frogkim93.stationsystemapi.station.constants.RunningState;
import com.frogkim93.stationsystemapi.utils.JsonConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class SimulationService {
    private final StationRepository stationRepository;
    private final DroneRepository droneRepository;

    @Async
    public void simulate(Station station, Drone drone, Mission mission) {
        ArrayList<PointDto> ways = JsonConverter.convertStringToObject(mission.getWays(), new TypeReference<>() {
        });

        station.setStatus(RunningState.RUNNING);
        station = stationRepository.saveAndFlush(station);

        double speed = 12;

        try {
            PointDto startPoint = ways.get(0);
            int currentIndex = 1;
            boolean isReturnToHome = false;

            while (true) {
                int nextIndex = currentIndex + 1;

                if (isReturnToHome) {
                    break;
                }

                if (ways.size() == nextIndex) {
                    isReturnToHome = true;
                    startPoint = ways.getLast();
                }

                PointDto nextPoint = isReturnToHome ? new PointDto(station.getLatitude(), station.getLongitude(), 0) : ways.get(nextIndex);
                double distance = getDistance(startPoint, nextPoint);
                int needTime = (int) Math.ceil(distance / speed);

                for (int i = 0; i < needTime; i++) {
                    double progress = (double) (i + 1) / needTime;
                    double newLatitude = startPoint.getLatitude() + (nextPoint.getLatitude() - startPoint.getLatitude()) * progress;
                    double newLongitude = startPoint.getLongitude() + (nextPoint.getLongitude() - startPoint.getLongitude()) * progress;

                    drone.setLatitude(newLatitude);
                    drone.setLongitude(newLongitude);

                    droneRepository.saveAndFlush(drone);

                    Thread.sleep(1000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            station.setStatus(RunningState.IDLE);
            stationRepository.saveAndFlush(station);
        }
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
