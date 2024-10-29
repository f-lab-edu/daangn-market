package com.limikju.daangn_market.controller;

import com.limikju.daangn_market.domain.dto.ProductInfoDto;
import com.limikju.daangn_market.domain.dto.ProductSaveDto;
import com.limikju.daangn_market.domain.dto.ProductUpdateDto;
import com.limikju.daangn_market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
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
  public ResponseEntity<?> save(@RequestBody @Validated ProductSaveDto productSaveDto) {
    productService.save(productSaveDto);
    return new ResponseEntity(HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> find(@PathVariable Long id) {
    ProductInfoDto productInfoDto = productService.findById(id);
    return ResponseEntity.ok(productInfoDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Validated ProductUpdateDto productUpdateDto) {
    productService.updateProduct(productUpdateDto);
    return new ResponseEntity(HttpStatus.OK);
  }
}
