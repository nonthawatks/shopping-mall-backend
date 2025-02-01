package com.example.meeboilerplate.model.conditions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConditionUpdateRequest extends ConditionCreateRequest {
    private Integer step;
}