package com.nhnacademy.bank.factory;

import com.nhnacademy.bank.domain.Currency;

public class KoreaExchangeFactory {

    private double amount;
    private final Currency targetCurrency;
    private  double exchangeResult;

    private final double KRW_USD_RATE = 0.001;

    private final double KRW_JPY_RATE = 0.1;

    public KoreaExchangeFactory(double amount, Currency targetCurrency) {
        this.amount = amount;
        this.targetCurrency = targetCurrency;
        this.exchangeResult = 0;
    }

    public double exchangeProcess() {
        switch (targetCurrency) {
            case CURRENCY_USD:
                this.exchangeResult = this.amount * KRW_USD_RATE;
                break;
            case CURRENCY_JPY:
                this.exchangeResult = this.amount * KRW_JPY_RATE;
                break;
        }
        return this.exchangeResult;
    }
}
