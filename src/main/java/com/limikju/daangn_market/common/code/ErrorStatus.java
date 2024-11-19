package com.limikju.daangn_market.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus {

  // 일반적인 응답
  _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
  _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
  _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
  _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러 입니다."),

  // 멤버 관련 에러
  MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER4001", "존재하지 않는 사용자 입니다."),
  MEMBER_INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "MEMBER4002", "비밀번호가 옳지 않습니다."),
  MEMBER_EMAIL_ALREADY_EXIST(HttpStatus.CONFLICT, "MEMBER4003", "이미 존재하는 사용자입니다."),

  // 카테고리 관련 에러
  CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "CATEGORY4001", "존재하지 않는 카테고리 입니다."),
  CATEGORY_HAS_CHILD(HttpStatus.CONFLICT, "CATEGORY4002", "하위 카테고리가 존재합니다."),
  ;

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  public ErrorReasonDTO getReasonHttpStatus() {
    return ErrorReasonDTO.builder()
        .message(message)
        .code(code)
        .isSuccess(false)
        .httpStatus(httpStatus)
        .build();
  }
}
