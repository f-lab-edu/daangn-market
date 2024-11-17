package com.limikju.daangn_market.domain;

import com.limikju.daangn_market.domain.common.BaseEntity;

public class Image extends BaseEntity {

  //primary Key
  private Long id;

  //product id
  private Product product;

  //s3 url
  private String url;
}
