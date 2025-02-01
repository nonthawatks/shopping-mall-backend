package com.example.meeboilerplate.exception;

public class FileException extends BaseException {
    public FileException(String code) {
        super("file."+code);
    }

    public static FileException fileIsNull() {
        return new FileException("null");
    }
    public static FileException fileMaxSize() {
        return new FileException("max.size");
    }
    public static FileException unsupportedFileType() {
        return new FileException("unsupported.file.type");
    }
}
