package com.nhnacademy.bank.factory;

import com.nhnacademy.bank.domain.Currency;
import com.nhnacademy.bank.domain.Money;

public interface ExchangeFactory {

    double exchangeByEachCountry(double amount, Currency currentCurrency, Currency targetCurrency);

}


