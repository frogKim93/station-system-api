package com.frogkim93.stationsystemapi.mission.dto;

import com.frogkim93.stationsystemapi.mission.constants.MissionType;
import com.frogkim93.stationsystemapi.model.Mission;
import com.frogkim93.stationsystemapi.utils.JsonConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateMissionDto {
    private String name;
    private MissionType type;
    private PointDto mainPoint;
    private List<PointDto> points;
    private List<PointDto> ways;
    private double transverseRedundancy;
    private double longitudinalRedundancy;
    private double angle;

    public Mission convertToEntity() {
        return Mission.builder()
            .name(name)
            .type(type)
            .mainPoint(JsonConverter.convertObjectToString(mainPoint))
            .points(JsonConverter.convertObjectToString(points))
            .ways(JsonConverter.convertObjectToString(ways))
            .transverseRedundancy(transverseRedundancy)
            .longitudinalRedundancy(longitudinalRedundancy)
            .angle(angle)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }

    public Mission convertToEntity(Mission entity) {
        Mission updatedEntity = this.convertToEntity();
        updatedEntity.setSeq(entity.getSeq());
        updatedEntity.setMemberSeq(entity.getMemberSeq());
        updatedEntity.setCreatedAt(entity.getCreatedAt());

        return updatedEntity;
    }
}
