package com.limikju.daangn_market.service;

import com.limikju.daangn_market.domain.Category;
import com.limikju.daangn_market.domain.Member;
import com.limikju.daangn_market.domain.dto.ProductSaveDto;
import com.limikju.daangn_market.repository.CategoryRepository;
import com.limikju.daangn_market.repository.MemberRepository;
import com.limikju.daangn_market.repository.ProductRepository;
import com.limikju.daangn_market.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final MemberRepository memberRepository;

  public void save(ProductSaveDto productSaveDto) {
    String categoryTitle = productSaveDto.getCategory();
    Category category = categoryRepository.findByTitle(categoryTitle).orElseThrow(()
        -> new IllegalArgumentException("CATEGORY_NOT_FOUND"));

    if (categoryRepository.hasChild(category.getId())) {
      throw new IllegalArgumentException("CATEGORY_HAS_CHILD");
    }

    Member owner = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    productRepository.save(owner.getId(), productSaveDto);
  }
}
