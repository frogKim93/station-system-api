package com.frogkim93.stationsystemapi.login.service;

import com.frogkim93.stationsystemapi.login.dto.LoginDto;
import com.frogkim93.stationsystemapi.login.dto.MemberDto;
import com.frogkim93.stationsystemapi.model.Member;
import com.frogkim93.stationsystemapi.repository.MemberRepository;
import com.frogkim93.stationsystemapi.utils.AESUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class LoginService {
    private final MemberRepository memberRepository;

    public ResponseEntity<MemberDto> login(HttpSession httpSession, LoginDto loginDto, HttpServletResponse response) {
        Optional<Member> foundMember = memberRepository.findByMemberID(loginDto.getId());

        if (foundMember.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Member member = foundMember.get();
        String encryptedPassword = memberRepository.getEncryptedText(loginDto.getPassword());

        if (encryptedPassword.equals(member.getPassword())) {
            httpSession.setAttribute("memberSeq", member.getSeq());
            httpSession.setMaxInactiveInterval(3600);

            updateCookie(member.getSeq(), response);

            return ResponseEntity.ok(MemberDto.builder()
                    .id(member.getMemberID())
                    .name(member.getName())
                    .build());
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public int getUserSeqInCookie(HttpServletRequest request) {
        Cookie foundCookie = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("station-simulate-auth")) {
                    foundCookie = cookie;
                    break;
                }
            }
        }

        if (foundCookie != null) {
            return AESUtils.decrypt(foundCookie.getValue());
        }

        return 0;
    }

    public void updateCookie(int memberSeq, HttpServletResponse servletResponse) {
        Cookie cookie = new Cookie("station-simulate-auth", AESUtils.encrypt(memberSeq));
        cookie.setMaxAge(60 * 60 * 8);
        cookie.setPath("/");
        servletResponse.addCookie(cookie);
    }
}
