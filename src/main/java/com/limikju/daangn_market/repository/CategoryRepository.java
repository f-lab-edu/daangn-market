package com.limikju.daangn_market.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryRepository {
  public boolean hasChild(Long id);
}
