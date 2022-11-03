package com.nhnacademy.bank.domain;

import com.nhnacademy.bank.exception.DifferentCurrencyException;
import com.nhnacademy.bank.exception.InvalidAmountException;

public class Money {

    private double amount;
    private Currency currency;

    public Money(double amount, Currency currency) {
        if (amount < 0) {
            throw new InvalidAmountException(amount);
        }
        this.amount = amount;
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void exchangeCurrency(Currency currency) {
        this.currency = currency;
    }

    public void exchangeAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return currency == money.currency;
    }

    public void deposit(Money money) {
        if (money.getAmount() < 0) {
            throw new InvalidAmountException(money.getAmount());
        }

        if (!this.equals(money)) {
            throw new DifferentCurrencyException(money.getCurrency());
        }

        this.amount += money.getAmount();
    }

    public void withdraw(Money money) {
        if (!this.equals(money)) {
            throw new DifferentCurrencyException(money.getCurrency());
        }

        if (this.getAmount() < money.getAmount()) {
            throw new InvalidAmountException(money.getAmount());
        }

        this.amount -= money.getAmount();
    }

    public void amountRoundingOff() {
        if (this.currency.equals(Currency.CURRENCY_USD)) {
            this.exchangeAmount(Math.round(this.getAmount() * 100) / (double) 100);
        } else if (this.currency.equals(Currency.CURRENCY_KRW)) {
            this.exchangeAmount(Math.round(this.getAmount() / 10) * 10);
        } else if (this.currency.equals(Currency.CURRENCY_JPY)) {
            this.exchangeAmount(Math.round(this.getAmount()));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (this.currency.equals(Currency.CURRENCY_KRW)) {
            sb.append(String.format("%.0f ", this.getAmount()));
            sb.append("원 ");
        }

        if (this.currency.equals(Currency.CURRENCY_USD)) {
            sb.append(String.format("%.2f ", this.getAmount()));
            sb.append("$");
        }

        if (this.currency.equals(Currency.CURRENCY_JPY)) {
            sb.append(String.format("%.0f ", this.getAmount()));
            sb.append("¥");
        }

        return String.valueOf(sb);
    }


}
