package com.limikju.daangn_market.service;

import com.limikju.daangn_market.domain.Member;
import com.limikju.daangn_market.domain.dto.MemberSignUpDto;
import com.limikju.daangn_market.domain.enums.Role;
import com.limikju.daangn_market.repository.MemberRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class MemberServiceTest {

  @MockBean
  private MemberRepository memberRepository;

  @MockBean
  private MemberService memberService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  @DisplayName("회원 서비스 join")
  @Transactional
  void memberJoinTest() {
    //given
    MemberSignUpDto memberSignUpDto = MemberSignUpDto.builder()
        .email(UUID.randomUUID().toString() + "@gmail.com")
        .password("1234")
        .name("limikju")
        .phone("010-1234-5678")
        .address("서울시 강남구")
        .build();

    Member member = Member.builder()
        .id(1L)
        .email(memberSignUpDto.getEmail())
        .password(memberSignUpDto.getPassword())
        .name(memberSignUpDto.getName())
        .phone(memberSignUpDto.getPhone())
        .address(memberSignUpDto.getAddress())
        .role(Role.USER)
        .build();

    //when
    doNothing().when(memberService).join(any(MemberSignUpDto.class));
    when((memberRepository.findByEmail(any(String.class)))).thenReturn(Optional.of(member));

    //then
    memberService.join(memberSignUpDto);
    Member findMember = memberRepository.findByEmail(memberSignUpDto.getEmail()).orElseThrow();
    Assertions.assertThat(member.getEmail()).isEqualTo(findMember.getEmail());
    Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    Assertions.assertThat(member.getPhone()).isEqualTo(findMember.getPhone());
    Assertions.assertThat(member.getAddress()).isEqualTo(findMember.getAddress());

  }

  @Test
  @DisplayName("회원 서비스 비밀번호 암호화")
  @Transactional
  void memberInsertTest() {
    //given
    String password = "1234";
    MemberSignUpDto memberSignUpDto = MemberSignUpDto.builder()
        .email(UUID.randomUUID().toString() + "@gmail.com")
        .password(password)
        .name("limikju")
        .phone("010-1234-5678")
        .address("서울시 강남구")
        .build();

    Member member = Member.builder()
        .id(1L)
        .email(memberSignUpDto.getEmail())
        .password(passwordEncoder.encode(memberSignUpDto.getPassword()))
        .name(memberSignUpDto.getName())
        .phone(memberSignUpDto.getPhone())
        .address(memberSignUpDto.getAddress())
        .role(Role.USER)
        .build();

    //when
    doNothing().when(memberService).join(any(MemberSignUpDto.class));
    when((memberRepository.findByEmail(any(String.class)))).thenReturn(Optional.of(member));

    memberService.join(memberSignUpDto);
    Member findMember = memberRepository.findByEmail(memberSignUpDto.getEmail()).orElseThrow();

    //then
    Assertions.assertThat(password).isNotEqualTo(findMember.getPassword());
    Assertions.assertThat(password).isEqualTo(memberSignUpDto.getPassword());
  }
}