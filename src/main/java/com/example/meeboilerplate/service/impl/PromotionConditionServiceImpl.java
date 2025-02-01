package com.example.meeboilerplate.service.impl;

import com.example.meeboilerplate.entity.PromotionConditionEntity;
import com.example.meeboilerplate.entity.PromotionEntity;
import com.example.meeboilerplate.exception.BaseException;
import com.example.meeboilerplate.exception.PromotionException;
import com.example.meeboilerplate.model.promotions.PromotionOrderRequest;
import com.example.meeboilerplate.repository.PromotionConditionRepository;
import com.example.meeboilerplate.service.PromotionConditionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionConditionServiceImpl implements PromotionConditionService {
    private final PromotionConditionRepository promotionCondRepository;

    public PromotionConditionServiceImpl(PromotionConditionRepository promotionCondRepository) {
        this.promotionCondRepository = promotionCondRepository;
    }

    public List<PromotionConditionEntity> search() throws BaseException {
        Iterable<PromotionConditionEntity> promotionConditions = promotionCondRepository.findAll();
        List<PromotionConditionEntity> promotionConditionsList = (List<PromotionConditionEntity>) promotionConditions;
        return promotionConditionsList;
    }

    public List<PromotionConditionEntity> changeConditionsStep(List<PromotionOrderRequest> conditionsUpdates)
            throws BaseException {
        List<PromotionConditionEntity> conditionsToUpdate = new ArrayList<>();

        for (PromotionOrderRequest conditionsUpdate : conditionsUpdates) {
            Optional<PromotionConditionEntity> conditionOpt = promotionCondRepository
                    .findById(conditionsUpdate.getId());
            PromotionConditionEntity condition = conditionOpt
                    .orElseThrow(() -> PromotionException.invalidPromotion(conditionsUpdate.getId()));

            condition.setStep(conditionsUpdate.getStep());
            condition.setUpdatedDate(LocalDateTime.now());
            conditionsToUpdate.add(condition);
        }

        try {
            promotionCondRepository.saveAll(conditionsToUpdate);
            return conditionsToUpdate;
        } catch (Exception e) {
            throw new PromotionException(e.getMessage());
        }
    }
}
