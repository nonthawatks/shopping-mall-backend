package com.example.meeboilerplate.service.impl;

import com.example.meeboilerplate.entity.PromotionConditionEntity;
import com.example.meeboilerplate.entity.PromotionEntity;
import com.example.meeboilerplate.exception.BaseException;
import com.example.meeboilerplate.exception.PromotionException;
import com.example.meeboilerplate.model.promotions.CalculateDiscountRequest;
import com.example.meeboilerplate.model.promotions.CalculateDiscountResponse;
import com.example.meeboilerplate.model.promotions.PromotionCreateRequest;
import com.example.meeboilerplate.model.promotions.PromotionOrderRequest;
import com.example.meeboilerplate.model.promotions.PromotionUpdateRequest;
import com.example.meeboilerplate.model.promotions.SearchPromotionRequest;
import com.example.meeboilerplate.repository.PromotionRepository;
import com.example.meeboilerplate.service.PromotionService;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PromotionServiceImpl implements PromotionService {
    private final PromotionRepository promotionRepository;
    private final String PROMOTION_TYPE_AMOUNT = "amount";
    private final String PROMOTION_TYPE_QUANTITY = "quantity";
    private final String DISCOUNT_TYPE_PERCENT = "percentage";
    private final String DISCOUNT_TYPE_FIXED = "fixed";

    public PromotionServiceImpl(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public List<PromotionEntity> search(SearchPromotionRequest searchParams) throws BaseException {
        Sort sort = Sort.by("step").ascending().and(Sort.by("conditions", "step").ascending());
        Iterable<PromotionEntity> promotions = promotionRepository.findAll(sort);
        List<PromotionEntity> promotionsList = StreamSupport.stream(promotions.spliterator(), false)
                .collect(Collectors.toList());
        return promotionsList;
    }

    public CalculateDiscountResponse calculateDiscount(List<CalculateDiscountRequest> calculateItems)
            throws BaseException {
        SearchPromotionRequest searchParams = new SearchPromotionRequest();
        searchParams.setActive(true);
        List<PromotionEntity> activePromotions = search(searchParams);
        CalculateDiscountResponse discountResponse = calculateDiscount(calculateItems, activePromotions);
        return discountResponse;
    }

    private CalculateDiscountResponse calculateDiscount(List<CalculateDiscountRequest> calculateItems,
            List<PromotionEntity> promotions) throws BaseException {
        CalculateDiscountResponse result = new CalculateDiscountResponse();
        result.setDiscountDetails(new ArrayList<>());
        BigDecimal discountAmount = BigDecimal.ZERO;
        BigDecimal netAmount = BigDecimal.ZERO;
        double totalAmount = calculateItems.stream()
                .mapToDouble(item -> item.getAmount().doubleValue() * item.getQuantity())
                .sum();

        int totalQuantity = calculateItems.stream()
                .mapToInt(CalculateDiscountRequest::getQuantity)
                .sum();

        netAmount = BigDecimal.valueOf(totalAmount);
        for (PromotionEntity promotion : promotions) {
            List<PromotionConditionEntity> conditions = promotion.getConditions();
            BigDecimal discountValue = BigDecimal.ZERO;
            if (promotion.getConditionType().equals(PROMOTION_TYPE_AMOUNT)) {
                discountValue = calculateDiscountByConditions(conditions, BigDecimal.valueOf(totalAmount), netAmount,
                        result);
            } else if (promotion.getConditionType().equals(PROMOTION_TYPE_QUANTITY)) {
                discountValue = calculateDiscountByConditions(conditions, BigDecimal.valueOf(totalQuantity), netAmount,
                        result);
            }
            discountAmount = discountAmount.add(discountValue);
            netAmount = netAmount.subtract(discountValue);
        }
        result.setDiscountAmount(discountAmount);
        result.setTotalAmount(BigDecimal.valueOf(totalAmount));
        result.setTotalQuantity(BigDecimal.valueOf(totalQuantity));
        result.setNetAmount(netAmount);
        return result;
    }

    private BigDecimal calculateDiscountByConditions(List<PromotionConditionEntity> conditions, BigDecimal totalAmount,
            BigDecimal netAmount,
            CalculateDiscountResponse result) {
        BigDecimal discountAmount = BigDecimal.ZERO;
        for (PromotionConditionEntity condition : conditions) {
            if ((condition.getMinValue() == null || totalAmount.compareTo(condition.getMinValue()) >= 0)
                    && (condition.getMaxValue() == null || totalAmount.compareTo(condition.getMaxValue()) <= 0)) {
                if (condition.getDiscountType().equals(DISCOUNT_TYPE_FIXED)) {
                    discountAmount = discountAmount.add(condition.getDiscountValue());
                    String discountMessage = String.format("Discount is %.2f",
                            discountAmount);
                    result.getDiscountDetails().add(discountMessage);
                } else if (condition.getDiscountType().equals(DISCOUNT_TYPE_PERCENT)) {
                    BigDecimal discountValue = netAmount
                            .multiply(condition.getDiscountValue().divide(BigDecimal.valueOf(100)));
                    discountAmount = discountAmount.add(discountValue);
                    String discountMessage = String.format("Discount is %.2f (%.0f%%)",
                            discountAmount, condition.getDiscountValue());
                    result.getDiscountDetails().add(discountMessage);
                }
            }
        }
        return discountAmount;
    }

    public PromotionEntity createPromotion(PromotionCreateRequest promotionCreateRequest) throws BaseException {
        try {
            PromotionEntity promotion = new PromotionEntity();
            promotion.setName(promotionCreateRequest.getName());
            promotion.setConditionType(promotionCreateRequest.getConditionType());

            // get last step
            Integer lastStep = promotionRepository.findLastStep();
            Integer nextStep = (lastStep == null ? 0 : lastStep) + 1;
            promotion.setStep(nextStep);
            promotion.setActive(true);
            return promotionRepository.save(promotion);
        } catch (Exception e) {
            throw new PromotionException(e.getMessage());
        }
    }

    public PromotionEntity updatePromotion(Long id, PromotionUpdateRequest promotionUpdateRequest)
            throws BaseException {
        try {
            PromotionEntity promotion = promotionRepository.findById(id)
                    .orElseThrow(() -> PromotionException.invalidPromotion(id));
            if (promotionUpdateRequest.getName() != null)
                promotion.setName(promotionUpdateRequest.getName());
            if (promotionUpdateRequest.getConditionType() != null)
                promotion.setConditionType(promotionUpdateRequest.getConditionType());
            if (promotionUpdateRequest.getStep() != null)
                promotion.setStep(promotionUpdateRequest.getStep());
            if (promotionUpdateRequest.getIsActive() != null)
                promotion.setActive(promotionUpdateRequest.getIsActive());
            return promotionRepository.save(promotion);
        } catch (Exception e) {
            throw new PromotionException(e.getMessage());
        }
    }

    public Long deletePromotion(Long id) throws BaseException {
        try {
            PromotionEntity promotion = promotionRepository.findById(id)
                    .orElseThrow(() -> PromotionException.invalidPromotion(id));
            promotionRepository.deleteById(promotion.getId());
            return id;
        } catch (Exception e) {
            throw new PromotionException(e.getMessage());
        }
    }

    public List<PromotionEntity> changePromotionsStep(List<PromotionOrderRequest> promotionUpdates)
            throws BaseException {
        List<PromotionEntity> promotionsToUpdate = new ArrayList<>();

        for (PromotionOrderRequest promotionUpdate : promotionUpdates) {
            Optional<PromotionEntity> promotionOpt = promotionRepository.findById(promotionUpdate.getId());
            PromotionEntity promotion = promotionOpt
                    .orElseThrow(() -> PromotionException.invalidPromotion(promotionUpdate.getId()));

            promotion.setStep(promotionUpdate.getStep());
            promotion.setUpdatedDate(LocalDateTime.now());
            promotionsToUpdate.add(promotion);
        }

        try {
            promotionRepository.saveAll(promotionsToUpdate);
            return promotionsToUpdate;
        } catch (Exception e) {
            throw new PromotionException(e.getMessage());
        }
    }
}
