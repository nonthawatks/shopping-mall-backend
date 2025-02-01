package com.example.meeboilerplate.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.meeboilerplate.exception.UserException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TokenUtil {

    private TokenUtil() {

    }

    public static Integer getCurrentUserId() throws UserException {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            throw UserException.notFound();
        }

        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            throw UserException.notFound();
        }

        Object principal = authentication.getPrincipal();
        if (principal == null) {
            throw UserException.notFound();
        }
        Integer userId = (Integer) principal;
        return userId;
    }

}