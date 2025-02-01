package com.example.meeboilerplate.service;

import java.util.List;

import com.example.meeboilerplate.entity.PromotionConditionEntity;
import com.example.meeboilerplate.exception.BaseException;

public interface PromotionConditionService {
    public List<PromotionConditionEntity> search() throws BaseException;
}
