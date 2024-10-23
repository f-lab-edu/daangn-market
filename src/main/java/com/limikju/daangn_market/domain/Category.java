package com.limikju.daangn_market.domain;

import com.limikju.daangn_market.domain.common.BaseEntity;

public class Category extends BaseEntity {

  //primary Key
  private Long id;

  //카테고리 이름
  private String title;

  //부모 카테고리
  private Category parent;
}
