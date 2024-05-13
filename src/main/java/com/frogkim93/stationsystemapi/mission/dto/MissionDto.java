package com.frogkim93.stationsystemapi.mission.dto;

import com.frogkim93.stationsystemapi.model.Mission;
import com.frogkim93.stationsystemapi.utils.JsonConverter;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MissionDto {
    private int seq;
    private String name;
    private int type;
    private PointDto mainPoint;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    private MissionDto(Mission entity) {
        seq = entity.getSeq();
        name = entity.getName();
        type = entity.getType().ordinal();
        mainPoint = JsonConverter.convertStringToObject(entity.getMainPoint(), PointDto.class);
        createdAt = entity.getCreatedAt();
        updatedAt = entity.getUpdatedAt();
    }
}
