package com.limikju.daangn_market.repository;

import com.limikju.daangn_market.domain.Member;
import com.limikju.daangn_market.domain.dto.MemberDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberRepository {
//    @Insert("INSERT INTO MEMBER(email, password, name, phone, address, role) VALUES(#{email}, #{password}, #{name}, #{phone}, #{address}, #{role})")
    public void join(MemberDto memberDto);
    public Member findByEmail(String email);
}
