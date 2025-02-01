package com.example.meeboilerplate.model.promotions;
import lombok.Data;

@Data
public class PromotionCreateRequest {
    private String name;
    private String conditionType;
}