package com.limikju.daangn_market.repository;

import com.limikju.daangn_market.domain.Member;
import com.limikju.daangn_market.domain.dto.MemberSignUpDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {

  @Autowired
  private MemberRepository memberRepository;

  @Test
  @DisplayName("회원 레포지토리 삽입")
  @Transactional
  void memberJoinTest() {
    MemberSignUpDto memberSignUpDto = MemberSignUpDto.builder()
        .email(UUID.randomUUID().toString() + "@gmail.com")
        .password("1234")
        .name("limikju")
        .phone("010-1234-5678")
        .address("서울시 강남구")
        .build();
    memberRepository.join(memberSignUpDto);
  }

  @Test
  @DisplayName("회원 레포지토리 삽입 email 중복 실패")
  @Transactional
  void memberJoinEmailDuplicateTest() {
    // given
    String email = UUID.randomUUID().toString() + "@gmail.com";

    // when
    memberRepository.join(MemberSignUpDto.builder()
        .email(email)
        .password("1234")
        .name("limikju")
        .phone("010-1234-5678")
        .address("서울시 강남구")
        .build());

    // then
    assertThrows(Exception.class, () -> {
      memberRepository.join(MemberSignUpDto.builder()
          .email(email)
          .password("1234")
          .name("limikju")
          .phone("010-1234-5678")
          .address("서울시 강남구")
          .build());
    });
  }


  @Test
  @DisplayName("회원 조회")
  @Transactional
  void findByMemberTest() {
    // given
    String email = UUID.randomUUID().toString() + "@gmail.com";
    MemberSignUpDto memberSignUpDto = MemberSignUpDto.builder()
        .email(email)
        .password("1234")
        .name("limikju")
        .phone("010-1234-5678")
        .address("서울시 강남구")
        .build();

    // when
    memberRepository.join(memberSignUpDto);

    // then
    Member member = memberRepository.findByEmail(email).orElseThrow();
    assertThat(member.getEmail()).isEqualTo(memberSignUpDto.getEmail());
    assertThat(member.getPassword()).isEqualTo(memberSignUpDto.getPassword());
    assertThat(member.getName()).isEqualTo(memberSignUpDto.getName());
    assertThat(member.getPhone()).isEqualTo(memberSignUpDto.getPhone());
    assertThat(member.getAddress()).isEqualTo(memberSignUpDto.getAddress());
  }
}