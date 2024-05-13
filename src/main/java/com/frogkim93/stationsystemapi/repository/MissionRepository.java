package com.frogkim93.stationsystemapi.repository;

import com.frogkim93.stationsystemapi.model.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Integer> {
    List<Mission> findAllByMemberSeqOrderByCreatedAtDesc(int memberSeq);
}
