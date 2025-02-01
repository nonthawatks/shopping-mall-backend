package com.example.meeboilerplate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.meeboilerplate.entity.PromotionEntity; // Import your entity
import com.example.meeboilerplate.exception.BaseException;
import com.example.meeboilerplate.model.promotions.PromotionCreateRequest;
import com.example.meeboilerplate.model.promotions.PromotionOrderRequest;
import com.example.meeboilerplate.model.promotions.PromotionUpdateRequest;
import com.example.meeboilerplate.model.promotions.SearchPromotionRequest;
import com.example.meeboilerplate.repository.PromotionRepository; // Import your repository
import com.example.meeboilerplate.service.impl.PromotionServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestPromotionService {

    @Mock
    private PromotionRepository promotionRepository;

    @InjectMocks
    private PromotionServiceImpl promotionService;

    @Test
    void testSearchPromotion() throws BaseException {
        PromotionEntity promotion1 = new PromotionEntity();

        List<PromotionEntity> promotionList = new ArrayList<>();
        promotionList.add(promotion1);

        when(promotionRepository.findAll(any())).thenReturn(promotionList);

        List<PromotionEntity> promotions = promotionService.search(new SearchPromotionRequest());

        assertNotNull(promotions);
        assertEquals(1, promotions.size());
    }

    @Test
    void testCreatePromotion() throws BaseException {
        PromotionCreateRequest promotion = new PromotionCreateRequest();
        promotion.setName("Test Promotion");
        promotion.setConditionType("Test Condition Type");
        

        PromotionEntity savedPromotion = new PromotionEntity();
        savedPromotion.setId(1L);
        savedPromotion.setName("Test Promotion");
        savedPromotion.setConditionType("Test Condition Type");
        savedPromotion.setStep(1);
        savedPromotion.setActive(true);

        when(promotionRepository.save(any())).thenReturn(savedPromotion);

        PromotionEntity createdPromotion = promotionService.createPromotion(promotion);

        assertNotNull(createdPromotion);
        assertEquals(1L, createdPromotion.getId());
        assertEquals("Test Promotion", createdPromotion.getName());
        assertEquals("Test Condition Type", createdPromotion.getConditionType());
        assertEquals(1, createdPromotion.getStep());
        assertTrue(createdPromotion.isActive());
    }

    @Test
    void testUpdatePromotion() throws BaseException {
        PromotionEntity promotion = new PromotionEntity();
        promotion.setId(1L);
        promotion.setName("Test Promotion");
        promotion.setConditionType("Test Condition Type");
        promotion.setStep(1);
        promotion.setActive(true);

        PromotionUpdateRequest updateRequest = new PromotionUpdateRequest();
        updateRequest.setName("Updated Promotion");
        updateRequest.setConditionType("Updated Condition Type");
        updateRequest.setStep(2);
        updateRequest.setIsActive(false);


        PromotionEntity updatedPromotion = new PromotionEntity();
        updatedPromotion.setId(1L);
        updatedPromotion.setName(updateRequest.getName());
        updatedPromotion.setConditionType(updateRequest.getConditionType());
        updatedPromotion.setStep(updateRequest.getStep());
        updatedPromotion.setActive(updateRequest.getIsActive());

        when(promotionRepository.findById(any())).thenReturn(Optional.of(promotion));
        when(promotionRepository.save(any())).thenReturn(updatedPromotion);

        PromotionEntity updated = promotionService.updatePromotion(1L, updateRequest);    

        assertNotNull(updated);
        assertEquals(1L, updated.getId());
        assertEquals("Updated Promotion", updated.getName());
        assertEquals("Updated Condition Type", updated.getConditionType());
        assertEquals(2, updated.getStep());
        assertFalse(updated.isActive());
    }

    //Change order
    @Test
    void testChangePromotionsStep() throws BaseException {
        PromotionEntity promotionA = new PromotionEntity();
        promotionA.setId(1L);
        promotionA.setStep(1);
        PromotionEntity promotionB = new PromotionEntity();
        promotionA.setId(2L);
        promotionA.setStep(2);

        when(promotionRepository.findById(1L)).thenReturn(Optional.of(promotionA));
        when(promotionRepository.findById(2L)).thenReturn(Optional.of(promotionB));

        PromotionOrderRequest promotionOrderRequestA = new PromotionOrderRequest();
        promotionOrderRequestA.setId(1L);
        promotionOrderRequestA.setStep(2);

        PromotionOrderRequest promotionOrderRequestB = new PromotionOrderRequest();
        promotionOrderRequestB.setId(2L);
        promotionOrderRequestB.setStep(1);

        List<PromotionOrderRequest> promotionUpdates = new ArrayList<>();
        promotionUpdates.add(promotionOrderRequestA);
        promotionUpdates.add(promotionOrderRequestB);

        
        PromotionEntity updatedPromotionA = new PromotionEntity();
        updatedPromotionA.setId(1L);
        updatedPromotionA.setStep(promotionOrderRequestA.getStep());
        PromotionEntity updatedPromotionB = new PromotionEntity();
        updatedPromotionB.setId(2L);
        updatedPromotionB.setStep(promotionOrderRequestB.getStep());
        List<PromotionEntity> promotions = new ArrayList<>();
        promotions.add(updatedPromotionA);
        promotions.add(updatedPromotionB);

        when(promotionRepository.saveAll(any())).thenReturn(promotions);
        

        List<PromotionEntity> updatedPromotions = promotionService.changePromotionsStep(promotionUpdates);

        assertNotNull(updatedPromotions);
        assertEquals(2, updatedPromotions.size());
        assertEquals(1, updatedPromotions.get(0).getId());
        assertEquals(2, updatedPromotions.get(0).getStep());
        assertEquals(2, updatedPromotions.get(1).getId());
        assertEquals(1, updatedPromotions.get(1).getStep());

    }
}