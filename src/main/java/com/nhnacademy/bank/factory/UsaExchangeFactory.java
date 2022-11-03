package com.nhnacademy.bank.factory;

import com.nhnacademy.bank.domain.Currency;

public class UsaExchangeFactory {

    private double amount;
    private final Currency targetCurrency;

    private double exchangeResult;

    private final double USA_KRW_RATE = 1000;

    private final double USA_JPY_RATE = 100;


    public UsaExchangeFactory(double amount, Currency targetCurrency) {
        this.amount = amount;
        this.targetCurrency = targetCurrency;
        this.exchangeResult = 0;
    }

    public double exchangeProcess() {

        switch (targetCurrency) {
            case CURRENCY_KRW:
                this.exchangeResult = this.amount * USA_KRW_RATE;
                break;
            case CURRENCY_JPY:
                this.exchangeResult = this.amount * USA_JPY_RATE;
                break;
        }

        return this.exchangeResult;
    }
}
