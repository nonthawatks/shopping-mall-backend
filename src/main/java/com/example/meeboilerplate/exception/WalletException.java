package com.example.meeboilerplate.exception;

public class WalletException extends BaseException {
    public WalletException(String code) {
        super("wallet."+code);
    }

    public static WalletException invalidUserId() {
        return new WalletException("invalid.user_id");
    }
    public static WalletException invalidName() {
        return new WalletException("invalid.name");
    }
    public static WalletException invalidType() {
        return new WalletException("invalid.type");
    }
    public static WalletException notFound() {
        return new WalletException("not.found");
    }

    public static WalletException invalidId() {
        return new WalletException("invalid.id");
    }
}
