package com.example.meeboilerplate.model.promotions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromotionUpdateRequest extends PromotionCreateRequest {
    private Integer step;
    private Boolean isActive;
}