package com.limikju.daangn_market.apiPayload.code.status;

import com.limikju.daangn_market.apiPayload.code.BaseErrorCode;
import com.limikju.daangn_market.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

  // 가장 일반적인 응답
  _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
  _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
  _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
  _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

  // 멤버 관련 에러
  MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER4000", "사용자를 찾을 수 없습니다."),
  MEMBER_EMAIL_ALREADY_EXIST(HttpStatus.CONFLICT, "MEMBER4001", "이미 존재하는 사용자입니다."),

  // 카테고리 관련 에러
  CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "CATEGORY4000", "카테고리를 찾을 수 없습니다."),
  CATEGORY_HAS_CHILD(HttpStatus.CONFLICT, "CATEGORY4001", "하위 카테고리가 존재합니다."),

  // 상품 관련 에러
  PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT4000", "상품을 찾을 수 없습니다."),
  PRODUCT_OWNER_MISMATCH(HttpStatus.FORBIDDEN, "PRODUCT4001", "상품 소유자가 아닙니다."),
  ;

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  @Override
  public ErrorReasonDTO getReason() {
    return ErrorReasonDTO.builder()
        .message(message)
        .code(code)
        .isSuccess(false)
        .build();
  }

  @Override
  public ErrorReasonDTO getReasonHttpStatus() {
    return ErrorReasonDTO.builder()
        .message(message)
        .code(code)
        .isSuccess(false)
        .httpStatus(httpStatus)
        .build()
        ;
  }
}
