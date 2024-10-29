package com.limikju.daangn_market.login;

import com.limikju.daangn_market.domain.Member;
import com.limikju.daangn_market.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private final MemberService memberService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    String loginId = authentication.getName();
    String password = (String) authentication.getCredentials();

    Member entity = (Member) memberService.loadUserByUsername(loginId);

    if(!passwordEncoder.matches(password, entity.getPassword())) {
      throw new BadCredentialsException("INVALID_PASSWORD");
    }

    return new CustomAuthenticationToken(entity, null, entity.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(CustomAuthenticationToken.class);
  }
}