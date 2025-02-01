package com.example.meeboilerplate.exception;

public class ConditionException extends BaseException {
    public ConditionException(String msg) {
        super(msg);
    }

    public static ConditionException invalidCondition(Long id) {
        return new ConditionException("Condition not found with id: " + id);
    }
}
