package com.example.meeboilerplate.repository;

import com.example.meeboilerplate.entity.ProductEntity;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
    Optional<ProductEntity> findById(Long id);
}
