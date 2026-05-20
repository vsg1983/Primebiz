package com.primebiz.product.controller;

import com.primebiz.product.dto.ProductResponseDTO;
import com.primebiz.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(
            @PathVariable Long id, 
            @RequestHeader(name = "Accept-Language", defaultValue = "en") String locale) {
        
        // Extract language code from locale (e.g., "en-US" -> "en")
        String lang = locale.split("-")[0].toLowerCase();
        return ResponseEntity.ok(productService.getProductLocalized(id, lang));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(
            @RequestHeader(name = "Accept-Language", defaultValue = "en") String locale) {
        
        String lang = locale.split("-")[0].toLowerCase();
        return ResponseEntity.ok(productService.getAllProductsLocalized(lang));
    }
}
