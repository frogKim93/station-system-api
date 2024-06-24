package com.frogkim93.stationsystemapi.schedule.controller;

import com.frogkim93.stationsystemapi.schedule.dto.CreateScheduleDto;
import com.frogkim93.stationsystemapi.schedule.dto.ScheduleDto;
import com.frogkim93.stationsystemapi.schedule.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping
    private ResponseEntity<List<ScheduleDto>> getSchedules(HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession(false);

        if (httpSession == null || httpSession.getAttribute("memberSeq") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return scheduleService.getSchedules((int) httpSession.getAttribute("memberSeq"));
    }

    @PostMapping
    private ResponseEntity<Void> createSchedule(HttpServletRequest httpServletRequest, @RequestBody CreateScheduleDto createScheduleDto) {
        HttpSession httpSession = httpServletRequest.getSession(false);

        if (httpSession == null || httpSession.getAttribute("memberSeq") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return scheduleService.createSchedule((int) httpSession.getAttribute("memberSeq"), createScheduleDto);
    }

    @PutMapping("/{scheduleSeq}")
    private ResponseEntity<Void> updateSchedule(HttpServletRequest httpServletRequest, @PathVariable int scheduleSeq, @RequestBody CreateScheduleDto createScheduleDto) {
        HttpSession httpSession = httpServletRequest.getSession(false);

        if (httpSession == null || httpSession.getAttribute("memberSeq") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return scheduleService.updateSchedule(scheduleSeq, createScheduleDto);
    }

    @DeleteMapping("/{scheduleSeq}")
    private ResponseEntity<Void> deleteSchdule(HttpServletRequest httpServletRequest, @PathVariable int scheduleSeq) {
        HttpSession httpSession = httpServletRequest.getSession(false);

        if (httpSession == null || httpSession.getAttribute("memberSeq") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return scheduleService.deleteSchedule(scheduleSeq);
    }
}
