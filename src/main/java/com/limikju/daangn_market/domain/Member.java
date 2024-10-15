package com.limikju.daangn_market.domain;

import com.limikju.daangn_market.domain.common.BaseEntity;
import com.limikju.daangn_market.domain.enums.Role;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {

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
}
