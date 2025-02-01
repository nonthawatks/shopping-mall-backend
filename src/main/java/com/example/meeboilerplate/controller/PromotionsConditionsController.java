package com.example.meeboilerplate.controller;

import com.example.meeboilerplate.entity.PromotionConditionEntity;
import com.example.meeboilerplate.entity.PromotionEntity;
import com.example.meeboilerplate.exception.BaseException;
import com.example.meeboilerplate.model.MBaseDataResponse;
import com.example.meeboilerplate.model.MBaseListResponse;
import com.example.meeboilerplate.model.conditions.ConditionCreateRequest;
import com.example.meeboilerplate.model.conditions.ConditionOrderRequest;
import com.example.meeboilerplate.model.conditions.ConditionUpdateRequest;
import com.example.meeboilerplate.model.promotions.PromotionOrderRequest;
import com.example.meeboilerplate.service.PromotionConditionService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/conditions-order")
    public ResponseEntity<MBaseListResponse> changePromotionsStep(
            @Parameter(description = "Change condition order", required = true, array = @ArraySchema(schema = @Schema(implementation = ConditionOrderRequest.class))) @RequestBody List<ConditionOrderRequest> orderRequest)
            throws BaseException {
        List<PromotionConditionEntity> res = promotionConditionService.changeConditionsStep(orderRequest);
        MBaseListResponse dataResult = new MBaseListResponse();
        dataResult.setData(res);
        return ResponseEntity.ok(dataResult);
    }

    @PostMapping("/")
    public ResponseEntity<MBaseDataResponse<PromotionConditionEntity>> createCondition(
            @Parameter(description = "Condition create request", required = true, schema = @Schema(implementation = ConditionCreateRequest.class)) @RequestBody ConditionCreateRequest condition)
            throws BaseException {
        PromotionConditionEntity res = promotionConditionService.createCondition(condition);
        MBaseDataResponse<PromotionConditionEntity> dataResult = new MBaseDataResponse<PromotionConditionEntity>();
        dataResult.setData(res);
        return ResponseEntity.ok(dataResult);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MBaseDataResponse<PromotionConditionEntity>> updateCondition(
            @Parameter(description = "Condition id", required = true) @PathVariable Long id,
            @Parameter(description = "Condition update request", required = true, schema = @Schema(implementation = ConditionUpdateRequest.class)) @RequestBody ConditionUpdateRequest updateRequest)
            throws BaseException {
        PromotionConditionEntity res = promotionConditionService.updateCondition(id,updateRequest);
        MBaseDataResponse<PromotionConditionEntity> dataResult = new MBaseDataResponse<PromotionConditionEntity>();
        dataResult.setData(res);
        return ResponseEntity.ok(dataResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MBaseDataResponse<Long>> deleteCondition(
            @Parameter(description = "Condition id", required = true) @PathVariable Long id)
            throws BaseException {
        Long res = promotionConditionService.deleteCondition(id);
        MBaseDataResponse<Long> dataResult = new MBaseDataResponse<Long>();
        dataResult.setData(res);
        return ResponseEntity.ok(dataResult);
    }
}
