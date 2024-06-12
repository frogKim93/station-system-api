package com.frogkim93.stationsystemapi.repository;

import com.frogkim93.stationsystemapi.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StationRepository extends JpaRepository<Station, Integer> {
    List<Station> findByMemberSeq(int memberSeq);
}
