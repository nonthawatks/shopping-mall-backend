package com.example.meeboilerplate.model.users;

import lombok.Data;

@Data
public class MRegisterRequest {
    private String username;
    private String email;
    private String password;
}
