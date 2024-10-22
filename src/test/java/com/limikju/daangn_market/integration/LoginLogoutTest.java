package com.limikju.daangn_market.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.limikju.daangn_market.domain.dto.MemberLoginDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginLogoutTest {

  @Autowired
  private MockMvc mvc;

  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  @DisplayName("로그인 성공")
  void loginTest() throws Exception {
    MemberLoginDto memberLoginDto = new MemberLoginDto("test@test.com","Q1w2e3r4!!");

    mvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(memberLoginDto))
        )
        .andExpect(status().isOk())
        .andExpect(content().string("\"로그인에 성공했습니다\""));
  }

  @Test
  @DisplayName("로그인 실패 - 이메일")
  void loginTestFailEmail() throws Exception {
    MemberLoginDto memberLoginDto = new MemberLoginDto("test@test.con","Q1w2e3r4!!");

    mvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(memberLoginDto))
        )
        .andExpect(status().isUnauthorized())
        .andExpect(content().string("\"이메일 또는 비밀번호가 일치하지 않습니다.\""));
  }

  @Test
  @DisplayName("로그인 실패 - 비밀번호")
  void loginTestFailPassword() throws Exception {
    MemberLoginDto memberLoginDto = new MemberLoginDto("test@test.com","Q1w2e3r4!!!");

    mvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(memberLoginDto))
        )
        .andExpect(status().isUnauthorized())
        .andExpect(content().string("\"이메일 또는 비밀번호가 일치하지 않습니다.\""));
  }

  @Test
  @DisplayName("로그아웃 성공")
  void logoutTest() throws Exception {

    mvc.perform(post("/api/logout")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());
  }
}
