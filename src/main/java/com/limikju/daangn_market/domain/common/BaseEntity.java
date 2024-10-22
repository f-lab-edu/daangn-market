package com.limikju.daangn_market.domain.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class BaseEntity implements Serializable {

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime updatedAt;
}
