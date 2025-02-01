package com.example.meeboilerplate.model.promotions;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PromotionConditionDTO {
    private Long id;
    private BigDecimal minValue;
    private BigDecimal maxValue;
    private String discountType;
    private BigDecimal discountValue;
}