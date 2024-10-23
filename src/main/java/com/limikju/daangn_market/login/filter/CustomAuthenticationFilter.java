package com.limikju.daangn_market.login.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.limikju.daangn_market.domain.dto.MemberLoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  private final ObjectMapper objectMapper = new ObjectMapper();

  public CustomAuthenticationFilter(String loginUrl) {
    super(new AntPathRequestMatcher(loginUrl, "POST"));
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException, IOException {

    // request 에서 MemberLoginDto 를 가져옴
    MemberLoginDto memberLoginDto = objectMapper.readValue(request.getReader(), MemberLoginDto.class);

    // email, password 가 있는지 확인
    if(!StringUtils.hasLength(memberLoginDto.getEmail())
        || !StringUtils.hasLength(memberLoginDto.getPassword())) {
      throw new IllegalArgumentException("이메일 또는 비밀번호가 없습니다.");
    }

    // 미인증 상태 토큰 생성
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
        memberLoginDto.getEmail(),
        memberLoginDto.getPassword()
    );

    // Manager 가 인증 처리
    return getAuthenticationManager().authenticate(token);
  }
}