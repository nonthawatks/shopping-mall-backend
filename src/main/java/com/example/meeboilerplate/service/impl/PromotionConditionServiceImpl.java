package com.example.meeboilerplate.service.impl;

import com.example.meeboilerplate.entity.PromotionConditionEntity;
import com.example.meeboilerplate.exception.BaseException;
import com.example.meeboilerplate.exception.ConditionException;
import com.example.meeboilerplate.exception.PromotionException;
import com.example.meeboilerplate.model.conditions.ConditionCreateRequest;
import com.example.meeboilerplate.model.conditions.ConditionOrderRequest;
import com.example.meeboilerplate.model.conditions.ConditionUpdateRequest;
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

    public List<PromotionConditionEntity> changeConditionsStep(List<ConditionOrderRequest> conditionsUpdates)
            throws BaseException {
        List<PromotionConditionEntity> conditionsToUpdate = new ArrayList<>();

        for (ConditionOrderRequest conditionsUpdate : conditionsUpdates) {
            Optional<PromotionConditionEntity> conditionOpt = promotionCondRepository
                    .findById(conditionsUpdate.getId());
            PromotionConditionEntity condition = conditionOpt
                    .orElseThrow(() -> ConditionException.invalidCondition(conditionsUpdate.getId()));

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

    public PromotionConditionEntity createCondition(ConditionCreateRequest request) throws BaseException {
        try {
            PromotionConditionEntity condition = new PromotionConditionEntity();
            condition.setPromotionId(request.getPromotionId());
            condition.setMinValue(request.getMinValue());
            condition.setMaxValue(request.getMaxValue());
            condition.setDiscountType(request.getDiscountType());
            condition.setDiscountValue(request.getDiscountValue());
            Integer lastStep = promotionCondRepository.findLastStep(request.getPromotionId());
            Integer nextStep = (lastStep == null ? 0 : lastStep) + 1;
            condition.setStep(nextStep);
            return promotionCondRepository.save(condition);
        } catch (Exception e) {
            throw new ConditionException(e.getMessage());
        }
    }

    public Long deleteCondition(Long id) throws BaseException {
        try {
            PromotionConditionEntity condition = promotionCondRepository.findById(id)
                    .orElseThrow(() -> ConditionException.invalidCondition(id));
            promotionCondRepository.deleteById(condition.getId());
            return id;
        } catch (Exception e) {
            throw new ConditionException(e.getMessage());
        }
    }

    public PromotionConditionEntity updateCondition(Long id, ConditionUpdateRequest request) throws BaseException {
        try {
            PromotionConditionEntity condition = promotionCondRepository.findById(id)
                    .orElseThrow(() -> ConditionException.invalidCondition(id));
            if (request.getMinValue() != null)
                condition.setMinValue(request.getMinValue());
            if (request.getMaxValue() != null)
                condition.setMaxValue(request.getMaxValue());
            if (request.getDiscountType() != null)
                condition.setDiscountType(request.getDiscountType());
            if (request.getDiscountValue() != null)
                condition.setDiscountValue(request.getDiscountValue());
            if (request.getStep() != null)
                condition.setStep(request.getStep());
            return promotionCondRepository.save(condition);
        } catch (Exception e) {
            throw new ConditionException(e.getMessage());
        }
    }
}
