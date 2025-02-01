package com.example.meeboilerplate.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MBaseDataResponse<T> extends MBaseResponse {
    private T data;
}
