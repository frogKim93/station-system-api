package com.frogkim93.stationsystemapi.schedule.controller;

import com.frogkim93.stationsystemapi.login.service.LoginService;
import com.frogkim93.stationsystemapi.schedule.dto.CreateScheduleDto;
import com.frogkim93.stationsystemapi.schedule.dto.ScheduleDto;
import com.frogkim93.stationsystemapi.schedule.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private final LoginService loginService;

    @GetMapping
    private ResponseEntity<List<ScheduleDto>> getSchedules(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        HttpSession httpSession = httpServletRequest.getSession(false);

        if (httpSession == null || httpSession.getAttribute("memberSeq") == null) {
            int foundMemberSeq = loginService.getUserSeqInCookie(httpSession, httpServletRequest);

            if (foundMemberSeq > 0) {
                httpSession = httpServletRequest.getSession(true);
                httpSession.setAttribute("memberSeq", foundMemberSeq);
                httpSession.setMaxInactiveInterval(3600);

                loginService.updateCookie(foundMemberSeq, httpServletResponse);
                return scheduleService.getSchedules(foundMemberSeq);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping
    private ResponseEntity<Void> createSchedule(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody CreateScheduleDto createScheduleDto) {
        HttpSession httpSession = httpServletRequest.getSession(false);

        if (httpSession == null || httpSession.getAttribute("memberSeq") == null) {
            int foundMemberSeq = loginService.getUserSeqInCookie(httpSession, httpServletRequest);

            if (foundMemberSeq > 0) {
                httpSession = httpServletRequest.getSession(true);
                httpSession.setAttribute("memberSeq", foundMemberSeq);
                httpSession.setMaxInactiveInterval(3600);

                loginService.updateCookie(foundMemberSeq, httpServletResponse);
                return scheduleService.createSchedule(foundMemberSeq, createScheduleDto);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/{scheduleSeq}")
    private ResponseEntity<Void> updateSchedule(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable int scheduleSeq, @RequestBody CreateScheduleDto createScheduleDto) {
        HttpSession httpSession = httpServletRequest.getSession(false);

        if (httpSession == null || httpSession.getAttribute("memberSeq") == null) {
            int foundMemberSeq = loginService.getUserSeqInCookie(httpSession, httpServletRequest);

            if (foundMemberSeq > 0) {
                httpSession = httpServletRequest.getSession(true);
                httpSession.setAttribute("memberSeq", foundMemberSeq);
                httpSession.setMaxInactiveInterval(3600);

                loginService.updateCookie(foundMemberSeq, httpServletResponse);
                return scheduleService.updateSchedule(scheduleSeq, createScheduleDto);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/{scheduleSeq}")
    private ResponseEntity<Void> deleteSchedule(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable int scheduleSeq) {
        HttpSession httpSession = httpServletRequest.getSession(false);

        if (httpSession == null || httpSession.getAttribute("memberSeq") == null) {
            int foundMemberSeq = loginService.getUserSeqInCookie(httpSession, httpServletRequest);

            if (foundMemberSeq > 0) {
                httpSession = httpServletRequest.getSession(true);
                httpSession.setAttribute("memberSeq", foundMemberSeq);
                httpSession.setMaxInactiveInterval(3600);

                loginService.updateCookie(foundMemberSeq, httpServletResponse);
                return scheduleService.deleteSchedule(scheduleSeq);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
