package com.limikju.daangn_market.domain;

import com.limikju.daangn_market.domain.common.BaseEntity;
import com.limikju.daangn_market.domain.enums.ProductStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {

  //primary Key
  private Long id;

  //Owner
  private Member owner;

  //카테고리
  private Category category;

  //title
  private String title;

  //body
  private String body;

  //status
  private ProductStatus status;

  //가격
  private int price;

  //이미지
  private List<Image> images;

  //조회수
  private int viewCount;

  //정렬 타임스탬프
  private LocalDateTime sortingTimestamp;
}
