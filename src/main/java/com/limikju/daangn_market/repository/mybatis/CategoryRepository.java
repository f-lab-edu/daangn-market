package com.limikju.daangn_market.repository.mybatis;

import com.limikju.daangn_market.domain.Category;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryRepository {
  public boolean hasChild(Long id);

  public Optional<Category> findByTitle(String title);
}
