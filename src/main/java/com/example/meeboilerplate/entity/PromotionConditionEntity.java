package com.example.meeboilerplate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Getter
@Setter
@Entity(name = "promotions_conditions")
public class PromotionConditionEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long promotionId;
    private BigDecimal minValue;
    private BigDecimal maxValue;
    private String discountType;
    private BigDecimal discountValue;
    private Integer step;

    @ManyToOne
    @JoinColumn(name = "promotion_id", nullable = false)
    @JsonIgnore
    private PromotionEntity promotion;

    // Getters and Setters
}