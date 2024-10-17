package com.limikju.daangn_market.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.limikju.daangn_market.domain.Member;
import com.limikju.daangn_market.domain.dto.MemberSignUpDto;
import com.limikju.daangn_market.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Nested
class MemberControllerTest {

  @Autowired
  MockMvc mvc;
  @Autowired
  MemberRepository memberRepository;

  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  @DisplayName("회원가입 성공")
  @Transactional
  void signUpTest() throws Exception {
    //given
    MemberSignUpDto memberSignUpDto = MemberSignUpDto.builder()
        .email(UUID.randomUUID().toString() + "@gmail.com")
        .password("Q1w2e3r4!")
        .name("limikju")
        .phone("010-1234-5678")
        .address("서울시 강남구 역삼동")
        .build();

    //when
    mvc.perform(
            post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberSignUpDto)))
        .andExpect(status().isCreated());

    //then
    Member member = memberRepository.findByEmail(memberSignUpDto.getEmail()).orElseThrow();
    Assertions.assertThat(memberSignUpDto.getEmail()).isEqualTo(member.getEmail());
    Assertions.assertThat(memberSignUpDto.getName()).isEqualTo(member.getName());
    Assertions.assertThat(memberSignUpDto.getPhone()).isEqualTo(member.getPhone());
    Assertions.assertThat(memberSignUpDto.getAddress()).isEqualTo(member.getAddress());
  }

  @Test
  @DisplayName("회원가입 실패 - 이메일")
  void signUpFailEmailTest() throws Exception {
    //given
    MemberSignUpDto memberSignUpDto = MemberSignUpDto.builder()
        .email(UUID.randomUUID().toString() + "gmail.com")
        .password("Q1w2e3r4!")
        .name("limikju")
        .phone("010-1234-5678")
        .address("서울시 강남구 역삼동")
        .build();

    //when, then
    mvc.perform(
            post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberSignUpDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("회원가입 실패 - 비밀번호")
  void signUpFailPasswordTest() throws Exception {
    //given
    MemberSignUpDto memberSignUpDto = MemberSignUpDto.builder()
        .email(UUID.randomUUID().toString() + "@gmail.com")
        .password("1234")
        .name("limikju")
        .phone("010-1234-5678")
        .address("서울시 강남구 역삼동")
        .build();

    //when, then
    mvc.perform(
            post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberSignUpDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("회원가입 실패 - 이름")
  void signUpFailNameTest() throws Exception {
    //given
    MemberSignUpDto memberSignUpDto = MemberSignUpDto.builder()
        .email(UUID.randomUUID().toString() + "@gmail.com")
        .password("Q1w2e3r4!")
        .name("김")
        .phone("010-1234-5678")
        .address("서울시 강남구 역삼동")
        .build();

    //when, then
    mvc.perform(
            post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberSignUpDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("회원가입 실패 - 전화번호")
  void signUpFailPhoneTest() throws Exception {
    //given
    MemberSignUpDto memberSignUpDto = MemberSignUpDto.builder()
        .email(UUID.randomUUID().toString() + "@gmail.com")
        .password("Q1w2e3r4!")
        .name("limikju")
        .phone("01012345678")
        .address("서울시 강남구 역삼동")
        .build();

    //when, then
    mvc.perform(
            post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberSignUpDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("회원가입 실패 - 주소")
  void signUpFailAdderssTest() throws Exception {
    //given
    MemberSignUpDto memberSignUpDto = MemberSignUpDto.builder()
        .email(UUID.randomUUID().toString() + "@gmail.com")
        .password("Q1w2e3r4!")
        .name("limikju")
        .phone("010-1234-5678")
        .address("서울시 강남구")
        .build();

    //when, then
    mvc.perform(
            post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberSignUpDto)))
        .andExpect(status().isBadRequest());
  }
}