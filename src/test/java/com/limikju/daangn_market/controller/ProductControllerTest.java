package com.limikju.daangn_market.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.limikju.daangn_market.domain.dto.ProductSaveDto;
import com.limikju.daangn_market.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductService productService;

  @Autowired
  private ObjectMapper objectMapper;

  private ProductSaveDto productSaveDto;

  @BeforeEach
  void setUp() {
    productSaveDto = ProductSaveDto.builder()
        .title("title")
        .content("content")
        .price(10000)
        .category("하의")
        .build();
  }


  @Test
  @DisplayName("상품 저장 성공")
  void saveProduct_success() throws Exception {
    // given

    // when
    doNothing().when(productService).save(any(ProductSaveDto.class));

    // then
    mockMvc.perform(
            post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productSaveDto)))
        .andExpect(status().isCreated());
  }

  @Test
  @DisplayName("상품 저장 실패 - 제목")
  @WithMockUser(username="test@test.com", password = "Q1w2e3r4!!", roles={"USER"})
  void saveProduct_missingField_failure() throws Exception {
    // given
    ProductSaveDto productSaveDto = ProductSaveDto.builder()
        .content("Sample Content")
        .price(10000)
        .category("TestCategory")
        .build();  // title 누락

    // when & then
    mockMvc.perform(
            post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productSaveDto)))
        .andExpect(status().isBadRequest());
  }
}