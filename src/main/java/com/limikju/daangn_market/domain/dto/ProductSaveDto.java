package com.limikju.daangn_market.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSaveDto {

  @NotBlank(message = "카테고리를 입력해주세요")
  public String category;

  @NotBlank(message = "제목을 입력해주세요")
  public String title;

  public String content;

  @NotNull(message = "가격을 입력해주세요")
  @Positive(message = "가격은 양수이어야 합니다.")
  public int price;
}
