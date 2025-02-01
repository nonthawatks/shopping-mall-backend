package com.example.meeboilerplate.model.promotions;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class CalculateDiscountResponse {
    private BigDecimal discountAmount;
    private BigDecimal totalAmount;
    private BigDecimal totalQuantity;
    private BigDecimal netAmount;
    private List<String> discountDetails;
}
