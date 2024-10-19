package com.limikju.daangn_market.domain.common;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class BaseEntity {

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
