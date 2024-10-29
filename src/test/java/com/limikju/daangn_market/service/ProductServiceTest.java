package com.limikju.daangn_market.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.limikju.daangn_market.domain.Category;
import com.limikju.daangn_market.domain.Member;
import com.limikju.daangn_market.domain.dto.ProductInfoDto;
import com.limikju.daangn_market.domain.dto.ProductSaveDto;
import com.limikju.daangn_market.domain.dto.ProductUpdateDto;
import com.limikju.daangn_market.domain.enums.ProductStatus;
import com.limikju.daangn_market.domain.enums.Role;
import com.limikju.daangn_market.repository.CategoryRepository;
import com.limikju.daangn_market.repository.MemberRepository;
import com.limikju.daangn_market.repository.ProductRepository;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

  @Mock
  private ProductRepository productRepository;

  @Mock
  private CategoryRepository categoryRepository;

  @Mock
  private MemberRepository memberRepository;

  @InjectMocks
  private ProductService productService;

  private ProductSaveDto productSaveDto;
  private ProductInfoDto productInfoDto;
  private ProductUpdateDto productUpdateDto;
  private Category category;
  private Member member;

  @BeforeEach
  void setUp() {
    productSaveDto = ProductSaveDto.builder()
        .title("title")
        .content("content")
        .price(10000)
        .category("TestCategory")
        .build();

    category = Category.builder()
        .id(1L)
        .title("TestCategory")
        .build();

    member = Member.builder()
        .id(1L)
        .email(UUID.randomUUID().toString() + "@gmail.com")
        .password("1234")
        .name("limikju")
        .phone("010-1234-5678")
        .address("서울시 강남구")
        .role(Role.USER)
        .build();

    productInfoDto = ProductInfoDto.builder()
        .id(1L)
        .title("title")
        .content("content")
        .price(10000)
        .category("TestCategory")
        .ownerId(1L)
        .createdDate("2021-08-01")
        .build();

    productUpdateDto = ProductUpdateDto.builder()
        .id(1L)
        .title("title")
        .content("content")
        .price(10000)
        .category("TestCategory")
        .build();

    // SecurityContext 설정
    User user = new User("test@gmail.com", "password", new ArrayList<>());
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        user, null, user.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  @Test
  @DisplayName("상품 저장 성공")
  void productSaveTest() {
    // given
    when(categoryRepository.findByTitle("TestCategory")).thenReturn(Optional.of(category));
    when(categoryRepository.hasChild(category.getId())).thenReturn(false);
    when(memberRepository.findByEmail(any())).thenReturn(Optional.of(member));

    // when & then
    assertDoesNotThrow(() -> productService.save(productSaveDto));
  }

  @Test
  @DisplayName("상품 저장 실패 - 카테고리 없음")
  void save_throwsException_whenCategoryNotFound() {
    when(categoryRepository.findByTitle("TestCategory")).thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class, () ->
        productService.save(productSaveDto), "CATEGORY_NOT_FOUND");
  }

  @Test
  @DisplayName("상품 저장 실패 - 카테고리에 자식 카테고리가 있음")
  void save_throwsException_whenCategoryHasChild() {
    when(categoryRepository.findByTitle("TestCategory")).thenReturn(Optional.of(category));
    when(categoryRepository.hasChild(category.getId())).thenReturn(true);

    assertThrows(IllegalArgumentException.class, () ->
        productService.save(productSaveDto), "CATEGORY_HAS_CHILD");
  }

  @Test
  @DisplayName("상품 조회 성공")
  void productFindTest() {
    // given
    when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(productInfoDto));

    // when & then
    assertDoesNotThrow(() -> productService.findById(1L));
  }

  @Test
  @DisplayName("상품 정보 변경 성공")
  void productUpdateTest() {
    // given

    when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(productInfoDto));
    when(memberRepository.findByEmail(any(String.class))).thenReturn(Optional.of(member));
    doNothing().when(productRepository).updateProduct(any(ProductUpdateDto.class));

    // when & then
    assertDoesNotThrow(() -> productService.updateProduct(productUpdateDto));
  }

  @Test
  @DisplayName("상품 삭제 성공")
  void productDeleteTest() {
    // given

    when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(productInfoDto));
    when(memberRepository.findByEmail(any(String.class))).thenReturn(Optional.of(member));
    doNothing().when(productRepository).updateStatus(any(Long.class), any(ProductStatus.class));

    // when & then
    assertDoesNotThrow(() -> productService.delete(1L));
  }
}