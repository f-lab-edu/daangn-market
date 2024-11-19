package com.limikju.daangn_market.domain;

import com.limikju.daangn_market.domain.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends BaseEntity {

  //primary Key
  private Long id;

  //카테고리 이름
  private String title;

  //부모 카테고리 id
  private Long parentId;
}
