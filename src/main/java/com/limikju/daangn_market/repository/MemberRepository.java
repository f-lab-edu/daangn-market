package com.limikju.daangn_market.repository;

import com.limikju.daangn_market.domain.Member;
import com.limikju.daangn_market.domain.dto.MemberSignUpDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberRepository {

  //    @Insert("INSERT INTO MEMBER(email, password, name, phone, address, role) VALUES(#{email}, #{password}, #{name}, #{phone}, #{address}, #{role})")
  public void join(MemberSignUpDto memberSignUpDto);

  public Optional<Member> findByEmail(String email);
}
