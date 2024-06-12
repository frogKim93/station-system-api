package com.frogkim93.stationsystemapi.repository;

import com.frogkim93.stationsystemapi.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneRepository extends JpaRepository<Drone, Integer> {
    Drone findByStationSeq(int stationSeq);
}
