package com.frogkim93.stationsystemapi.station.controller;

import com.frogkim93.stationsystemapi.station.dto.StationDto;
import com.frogkim93.stationsystemapi.station.service.StationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/station")
public class StationController {
    private final StationService stationService;

    @GetMapping
    private ResponseEntity<List<StationDto>> getStations(HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession(false);

        if (httpSession == null || httpSession.getAttribute("memberSeq") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return stationService.getStations((int) httpSession.getAttribute("memberSeq"));
    }
}
