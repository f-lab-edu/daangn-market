package com.limikju.daangn_market.login.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {

    String message = "로그인에 성공했습니다";

    response.setCharacterEncoding("utf-8");
    response.setStatus(HttpStatus.OK.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    objectMapper.writeValue(response.getWriter(), message);
  }
}