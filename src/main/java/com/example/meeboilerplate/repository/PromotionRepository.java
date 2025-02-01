package com.example.meeboilerplate.repository;

import com.example.meeboilerplate.entity.PromotionEntity;

import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PromotionRepository extends CrudRepository<PromotionEntity, Long> {

    Iterable<PromotionEntity> findAll(Sort sort);

    Optional<PromotionEntity> findById(Long id);

    @Query(value = "select max(step) from promotions", nativeQuery = true)
    Integer findLastStep();
}
