package com.limikju.daangn_market.apiPayload.code.status;

import com.limikju.daangn_market.apiPayload.code.BaseCode;
import com.limikju.daangn_market.apiPayload.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

  // 일반적인 응답
  _OK(HttpStatus.OK, "COMMON200", "성공입니다."),

  // 회원 관련 응답
  MEMBER_JOIN(HttpStatus.OK, "MEMBER2000", "회원 가입 성공"),

  // 상품 관련 응답
  PRODUCT_SAVE(HttpStatus.OK, "PRODUCT2000", "상품 저장 성공"),
  PRODUCT_GET_ONE(HttpStatus.OK, "PRODUCT2001", "상품 단건 조회 성공"),
  PRODUCT_GET_LIST(HttpStatus.OK, "PRODUCT2002", "상품 목록 조회 성공"),
  PRODUCT_UPDATE(HttpStatus.OK, "PRODUCT2003", "상품 수정 성공"),
  PRODUCT_DELETE(HttpStatus.OK, "PRODUCT2004", "상품 삭제 성공"),
  ;

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  @Override
  public ReasonDTO getReason() {
    return ReasonDTO.builder()
        .message(message)
        .code(code)
        .isSuccess(true)
        .build();
  }

  @Override
  public ReasonDTO getReasonHttpStatus() {
    return ReasonDTO.builder()
        .message(message)
        .code(code)
        .isSuccess(true)
        .httpStatus(httpStatus)
        .build();
  }
}
