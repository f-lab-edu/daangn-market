package com.limikju.daangn_market.service;

import com.limikju.daangn_market.domain.dto.MemberDto;
import com.limikju.daangn_market.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(MemberDto memberDto) {
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        try {
            memberRepository.join(memberDto);
        } catch (Exception e) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
    }
}

