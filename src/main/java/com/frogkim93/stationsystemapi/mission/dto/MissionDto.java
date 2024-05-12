package com.frogkim93.stationsystemapi.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MissionDto {
    private int seq;
    private String name;
    private int type;
    private PointDto mainPoint;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
