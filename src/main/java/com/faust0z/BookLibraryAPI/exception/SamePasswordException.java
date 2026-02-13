package com.faust0z.BookLibraryAPI.exception;

public class SamePasswordException extends RuntimeException {
    public SamePasswordException(String message) {
        super(message);
    }
}
