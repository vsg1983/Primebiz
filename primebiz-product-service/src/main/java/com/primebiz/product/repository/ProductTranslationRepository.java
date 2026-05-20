package com.primebiz.product.repository;

import com.primebiz.product.model.ProductTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductTranslationRepository extends JpaRepository<ProductTranslation, Long> {
    Optional<ProductTranslation> findByProductIdAndLocale(Long productId, String locale);
}
