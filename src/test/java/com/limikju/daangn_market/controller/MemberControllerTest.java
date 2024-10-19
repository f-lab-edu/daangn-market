package com.limikju.daangn_market.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.limikju.daangn_market.domain.Member;
import com.limikju.daangn_market.domain.dto.MemberSignUpDto;
import com.limikju.daangn_market.domain.enums.Role;
import com.limikju.daangn_market.repository.MemberRepository;
import com.limikju.daangn_market.service.MemberService;
import java.time.LocalDateTime;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

  @Autowired
  MockMvc mvc;
  @MockBean
  MemberRepository memberRepository;
  @MockBean
  MemberService memberService;

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
    when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
    doNothing().when(memberService).join(any(MemberSignUpDto.class));

    mvc.perform(post("/api/members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(memberSignUpDto)))
        .andExpect(status().isCreated());

    //then
    Member savedMember = memberRepository.findByEmail(memberSignUpDto.getEmail()).orElseThrow();
    Assertions.assertThat(savedMember.getEmail()).isEqualTo(memberSignUpDto.getEmail());
    Assertions.assertThat(savedMember.getName()).isEqualTo(memberSignUpDto.getName());
    Assertions.assertThat(savedMember.getPhone()).isEqualTo(memberSignUpDto.getPhone());
    Assertions.assertThat(savedMember.getAddress()).isEqualTo(memberSignUpDto.getAddress());
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