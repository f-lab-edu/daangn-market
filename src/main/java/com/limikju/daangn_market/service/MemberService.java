package com.limikju.daangn_market.service;

import com.limikju.daangn_market.domain.Member;
import com.limikju.daangn_market.domain.dto.MemberSignUpDto;
import com.limikju.daangn_market.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(MemberSignUpDto memberSignUpDto) {
        memberSignUpDto.setPassword(passwordEncoder.encode(memberSignUpDto.getPassword()));
        try {
            memberRepository.join(memberSignUpDto);
        } catch (Exception e) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
    }
}

