package com.limikju.daangn_market.domain.dto;

import com.limikju.daangn_market.domain.Category;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateDto {
  private Long id;

  public String category;

  public String title;

  public String content;

  @Positive(message = "가격은 양수이어야 합니다.")
  public int price;
}