package com.limikju.daangn_market.domain.dto;

import com.limikju.daangn_market.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class MemberDto {

    //email
    private String email;

    //비밀번호
    @Setter
    private String password;

    //이름
    private String name;

    //전화번호
    private String phone;

    //주소
    private String address;

    //권한 -> USER, ADMIN
    private Role role;

}
