package com.example.meeboilerplate.model.conditions;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class ConditionCreateRequest {
    private Long promotionId;
    private BigDecimal minValue;
    private BigDecimal maxValue;
    private String discountType;
    private BigDecimal discountValue;
}