package com.frogkim93.stationsystemapi.schedule.controller;

import com.frogkim93.stationsystemapi.schedule.dto.ScheduleDto;
import com.frogkim93.stationsystemapi.schedule.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping

    private ResponseEntity<List<ScheduleDto>> getStations(HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession(false);

        if (httpSession == null || httpSession.getAttribute("memberSeq") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return scheduleService.getSchedules((int) httpSession.getAttribute("memberSeq"));
    }
}
