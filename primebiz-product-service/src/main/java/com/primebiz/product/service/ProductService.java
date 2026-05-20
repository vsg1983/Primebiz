package com.primebiz.product.service;

import com.primebiz.product.dto.ProductResponseDTO;
import com.primebiz.product.model.Product;
import com.primebiz.product.model.ProductTranslation;
import com.primebiz.product.repository.ProductRepository;
import com.primebiz.product.repository.ProductTranslationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductTranslationRepository translationRepository;

    public ProductResponseDTO getProductLocalized(Long id, String locale) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductTranslation translation = translationRepository.findByProductIdAndLocale(id, locale)
                .orElseGet(() -> translationRepository.findByProductIdAndLocale(id, "en")
                        .orElseThrow(() -> new RuntimeException("No translation found for product")));

        return ProductResponseDTO.builder()
                .id(product.getId())
                .sku(product.getSku())
                .price(product.getPrice())
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .name(translation.getName())
                .description(translation.getDescription())
                .build();
    }

    public List<ProductResponseDTO> getAllProductsLocalized(String locale) {
        return productRepository.findAll().stream()
                .map(p -> getProductLocalized(p.getId(), locale))
                .collect(Collectors.toList());
    }
}
