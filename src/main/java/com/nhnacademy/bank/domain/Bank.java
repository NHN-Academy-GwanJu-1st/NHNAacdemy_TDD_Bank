package com.nhnacademy.bank.domain;

public class Bank {

    private final long accountId;
    private final Money money;

    public Bank(long accountId, Money money) {
        this.accountId = accountId;
        this.money = money;
    }

    public Money getMoney() {
        return this.money;
    }

    public double getBankOfAmount() {
        return this.getMoney().getAmount();
    }

    public Currency getBankOfCurrency() {
        return this.getMoney().getCurrency();
    }

}
