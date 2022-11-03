package com.nhnacademy.bank.exception;

import com.nhnacademy.bank.domain.Currency;

public class ImpossibleExchangeException extends RuntimeException {
    public ImpossibleExchangeException(Currency targetCurrency) {
        super("Impossible Exchange : " + targetCurrency);
    }
}
