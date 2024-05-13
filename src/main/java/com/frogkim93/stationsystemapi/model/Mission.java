package com.frogkim93.stationsystemapi.model;

import com.frogkim93.stationsystemapi.mission.constants.MissionType;
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
@Entity(name = "missions")
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column
    private int memberSeq;

    @Column
    private String name;

    @Column
    private MissionType type;

    @Column
    private String mainPoint;

    @Column
    private String points;

    @Column
    private String ways;

    @Column
    private double transverseRedundancy;

    @Column
    private double longitudinalRedundancy;

    @Column
    private double angle;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private LocalDateTime updatedAt;
}
