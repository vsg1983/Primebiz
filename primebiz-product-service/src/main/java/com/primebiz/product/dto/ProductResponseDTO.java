package com.primebiz.product.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String sku;
    private BigDecimal price;
    private String categoryName;
    private String name;
    private String description;
}
