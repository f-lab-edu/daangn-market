package com.limikju.daangn_market.config;

import com.limikju.daangn_market.login.Handler.CustomAuthenticationFailureHandler;
import com.limikju.daangn_market.login.Handler.CustomAuthenticationSuccessHandler;
import com.limikju.daangn_market.login.filter.CustomAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
  private final AuthenticationConfiguration authenticationConfiguration;
  private final CustomAuthenticationFailureHandler customAuthenticationFailureHandlerHandler;

  private static final String signUpUrl = "/api/members";
  private static final String loginUrl = "/api/login";
  private static final String logoutUrl = "/api/logout";

  private static final String[] AUTH_WHITELIST = {
      signUpUrl,
      loginUrl,
      logoutUrl
  };

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .httpBasic(HttpBasicConfigurer::disable)
        .csrf(CsrfConfigurer::disable)
        .cors(CorsConfigurer::disable)
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(AUTH_WHITELIST).permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(ajaxAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public CustomAuthenticationFilter ajaxAuthenticationFilter() throws Exception {
    CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(loginUrl);
    customAuthenticationFilter.setAuthenticationManager(
        authenticationConfiguration.getAuthenticationManager()
    );
    customAuthenticationFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
    customAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandlerHandler);

    return customAuthenticationFilter;
  }
}