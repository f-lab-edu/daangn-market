package com.limikju.daangn_market.domain.dto;

import com.limikju.daangn_market.domain.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Getter
public class MemberDto {

    //email
    @NotBlank(message = "아이디를 입력해주세요")
    @Size(min = 7, max = 25, message = "이메일은 7~25자 내외로 입력해주세요")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    //비밀번호
    @Setter
    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
            message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    private String password;

    //이름
    @NotBlank(message = "이름을 입력해주세요") @Size(min=2, message = "사용자 이름이 너무 짧습니다.")
    @Pattern(regexp = "^[A-Za-z가-힣]+$", message = "사용자 이름은 한글 또는 알파벳만 입력해주세요.")
    private String name;

    //전화번호
    @NotBlank
    private String phone;

    //주소
    @NotBlank
    @Size(min=, max=)
    private String address;
}
