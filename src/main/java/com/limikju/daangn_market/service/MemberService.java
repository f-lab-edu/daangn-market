package com.limikju.daangn_market.service;

import com.limikju.daangn_market.common.code.ErrorStatus;
import com.limikju.daangn_market.common.exception.handler.MemberHandler;
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
      throw new MemberHandler(ErrorStatus.MEMBER_EMAIL_ALREADY_EXIST);
    }
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    return memberRepository.findByEmail(email)
        .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
  }
}

