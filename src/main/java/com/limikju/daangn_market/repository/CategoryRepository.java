package com.limikju.daangn_market.repository;

import com.limikju.daangn_market.domain.dto.CategoryInfoDto;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryRepository {
  public boolean hasChild(Long id);

  public Optional<CategoryInfoDto> findByTitle(String title);

  public List<CategoryInfoDto> findAll();
}
