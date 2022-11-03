package com.nhnacademy.bank.exception;

import com.nhnacademy.bank.domain.Currency;

public class DifferentCurrencyException extends RuntimeException {
    public DifferentCurrencyException(Currency currency){
        super("Not Match Currency : " + currency);
    }
}
