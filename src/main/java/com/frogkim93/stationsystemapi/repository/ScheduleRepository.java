package com.frogkim93.stationsystemapi.repository;

import com.frogkim93.stationsystemapi.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findByMemberSeq(int memberSeq);
}
