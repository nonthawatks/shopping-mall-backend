package com.example.meeboilerplate.service;

import java.util.List;

import com.example.meeboilerplate.entity.PromotionConditionEntity;
import com.example.meeboilerplate.exception.BaseException;
import com.example.meeboilerplate.model.conditions.ConditionCreateRequest;
import com.example.meeboilerplate.model.conditions.ConditionOrderRequest;
import com.example.meeboilerplate.model.conditions.ConditionUpdateRequest;

public interface PromotionConditionService {
    public List<PromotionConditionEntity> search() throws BaseException;
    public List<PromotionConditionEntity> changeConditionsStep(List<ConditionOrderRequest> promotionUpdates) throws BaseException;
    public PromotionConditionEntity createCondition(ConditionCreateRequest request) throws BaseException;
    public Long deleteCondition(Long id) throws BaseException;
    public PromotionConditionEntity updateCondition(Long id, ConditionUpdateRequest request) throws BaseException;
}
