package com.frogkim93.stationsystemapi.model;

import com.frogkim93.stationsystemapi.schedule.constants.ScheduleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column
    private int memberSeq;

    @Column
    private int stationSeq;

    @Column
    private int missionSeq;

    @Column
    private String name;

    @Column
    private ScheduleStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private LocalDateTime completedAt;
}
