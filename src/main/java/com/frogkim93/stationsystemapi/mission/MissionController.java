package com.frogkim93.stationsystemapi.mission;

import com.frogkim93.stationsystemapi.mission.dto.CreateMissionDto;
import com.frogkim93.stationsystemapi.mission.dto.DetailMissionDto;
import com.frogkim93.stationsystemapi.mission.dto.MissionDto;
import com.frogkim93.stationsystemapi.mission.service.MissionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("mission")
@RequiredArgsConstructor
public class MissionController {
    private final MissionService missionService;

    @GetMapping
    private ResponseEntity<List<MissionDto>> getMissions(HttpSession httpSession) {
        Object foundMemberSeq = httpSession.getAttribute("memberSeq");
        if (foundMemberSeq == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return missionService.getMissions((int) foundMemberSeq);
    }

    @PostMapping
    private ResponseEntity<Void> createMission(HttpSession httpSession, @RequestBody CreateMissionDto createMissionDto) {
        Object foundMemberSeq = httpSession.getAttribute("memberSeq");
        if (foundMemberSeq == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return missionService.create((int) foundMemberSeq, createMissionDto);
    }

    @GetMapping(value = "/{missionSeq}")
    private ResponseEntity<DetailMissionDto> getMission(HttpSession httpSession, @PathVariable int missionSeq) {
        Object foundMemberSeq = httpSession.getAttribute("memberSeq");
        if (foundMemberSeq == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return missionService.getMission(missionSeq);
    }

    @PutMapping(value = "/{missionSeq}")
    private ResponseEntity<Void> updateMission(HttpSession httpSession, @PathVariable int missionSeq, @RequestBody CreateMissionDto createMissionDto) {
        Object foundMemberSeq = httpSession.getAttribute("memberSeq");
        if (foundMemberSeq == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return missionService.update(missionSeq, createMissionDto);
    }

    @DeleteMapping(value = "/{missionSeq}")
    private ResponseEntity<Void> deleteMission(HttpSession httpSession, @PathVariable int missionSeq) {
        Object foundMemberSeq = httpSession.getAttribute("memberSeq");
        if (foundMemberSeq == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return missionService.delete(missionSeq);
    }
}
