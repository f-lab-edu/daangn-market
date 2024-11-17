package com.limikju.daangn_market.repository;

import com.limikju.daangn_market.domain.dto.ProductInfoDto;
import com.limikju.daangn_market.domain.dto.ProductUpdateDto;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;

@Mapper
public interface ProductRepository {
  public void save(Long ownerId, Long categoryId, String title, String content, int price);
  public Optional<ProductInfoDto> findById(Long id);
  List<Map<String, Object>> getList(Pageable pageable);
  int getListCount();
  public void updateStatus(Long id);
  public void updateProduct(ProductUpdateDto productUpdateDto);
  public void updateSortingTimestamp(Long id);
}
