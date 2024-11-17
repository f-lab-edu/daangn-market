package com.limikju.daangn_market.service;

import com.limikju.daangn_market.domain.Category;
import com.limikju.daangn_market.domain.Member;
import com.limikju.daangn_market.domain.dto.ProductInfoDto;
import com.limikju.daangn_market.domain.dto.ProductSaveDto;
import com.limikju.daangn_market.repository.CategoryRepository;
import com.limikju.daangn_market.repository.MemberRepository;
import com.limikju.daangn_market.repository.ProductRepository;
import com.limikju.daangn_market.util.secutity.SecurityUtil;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final MemberRepository memberRepository;
  private final CategoryRepository categoryRepository;

  public void save(ProductSaveDto productSaveDto) {
    String categoryTitle = productSaveDto.getCategory();
    Category category = categoryRepository.findByTitle(categoryTitle).orElseThrow(()
        -> new IllegalArgumentException("CATEGORY_NOT_FOUND"));

    if (categoryRepository.hasChild(category.getId())) {
      throw new IllegalArgumentException("CATEGORY_HAS_CHILD");
    }

    Member owner = memberRepository.findByEmail(
        SecurityUtil.getLoginUsername()).orElseThrow(()
        -> new IllegalArgumentException("MEMBER_NOT_FOUND"));

    productRepository.save(owner.getId(), category.getId(), productSaveDto.getTitle(),
        productSaveDto.getContent(), productSaveDto.getPrice());
  }

  public ProductInfoDto findById(Long id) {
    ProductInfoDto productInfo = productRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("PRODUCT_NOT_FOUND"));
    return productInfo;
  }

  public Page<Map<String, Object>> getList(Pageable pageable) {

    List<Map<String, Object>> content = productRepository.getList(pageable);
    int total = productRepository.getListCount();

    return new PageImpl<>(content, pageable, total);
  }
}
