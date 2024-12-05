package com.limikju.daangn_market.controller;

import com.limikju.daangn_market.apiPayload.ApiResponse;
import com.limikju.daangn_market.apiPayload.code.status.SuccessStatus;
import com.limikju.daangn_market.domain.dto.ProductInfoDto;
import com.limikju.daangn_market.domain.dto.ProductSaveDto;
import com.limikju.daangn_market.domain.dto.ProductUpdateDto;
import com.limikju.daangn_market.service.ProductService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ApiResponse<?> save(@RequestBody @Validated ProductSaveDto productSaveDto) {
    productService.save(productSaveDto);
    return ApiResponse.of(SuccessStatus.PRODUCT_SAVE);
  }

  @GetMapping("/{id}")
  public ApiResponse<?> find(@PathVariable Long id) {
    ProductInfoDto productInfoDto = productService.findById(id);
    return ApiResponse.of(SuccessStatus.PRODUCT_GET_ONE, productInfoDto);
  }

  @PutMapping("/{id}")
  public ApiResponse<?> update(@PathVariable Long id, @RequestBody @Validated ProductUpdateDto productUpdateDto) {
    productService.updateProduct(productUpdateDto);
    return ApiResponse.of(SuccessStatus.PRODUCT_UPDATE);
  }

  @DeleteMapping("/{id}")
  public ApiResponse<?> delete(@PathVariable Long id) {
    productService.delete(id);
    return ApiResponse.of(SuccessStatus.PRODUCT_DELETE);
  }

  @GetMapping
  public ApiResponse<?> list(@PageableDefault(size = 10, page = 0) Pageable pageable) {
    Page<Map<String, Object>> list = productService.getList(pageable);
    return ApiResponse.of(SuccessStatus.PRODUCT_GET_LIST, list);
  }
}
