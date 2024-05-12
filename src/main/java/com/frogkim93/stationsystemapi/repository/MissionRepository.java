package com.frogkim93.stationsystemapi.repository;

import com.frogkim93.stationsystemapi.model.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Integer> {
}
