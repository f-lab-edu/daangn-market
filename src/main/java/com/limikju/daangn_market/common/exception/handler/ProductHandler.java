package com.limikju.daangn_market.common.exception.handler;

import com.limikju.daangn_market.common.code.ErrorStatus;
import com.limikju.daangn_market.common.exception.GeneralException;

public class ProductHandler extends GeneralException {

  public ProductHandler(ErrorStatus errorCode) {
    super(errorCode);
  }
}
