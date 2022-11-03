package com.nhnacademy.bank.service;

import com.nhnacademy.bank.domain.ExchangeFee;
import com.nhnacademy.bank.domain.Bank;
import com.nhnacademy.bank.domain.Currency;
import com.nhnacademy.bank.exception.ImpossibleExchangeException;
import com.nhnacademy.bank.exception.NotFoundBankException;
import com.nhnacademy.bank.factory.ExchangeFactory;
import com.nhnacademy.bank.factory.IntegrationExchangeFactory;
import com.nhnacademy.bank.repository.BankRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class BankService {

    private final BankRepository bankRepository;

    private final double FIXED_FEE_RATE = 0.01;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public void exchange(long bankId, Currency targetCurrency) {

        Bank bank = bankRepository.findById(bankId);

        if (Objects.isNull(bank)) {
            throw new NotFoundBankException(bankId);
        }

        if(targetCurrency.equals(bank.getBankOfCurrency())){
            throw new ImpossibleExchangeException(targetCurrency);
        }

        eachExchangeCountry(targetCurrency, bank);

        log.info("환전 후 금액 {} ", bank.getMoney());

        ExchangeFee exchangeFee = new ExchangeFee(FIXED_FEE_RATE);
        double fee = calculateFee(bank, exchangeFee);
        log.info("환전 수수료 {}", fee);

    }

    private void eachExchangeCountry(Currency targetCurrency, Bank bank) {
        ExchangeFactory exchangeFactory = new IntegrationExchangeFactory();
        bank.getMoney().exchangeAmount(
                exchangeFactory
                        .exchangeByEachCountry(bank.getBankOfAmount(), bank.getBankOfCurrency(), targetCurrency)
        );
        bank.getMoney().exchangeCurrency(targetCurrency);
        bank.getMoney().amountRoundingOff();
    }

    private double calculateFee(Bank bank, ExchangeFee exchangeFee) {
        return bank.getBankOfAmount() * exchangeFee.getFee();
    }
}
