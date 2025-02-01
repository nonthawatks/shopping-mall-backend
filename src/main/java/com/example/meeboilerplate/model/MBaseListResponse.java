package com.example.meeboilerplate.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MBaseListResponse extends MBaseResponse {
    private List data;
}
