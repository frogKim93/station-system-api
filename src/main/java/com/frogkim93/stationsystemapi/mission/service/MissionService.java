package com.frogkim93.stationsystemapi.mission.service;

import com.frogkim93.stationsystemapi.mission.dto.CreateMissionDto;
import com.frogkim93.stationsystemapi.mission.dto.DetailMissionDto;
import com.frogkim93.stationsystemapi.mission.dto.MissionDto;
import com.frogkim93.stationsystemapi.model.Member;
import com.frogkim93.stationsystemapi.model.Mission;
import com.frogkim93.stationsystemapi.repository.MemberRepository;
import com.frogkim93.stationsystemapi.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MissionService {
    private final MemberRepository memberRepository;
    private final MissionRepository missionRepository;

    public ResponseEntity<List<MissionDto>> getMissions(int memberSeq) {
        List<MissionDto> result = new ArrayList<>();
        List<Mission> foundList = missionRepository.findAllByMemberSeqOrderByCreatedAtDesc(memberSeq);

        for (Mission mission : foundList) {
            result.add(
                MissionDto.builder()
                    .entity(mission)
                    .build()
            );
        }

        return ResponseEntity.ok(result);
    }

    public ResponseEntity<Void> create(int memberSeq, CreateMissionDto createMissionDto) {
        Optional<Member> foundMember = memberRepository.findById(memberSeq);

        if (foundMember.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Mission mission = createMissionDto.convertToEntity();
        mission.setMemberSeq(memberSeq);

        missionRepository.saveAndFlush(mission);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<DetailMissionDto> getMission(int missionSeq) {
        Optional<Mission> foundMission = missionRepository.findById(missionSeq);

        if (foundMission.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        DetailMissionDto detailMissionDto = DetailMissionDto.DetailMissionBuilder()
            .entity(foundMission.get())
            .build();

        return ResponseEntity.ok(detailMissionDto);
    }

    public ResponseEntity<Void> update(int missionSeq, CreateMissionDto createMissionDto) {
        Optional<Mission> foundMission = missionRepository.findById(missionSeq);

        if (foundMission.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Mission updatedMission = createMissionDto.convertToEntity(foundMission.get());
        missionRepository.saveAndFlush(updatedMission);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> delete(int missionSeq) {
        Optional<Mission> foundMission = missionRepository.findById(missionSeq);

        if (foundMission.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        missionRepository.delete(foundMission.get());

        return ResponseEntity.ok().build();
    }
}
