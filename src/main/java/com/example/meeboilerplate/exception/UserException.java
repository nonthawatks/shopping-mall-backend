package com.example.meeboilerplate.exception;

public class UserException extends BaseException {
    public UserException(String code) {
        super("user."+code);
    }

    public static UserException emailIsNull() {
        return new UserException("register.email.null");
    }
    public static UserException usernameIsNull() {
        return new UserException("register.username.null");
    }
    public static UserException passwordIsNull() {
        return new UserException("register.password.null");
    }
    public static UserException emailIsDuplicated() {
        return new UserException("register.email.duplicated");
    }
    public static UserException notFound() {
        return new UserException("not.found");
    }
    public static UserException loginUsernameNotFound() {
        return new UserException("login.failed");
    }
    public static UserException loginPasswordIsWrong() {
        return new UserException("login.failed");
    }
}
