package com.example.meeboilerplate.model.users;

import lombok.Data;

@Data
public class MUserRequest {
    private String username;
    private String email;
    private String password;
}
