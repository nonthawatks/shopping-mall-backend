package com.example.meeboilerplate.repository;

import com.example.meeboilerplate.entity.PromotionConditionEntity;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PromotionConditionRepository extends CrudRepository<PromotionConditionEntity, Long> {
   

    Optional<PromotionConditionEntity> findById(Long id);

    @Query(value = "select max(step) from promotions_conditions where promotion_id = :id", nativeQuery = true)
    Integer findLastStep(Long id);
}
