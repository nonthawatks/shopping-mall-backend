package com.example.meeboilerplate.service;

import java.util.List;

import com.example.meeboilerplate.entity.PromotionEntity;
import com.example.meeboilerplate.exception.BaseException;
import com.example.meeboilerplate.model.promotions.CalculateDiscountRequest;
import com.example.meeboilerplate.model.promotions.CalculateDiscountResponse;
import com.example.meeboilerplate.model.promotions.PromotionCreateRequest;
import com.example.meeboilerplate.model.promotions.PromotionOrderRequest;
import com.example.meeboilerplate.model.promotions.PromotionUpdateRequest;
import com.example.meeboilerplate.model.promotions.SearchPromotionRequest;

public interface PromotionService {
    public List<PromotionEntity> search(SearchPromotionRequest searchParams) throws BaseException;
    public CalculateDiscountResponse calculateDiscount(List<CalculateDiscountRequest> calculateItems) throws BaseException;
    public PromotionEntity createPromotion(PromotionCreateRequest promotionCreateRequest) throws BaseException;
    public List<PromotionEntity> changePromotionsStep(List<PromotionOrderRequest> promotionUpdates) throws BaseException;
    public PromotionEntity updatePromotion(Long id, PromotionUpdateRequest promotionUpdateRequest) throws BaseException;
    public Long deletePromotion(Long id) throws BaseException ;
}
