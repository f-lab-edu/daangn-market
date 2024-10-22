package com.limikju.daangn_market.domain;

import com.limikju.daangn_market.domain.common.BaseEntity;
import com.limikju.daangn_market.domain.enums.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseEntity implements UserDetails {

  //primary Key
  private Long id;

  //email
  private String email;

  //비밀번호
  private String password;

  //이름
  private String name;

  //전화번호
  private String phone;

  //주소
  private String address;

  //권한 -> USER, ADMIN
  private Role role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    List<GrantedAuthority> roles = new ArrayList<>();
    roles.add(new SimpleGrantedAuthority(role.name()));

    return roles;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }
}
