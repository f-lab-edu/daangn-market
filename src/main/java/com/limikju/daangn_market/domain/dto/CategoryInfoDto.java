package com.limikju.daangn_market.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInfoDto {

  private Long id;

  private String title;

  private Long parentId;
}
