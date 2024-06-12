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
@Entity(name = "drones")
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column
    private int stationSeq;

    @Column
    private String name;

    @Column
    private double latitude;

    @Column
    private double longitude;
}
