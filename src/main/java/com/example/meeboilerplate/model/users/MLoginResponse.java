package com.example.meeboilerplate.model.users;

import lombok.Data;

@Data
public class MLoginResponse {
    private String username;
    private String email;
    private String accessToken;
}
