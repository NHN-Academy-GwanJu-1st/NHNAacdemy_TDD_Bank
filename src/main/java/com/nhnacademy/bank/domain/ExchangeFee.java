package com.nhnacademy.bank.domain;

public class ExchangeFee {

    private final double fee;

    public ExchangeFee(double fee) {
        this.fee = fee;
    }

    public double getFee() {
        return fee;
    }
}
