package com.example.meeboilerplate.controller;

import com.example.meeboilerplate.entity.PromotionConditionEntity;
import com.example.meeboilerplate.exception.BaseException;
import com.example.meeboilerplate.model.MBaseListResponse;
import com.example.meeboilerplate.service.PromotionConditionService;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/promotions-conditions")
public class PromotionsConditionsController {
    private final PromotionConditionService promotionConditionService;

    public PromotionsConditionsController(PromotionConditionService promotionConditionService) {
        this.promotionConditionService = promotionConditionService;
    }

    @GetMapping
    public ResponseEntity<MBaseListResponse> search() throws BaseException {
        List<PromotionConditionEntity> res = promotionConditionService.search();
        MBaseListResponse dataResult = new MBaseListResponse();
        dataResult.setData(res);
        return ResponseEntity.ok(dataResult);
    }
}
