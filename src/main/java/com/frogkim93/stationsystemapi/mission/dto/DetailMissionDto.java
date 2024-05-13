package com.frogkim93.stationsystemapi.mission.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.frogkim93.stationsystemapi.model.Mission;
import com.frogkim93.stationsystemapi.utils.JsonConverter;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DetailMissionDto extends MissionDto {
    private List<PointDto> points;
    private List<PointDto> ways;
    private double transverseRedundancy;
    private double longitudinalRedundancy;
    private double angle;

    @Builder(builderMethodName = "DetailMissionBuilder")
    private DetailMissionDto(Mission entity) {
        setSeq(entity.getSeq());
        setName(entity.getName());
        setType(entity.getType().ordinal());
        setMainPoint(JsonConverter.convertStringToObject(entity.getMainPoint(), PointDto.class));
        setCreatedAt(entity.getCreatedAt());
        setUpdatedAt(entity.getUpdatedAt());

        points = JsonConverter.convertStringToObject(entity.getPoints(), new TypeReference<>() {});
        ways = JsonConverter.convertStringToObject(entity.getWays(), new TypeReference<>() {});
        transverseRedundancy = entity.getTransverseRedundancy();
        longitudinalRedundancy = entity.getLongitudinalRedundancy();
        angle = entity.getAngle();
    }
}
