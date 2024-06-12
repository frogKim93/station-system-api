package com.frogkim93.stationsystemapi.schedule.dto;

import com.frogkim93.stationsystemapi.station.dto.StationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ScheduleDto {
    private int seq;
    private String name;
    private int status;
    private StationDto station;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
}
