package com.frogkim93.stationsystemapi.mission;

import com.frogkim93.stationsystemapi.login.service.LoginService;
import com.frogkim93.stationsystemapi.mission.dto.CreateMissionDto;
import com.frogkim93.stationsystemapi.mission.dto.DetailMissionDto;
import com.frogkim93.stationsystemapi.mission.dto.MissionDto;
import com.frogkim93.stationsystemapi.mission.service.MissionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("mission")
@RequiredArgsConstructor
@Slf4j
public class MissionController {
    private final MissionService missionService;
    private final LoginService loginService;

    @GetMapping
    private ResponseEntity<List<MissionDto>> getMissions(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        HttpSession httpSession = httpServletRequest.getSession(false);

        if (httpSession == null || httpSession.getAttribute("memberSeq") == null) {
            int foundMemberSeq = loginService.getUserSeqInCookie(httpSession, httpServletRequest);

            if (foundMemberSeq > 0) {
                httpSession = httpServletRequest.getSession(true);
                httpSession.setAttribute("memberSeq", foundMemberSeq);
                httpSession.setMaxInactiveInterval(3600);

                loginService.updateCookie(foundMemberSeq, httpServletResponse);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }

        return missionService.getMissions((int) httpSession.getAttribute("memberSeq"));
    }

    @PostMapping
    private ResponseEntity<Void> createMission(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody CreateMissionDto createMissionDto) {
        HttpSession httpSession = httpServletRequest.getSession(false);

        if (httpSession == null || httpSession.getAttribute("memberSeq") == null) {
            int foundMemberSeq = loginService.getUserSeqInCookie(httpSession, httpServletRequest);

            if (foundMemberSeq > 0) {
                httpSession = httpServletRequest.getSession(true);
                httpSession.setAttribute("memberSeq", foundMemberSeq);
                httpSession.setMaxInactiveInterval(3600);

                loginService.updateCookie(foundMemberSeq, httpServletResponse);
                return missionService.create(foundMemberSeq, createMissionDto);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping(value = "/{missionSeq}")
    private ResponseEntity<DetailMissionDto> getMission(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable int missionSeq) {
        HttpSession httpSession = httpServletRequest.getSession(false);

        if (httpSession == null || httpSession.getAttribute("memberSeq") == null) {
            int foundMemberSeq = loginService.getUserSeqInCookie(httpSession, httpServletRequest);

            if (foundMemberSeq > 0) {
                httpSession = httpServletRequest.getSession(true);
                httpSession.setAttribute("memberSeq", foundMemberSeq);
                httpSession.setMaxInactiveInterval(3600);

                loginService.updateCookie(foundMemberSeq, httpServletResponse);
                return missionService.getMission(missionSeq);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping(value = "/{missionSeq}")
    private ResponseEntity<Void> updateMission(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable int missionSeq, @RequestBody CreateMissionDto createMissionDto) {
        HttpSession httpSession = httpServletRequest.getSession(false);

        if (httpSession == null || httpSession.getAttribute("memberSeq") == null) {
            int foundMemberSeq = loginService.getUserSeqInCookie(httpSession, httpServletRequest);

            if (foundMemberSeq > 0) {
                httpSession = httpServletRequest.getSession(true);
                httpSession.setAttribute("memberSeq", foundMemberSeq);
                httpSession.setMaxInactiveInterval(3600);

                loginService.updateCookie(foundMemberSeq, httpServletResponse);
                return missionService.update(missionSeq, createMissionDto);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping(value = "/{missionSeq}")
    private ResponseEntity<Void> deleteMission(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable int missionSeq) {
        HttpSession httpSession = httpServletRequest.getSession(false);

        if (httpSession == null || httpSession.getAttribute("memberSeq") == null) {
            int foundMemberSeq = loginService.getUserSeqInCookie(httpSession, httpServletRequest);

            if (foundMemberSeq > 0) {
                httpSession = httpServletRequest.getSession(true);
                httpSession.setAttribute("memberSeq", foundMemberSeq);
                httpSession.setMaxInactiveInterval(3600);

                loginService.updateCookie(foundMemberSeq, httpServletResponse);
                return missionService.delete(missionSeq);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
