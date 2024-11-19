package com.limikju.daangn_market.common.exception.handler;

import com.limikju.daangn_market.common.code.ErrorStatus;
import com.limikju.daangn_market.common.exception.GeneralException;

public class CategoryHandler extends GeneralException {

  public CategoryHandler(ErrorStatus errorCode) {
    super(errorCode);
  }
}
