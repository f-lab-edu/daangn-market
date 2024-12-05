package com.limikju.daangn_market.apiPayload.exception.handler;

import com.limikju.daangn_market.apiPayload.code.BaseErrorCode;
import com.limikju.daangn_market.apiPayload.exception.GeneralException;

public class MemberHandler extends GeneralException {

  public MemberHandler(BaseErrorCode errorCode) {
    super(errorCode);
  }
}
