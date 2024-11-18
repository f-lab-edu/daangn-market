package com.limikju.daangn_market.controller;

import com.limikju.daangn_market.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

  private final CategoryRepository categoryRepository;

  @GetMapping("/all")
  public ResponseEntity<?> getAll() {
    return ResponseEntity.ok(categoryRepository.findAll());
  }
}
