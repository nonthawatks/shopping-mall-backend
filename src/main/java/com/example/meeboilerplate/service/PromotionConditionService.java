package com.example.meeboilerplate.service;

import java.util.List;

import com.example.meeboilerplate.entity.PromotionConditionEntity;
import com.example.meeboilerplate.exception.BaseException;
import com.example.meeboilerplate.model.promotions.PromotionOrderRequest;

public interface PromotionConditionService {
    public List<PromotionConditionEntity> search() throws BaseException;
    public List<PromotionConditionEntity> changeConditionsStep(List<PromotionOrderRequest> promotionUpdates) throws BaseException;
}
