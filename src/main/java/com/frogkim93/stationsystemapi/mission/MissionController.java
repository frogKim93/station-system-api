package com.frogkim93.stationsystemapi.mission;

import com.frogkim93.stationsystemapi.mission.dto.MissionDto;
import com.frogkim93.stationsystemapi.mission.service.MissionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        return missionService.getMissions((int)foundMemberSeq);
    }
}
