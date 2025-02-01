package com.example.meeboilerplate.service.impl;

import com.example.meeboilerplate.entity.PromotionConditionEntity;
import com.example.meeboilerplate.exception.BaseException;
import com.example.meeboilerplate.repository.PromotionConditionRepository;
import com.example.meeboilerplate.service.PromotionConditionService;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
