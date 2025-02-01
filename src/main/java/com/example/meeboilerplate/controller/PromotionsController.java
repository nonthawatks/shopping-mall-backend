package com.example.meeboilerplate.controller;

import com.example.meeboilerplate.entity.PromotionEntity;
import com.example.meeboilerplate.exception.BaseException;
import com.example.meeboilerplate.model.MBaseDataResponse;
import com.example.meeboilerplate.model.MBaseListResponse;
import com.example.meeboilerplate.model.promotions.CalculateDiscountRequest;
import com.example.meeboilerplate.model.promotions.CalculateDiscountResponse;
import com.example.meeboilerplate.model.promotions.SearchPromotionRequest;
import com.example.meeboilerplate.service.PromotionService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.core.annotation.MergedAnnotations.Search;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/calculate-discount")
    public ResponseEntity<MBaseDataResponse<CalculateDiscountResponse>> calculateDiscount(
            @Parameter(description = "List of product items", required = true, array = @ArraySchema(schema = @Schema(implementation = CalculateDiscountRequest.class))) @RequestBody List<CalculateDiscountRequest> calculateItems)
            throws BaseException {
                CalculateDiscountResponse res = promotionService.calculateDiscount(calculateItems);
        MBaseDataResponse<CalculateDiscountResponse> dataResult = new MBaseDataResponse<CalculateDiscountResponse>();
        dataResult.setData(res);
        return ResponseEntity.ok(dataResult);
    }
}
