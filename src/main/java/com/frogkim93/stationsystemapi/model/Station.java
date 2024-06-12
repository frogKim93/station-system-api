package com.frogkim93.stationsystemapi.model;

import com.frogkim93.stationsystemapi.station.constants.RunningState;
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
@Entity(name = "stations")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column
    private int memberSeq;

    @Column
    private String name;

    @Column
    private RunningState status;

    @Column
    private double latitude;

    @Column
    private double longitude;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private LocalDateTime createdAt;
}
