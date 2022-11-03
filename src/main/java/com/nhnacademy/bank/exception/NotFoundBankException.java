package com.nhnacademy.bank.exception;

public class NotFoundBankException extends RuntimeException {

    public NotFoundBankException(long bankId) {
        super("Not Found Bank : " + bankId);
    }
}
