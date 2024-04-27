package com.frogkim93.stationsystemapi.login.service;

import com.frogkim93.stationsystemapi.login.dto.LoginDto;
import com.frogkim93.stationsystemapi.login.dto.MemberDto;
import com.frogkim93.stationsystemapi.model.Member;
import com.frogkim93.stationsystemapi.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LoginService {
    private final MemberRepository memberRepository;

    public ResponseEntity<MemberDto> login(HttpSession httpSession, LoginDto loginDto) {
        Optional<Member> foundMember = memberRepository.findByMemberID(loginDto.getId());

        if (foundMember.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Member member = foundMember.get();
        String encryptedPassword = memberRepository.getEncryptedText(loginDto.getPassword());

        if (encryptedPassword.equals(member.getPassword())) {
            httpSession.setAttribute("memberSeq", member.getSeq());

            return ResponseEntity.ok(MemberDto.builder()
                    .id(member.getMemberID())
                    .name(member.getName())
                    .build());
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
