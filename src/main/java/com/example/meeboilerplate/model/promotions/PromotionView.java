package com.example.meeboilerplate.model.promotions;

import java.util.List;

public interface PromotionView {
    Long getId();

    String getName();

    String getConditionType();

    List<PromotionConditionDTO> getConditions();
}