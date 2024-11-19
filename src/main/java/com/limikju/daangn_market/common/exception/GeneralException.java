package com.limikju.daangn_market.common.exception;

import com.limikju.daangn_market.common.code.ErrorReasonDTO;
import com.limikju.daangn_market.common.code.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

  private ErrorStatus code;

  public ErrorReasonDTO getErrorReasonHttpStatus() {
    return this.code.getReasonHttpStatus();
  }
}