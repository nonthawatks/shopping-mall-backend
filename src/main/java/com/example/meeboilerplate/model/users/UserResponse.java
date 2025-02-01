package com.example.meeboilerplate.model.users;

import lombok.Data;

import java.util.Date;

@Data
public class UserResponse {
    private Integer user_id;
    private String username;
    private String email;
    private String password;
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;
}
