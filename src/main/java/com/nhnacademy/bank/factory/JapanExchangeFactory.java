package com.nhnacademy.bank.factory;

import com.nhnacademy.bank.domain.Currency;

public class JapanExchangeFactory {

    private double amount;
    private final Currency targetCurrency;

    private  double exchangeResult;

    private final double JPY_KRW_RATE = 10;

    private final double JPY_USD_RATE = 0.01;

    public JapanExchangeFactory(double amount, Currency targetCurrency) {
        this.amount = amount;
        this.targetCurrency = targetCurrency;
        this.exchangeResult = 0;
    }

    public double exchangeProcess() {
        switch (targetCurrency) {
            case CURRENCY_KRW:
                this.exchangeResult = this.amount * JPY_KRW_RATE;
                break;
            case CURRENCY_USD:
                this.exchangeResult = this.amount * JPY_USD_RATE;
                break;
        }
        return this.exchangeResult;
    }
}
