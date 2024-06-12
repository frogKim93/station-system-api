package com.frogkim93.stationsystemapi.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateScheduleDto {
    private String name;
    private int stationSeq;
    private int missionSeq;
}
