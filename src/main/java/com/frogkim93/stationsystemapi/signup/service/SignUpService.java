package com.frogkim93.stationsystemapi.signup.service;

import com.frogkim93.stationsystemapi.model.Member;
import com.frogkim93.stationsystemapi.repository.MemberRepository;
import com.frogkim93.stationsystemapi.signup.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class SignUpService {
    private final MemberRepository memberRepository;

    public ResponseEntity<Void> signUp(SignUpDto signUpDto) {
        if (isDuplicatedID(signUpDto.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        memberRepository.save(Member.builder()
                .memberID(signUpDto.getId())
                .password(memberRepository.getEncryptedText(signUpDto.getPassword()))
                .name(signUpDto.getName())
                .createdAt(LocalDateTime.now())
                .build()
        );

        return ResponseEntity.ok().build();
    }

    public boolean isDuplicatedID(String id) {
        return memberRepository.findByMemberID(id).isPresent();
    }


}
