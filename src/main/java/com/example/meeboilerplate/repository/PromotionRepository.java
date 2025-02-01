package com.example.meeboilerplate.repository;

import com.example.meeboilerplate.entity.PromotionEntity;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface PromotionRepository extends CrudRepository<PromotionEntity, Integer> {

    Iterable<PromotionEntity> findAll(Sort sort);
}
