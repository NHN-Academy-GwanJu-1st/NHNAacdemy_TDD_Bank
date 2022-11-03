package com.nhnacademy.bank.factory;

import com.nhnacademy.bank.domain.Currency;
import com.nhnacademy.bank.domain.Money;

public class IntegrationExchangeFactory implements ExchangeFactory {

    @Override
    public double exchangeByEachCountry(double amount, Currency currentCurrency ,Currency targetCurrency) {

        double result = 0;

        switch (currentCurrency) {
            case CURRENCY_KRW:
                result =  new KoreaExchangeFactory(amount, targetCurrency).exchangeProcess();
            break;
            case CURRENCY_USD:
                result =  new UsaExchangeFactory(amount, targetCurrency).exchangeProcess();
            break;
            case CURRENCY_JPY:
                result =  new JapanExchangeFactory(amount, targetCurrency).exchangeProcess();
            break;
        }

        return result;
    }
}
