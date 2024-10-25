package com.limikju.daangn_market.repository;

import com.limikju.daangn_market.domain.Product;
import com.limikju.daangn_market.domain.dto.ProductSaveDto;
import com.limikju.daangn_market.domain.dto.ProductUpdateDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductRepository {
  public void save(Long ownerId, ProductSaveDto productSaveDto);
  public Product findById(Long id);
  public void updateStatus(Long id);
  public void updateProduct(ProductUpdateDto productUpdateDto);
  public void updateSortingTimestamp(Long id);
}
