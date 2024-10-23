package com.limikju.daangn_market.service;

import com.limikju.daangn_market.domain.Member;
import com.limikju.daangn_market.domain.dto.MemberSignUpDto;
import com.limikju.daangn_market.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  public void join(MemberSignUpDto memberSignUpDto) {
    memberSignUpDto.setPassword(passwordEncoder.encode(memberSignUpDto.getPassword()));
    try {
      memberRepository.join(memberSignUpDto);
    } catch (DataIntegrityViolationException ex) {
      throw new IllegalArgumentException("이미 가입된 이메일입니다.");
    }
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    return memberRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용장 입니다."));
  }
}

