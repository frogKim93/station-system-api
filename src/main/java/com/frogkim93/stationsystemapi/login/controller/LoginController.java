package com.frogkim93.stationsystemapi.login.controller;

import com.frogkim93.stationsystemapi.login.dto.LoginDto;
import com.frogkim93.stationsystemapi.login.dto.MemberDto;
import com.frogkim93.stationsystemapi.login.service.LoginService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    private ResponseEntity<MemberDto> login(HttpSession httpSession, @RequestBody LoginDto loginDto) {
        return loginService.login(httpSession, loginDto);
    }
}
