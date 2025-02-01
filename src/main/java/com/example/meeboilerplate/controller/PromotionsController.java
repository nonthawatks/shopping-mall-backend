package com.example.meeboilerplate.controller;

import com.example.meeboilerplate.entity.PromotionEntity;
import com.example.meeboilerplate.exception.BaseException;
import com.example.meeboilerplate.model.MBaseDataResponse;
import com.example.meeboilerplate.model.MBaseListResponse;
import com.example.meeboilerplate.model.promotions.CalculateDiscountRequest;
import com.example.meeboilerplate.model.promotions.CalculateDiscountResponse;
import com.example.meeboilerplate.model.promotions.PromotionCreateRequest;
import com.example.meeboilerplate.model.promotions.PromotionOrderRequest;
import com.example.meeboilerplate.model.promotions.PromotionUpdateRequest;
import com.example.meeboilerplate.model.promotions.SearchPromotionRequest;
import com.example.meeboilerplate.service.PromotionService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.core.annotation.MergedAnnotations.Search;
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
@RequestMapping("/v1/promotions")
public class PromotionsController {
    private final PromotionService promotionService;

    public PromotionsController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @PostMapping("/search")
    public ResponseEntity<MBaseListResponse> search(
            @Parameter(description = "Search promotion", required = false, array = @ArraySchema(schema = @Schema(implementation = SearchPromotionRequest.class))) @RequestBody SearchPromotionRequest searchParams)
            throws BaseException {
        List<PromotionEntity> res = promotionService.search(searchParams);
        MBaseListResponse dataResult = new MBaseListResponse();
        dataResult.setData(res);
        return ResponseEntity.ok(dataResult);
    }

    @PostMapping("/discount-calculate")
    public ResponseEntity<MBaseDataResponse<CalculateDiscountResponse>> calculateDiscount(
            @Parameter(description = "List of product items", required = true, array = @ArraySchema(schema = @Schema(implementation = CalculateDiscountRequest.class))) @RequestBody List<CalculateDiscountRequest> calculateItems)
            throws BaseException {
        CalculateDiscountResponse res = promotionService.calculateDiscount(calculateItems);
        MBaseDataResponse<CalculateDiscountResponse> dataResult = new MBaseDataResponse<CalculateDiscountResponse>();
        dataResult.setData(res);
        return ResponseEntity.ok(dataResult);
    }

    @PostMapping("/")
    public ResponseEntity<MBaseDataResponse<PromotionEntity>> createPromotion(
            @Parameter(description = "Promotion create request", required = true, schema = @Schema(implementation = PromotionCreateRequest.class)) @RequestBody PromotionCreateRequest createRequest)
            throws BaseException {
        PromotionEntity res = promotionService.createPromotion(createRequest);
        MBaseDataResponse<PromotionEntity> dataResult = new MBaseDataResponse<PromotionEntity>();
        dataResult.setData(res);
        return ResponseEntity.ok(dataResult);
    }

    @PostMapping("/promotions-order")
    public ResponseEntity<MBaseListResponse> changePromotionsStep(
            @Parameter(description = "Change promotion order", required = true, array = @ArraySchema(schema = @Schema(implementation = PromotionOrderRequest.class))) @RequestBody List<PromotionOrderRequest> orderRequest)
            throws BaseException {
        List<PromotionEntity> res = promotionService.changePromotionsStep(orderRequest);
        MBaseListResponse dataResult = new MBaseListResponse();
        dataResult.setData(res);
        return ResponseEntity.ok(dataResult);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MBaseDataResponse<PromotionEntity>> updatePromotion(
            @Parameter(description = "Promotion id", required = true) @PathVariable Long id,
            @Parameter(description = "Promotion update request", required = true, schema = @Schema(implementation = PromotionUpdateRequest.class)) @RequestBody PromotionUpdateRequest updateRequest)
            throws BaseException {
        PromotionEntity res = promotionService.updatePromotion(id,updateRequest);
        MBaseDataResponse<PromotionEntity> dataResult = new MBaseDataResponse<PromotionEntity>();
        dataResult.setData(res);
        return ResponseEntity.ok(dataResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MBaseDataResponse<Long>> deletePromotion(
            @Parameter(description = "Promotion id", required = true) @PathVariable Long id)
            throws BaseException {
        Long res = promotionService.deletePromotion(id);
        MBaseDataResponse<Long> dataResult = new MBaseDataResponse<Long>();
        dataResult.setData(res);
        return ResponseEntity.ok(dataResult);
    }
}
