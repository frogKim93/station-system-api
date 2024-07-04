package com.frogkim93.stationsystemapi.station.controller;

import com.frogkim93.stationsystemapi.login.service.LoginService;
import com.frogkim93.stationsystemapi.station.dto.RunningStationDto;
import com.frogkim93.stationsystemapi.station.dto.StationDto;
import com.frogkim93.stationsystemapi.station.service.StationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/station")
public class StationController {
    private final StationService stationService;
    private final LoginService loginService;

    @GetMapping
    private ResponseEntity<List<StationDto>> getStations(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        HttpSession httpSession = httpServletRequest.getSession(false);

        if (httpSession == null || httpSession.getAttribute("memberSeq") == null) {
            int foundMemberSeq = loginService.getUserSeqInCookie(httpServletRequest);

            if (foundMemberSeq > 0) {
                httpSession = httpServletRequest.getSession(true);
                httpSession.setAttribute("memberSeq", foundMemberSeq);
                httpSession.setMaxInactiveInterval(3600);

                loginService.updateCookie(foundMemberSeq, httpServletResponse);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }

        return stationService.getStations((int) httpSession.getAttribute("memberSeq"));
    }

    @PostMapping
    private ResponseEntity<Void> createStation(HttpServletRequest httpServletRequest, @RequestBody StationDto stationDto) {
        HttpSession httpSession = httpServletRequest.getSession(false);

        if (httpSession == null || httpSession.getAttribute("memberSeq") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return stationService.create((int) httpSession.getAttribute("memberSeq"), stationDto);
    }

    @GetMapping(value = "running")
    private ResponseEntity<List<RunningStationDto>> getRunningStations(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        HttpSession httpSession = httpServletRequest.getSession(false);

        if (httpSession == null || httpSession.getAttribute("memberSeq") == null) {
            int foundMemberSeq = loginService.getUserSeqInCookie(httpServletRequest);

            if (foundMemberSeq > 0) {
                httpSession = httpServletRequest.getSession(true);
                httpSession.setAttribute("memberSeq", foundMemberSeq);
                httpSession.setMaxInactiveInterval(3600);

                loginService.updateCookie(foundMemberSeq, httpServletResponse);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }

        return stationService.getRunningStations((int) httpSession.getAttribute("memberSeq"));
    }

    @GetMapping(value = "test")
    private ResponseEntity<List<RunningStationDto>> getRunningStationsTest() {
        System.out.println("test");
        return stationService.getRunningStations(5);
    }
}
