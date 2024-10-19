package com.limikju.daangn_market.service;

import com.limikju.daangn_market.domain.Member;
import com.limikju.daangn_market.domain.dto.MemberSignUpDto;
import com.limikju.daangn_market.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private MemberService memberService;

  @Test
  @DisplayName("회원 서비스 join")
  @Transactional
  void memberJoinTest() {
    MemberSignUpDto memberSignUpDto = MemberSignUpDto.builder()
        .email(UUID.randomUUID().toString() + "@gmail.com")
        .password("1234")
        .name("limikju")
        .phone("010-1234-5678")
        .address("서울시 강남구")
        .build();
    memberService.join(memberSignUpDto);
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
    memberService.join(memberSignUpDto);

    //when
    Member member = memberRepository.findByEmail(memberSignUpDto.getEmail()).orElseThrow();

    //then
    assertNotEquals(password, member.getPassword());
    assertNotEquals(password, memberSignUpDto.getPassword());
  }
}