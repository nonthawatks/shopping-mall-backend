package com.example.meeboilerplate.exception;

public class PromotionException extends BaseException {
    public PromotionException(String msg) {
        super(msg);
    }

    public static PromotionException invalidPromotion(Long id) {
        return new PromotionException("Promotion not found with id: " + id);
    }
}
