package com.frogkim93.stationsystemapi.mission.service;

import com.frogkim93.stationsystemapi.mission.dto.MissionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {
    public ResponseEntity<List<MissionDto>> getMissions(int memberSeq) {
        return ResponseEntity.ok(new ArrayList<>());
    }
}
