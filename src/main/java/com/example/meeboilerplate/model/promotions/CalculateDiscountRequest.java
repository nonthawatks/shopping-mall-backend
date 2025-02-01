package com.example.meeboilerplate.model.promotions;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CalculateDiscountRequest {
    private Long productId;
    @Schema(description = "Product price", example = "30000")
    private BigDecimal amount;
    @Schema(description = "Product quantity", example = "1")
    private Integer quantity;
}
