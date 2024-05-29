package com.frogkim93.stationsystemapi.login.controller;

import com.frogkim93.stationsystemapi.login.dto.LoginDto;
import com.frogkim93.stationsystemapi.login.dto.MemberDto;
import com.frogkim93.stationsystemapi.login.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    private ResponseEntity<MemberDto> login(HttpServletRequest httpServletRequest, @RequestBody LoginDto loginDto, HttpServletResponse response) {
        HttpSession session = httpServletRequest.getSession();
        return loginService.login(session, loginDto);
    }
}
