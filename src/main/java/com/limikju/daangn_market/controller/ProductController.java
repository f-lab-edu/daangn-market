package com.limikju.daangn_market.controller;

import com.limikju.daangn_market.domain.dto.ProductSaveDto;
import com.limikju.daangn_market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
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
}
