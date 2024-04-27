package com.frogkim93.stationsystemapi.signup.controller;

import com.frogkim93.stationsystemapi.signup.dto.SignUpDto;
import com.frogkim93.stationsystemapi.signup.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sign-up")
public class SignUpController {
    private final SignUpService signUpService;

    @PostMapping
    private ResponseEntity<Void> signUp(@RequestBody SignUpDto signUpDto) {
        return signUpService.signUp(signUpDto);
    }
}
