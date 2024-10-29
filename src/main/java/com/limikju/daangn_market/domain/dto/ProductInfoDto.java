package com.limikju.daangn_market.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoDto {

  private Long id;
  private String title;
  private String content;
  private int price;
  private String category;
  private Long ownerId;
  private String createdDate;

  public Boolean checkOwner(Long memberId) {
    return this.ownerId.equals(memberId);
  }
}


