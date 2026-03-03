package com.faust0z.BookLibraryAPI.exception;

public class AlreadyLoanedBookException extends RuntimeException {
    public AlreadyLoanedBookException(String message) {
        super(message);
    }
}
