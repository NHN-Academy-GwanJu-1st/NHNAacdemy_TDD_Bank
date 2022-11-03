package com.nhnacademy.bank.service;

import com.nhnacademy.bank.domain.Bank;
import com.nhnacademy.bank.domain.Currency;
import com.nhnacademy.bank.domain.Money;
import com.nhnacademy.bank.exception.ImpossibleExchangeException;
import com.nhnacademy.bank.exception.InvalidAmountException;
import com.nhnacademy.bank.exception.NotFoundBankException;
import com.nhnacademy.bank.repository.BankRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BankServiceTest {

    /*
     * 만약 계좌가 존재하지 않을 경우    V
     * 바꾸려는 돈의 타입과 기존의 돈 타입이 같을경우 예외    V
     * 내 계좌의 머니가 Currency KRW -> USD 이거 확인  V
     * 내 계좌 머니의 돈 ex)3000원일 경우 환율에따라 변했는지   V
     * 달러->원화 환전값이 5원 이상일떄 10원으로 반올림 되는지    V
     * 원화->달러 환전값이 $0.005이상일때 0.01로 반올림 되는지     V
     *
     * */
    BankRepository bankRepository;
    BankService bankService;

    @BeforeEach
    void setUp(){
        bankRepository = mock(BankRepository.class);
        bankService = new BankService(bankRepository);
    }

    @Test
    void not_exist_bankAccount() {
        long bankId = 123;
        Currency targetCurrency = Currency.CURRENCY_KRW;

        when(bankRepository.findById(bankId)).thenReturn(null);

        Assertions.assertThatThrownBy(() -> bankService.exchange(bankId, targetCurrency))
                .isInstanceOf(NotFoundBankException.class)
                .hasMessageContaining("Not Found Bank", bankId);
    }

    @Test
    void exchange_SameCurrency_thenImpossibleExchangeException(){
        long bankId = 123;
        Currency targetCurrency = Currency.CURRENCY_KRW;

        Bank bank = new Bank(123, new Money(3000, Currency.CURRENCY_KRW));

        when(bankRepository.findById(bankId)).thenReturn(bank);

        Assertions.assertThatThrownBy(() -> bankService.exchange(bankId, targetCurrency))
                .isInstanceOf(ImpossibleExchangeException.class)
                .hasMessageContaining("Impossible Exchange", targetCurrency);
    }

    @Test
    void exchange_KRW_to_USD(){
        long bankId = 123;
        Currency targetCurrency = Currency.CURRENCY_USD;

        Bank bank = new Bank(123, new Money(5550, Currency.CURRENCY_KRW));

        when(bankRepository.findById(bankId)).thenReturn(bank);
        bankService.exchange(bankId, targetCurrency);

        assertThat(bank.getMoney().getAmount()).isEqualTo(5.55);
        assertThat(bank.getMoney().getCurrency()).isEqualTo(Currency.CURRENCY_USD);

    }

    @Test
    void exchange_USD_to_KRW(){
        long bankId = 123;
        Currency targetCurrency = Currency.CURRENCY_KRW;

        Bank bank = new Bank(123, new Money(3.12, Currency.CURRENCY_USD));

        when(bankRepository.findById(bankId)).thenReturn(bank);

        bankService.exchange(bankId, targetCurrency);

        assertThat(bank.getMoney().getAmount()).isEqualTo(3120);
        assertThat(bank.getMoney().getCurrency()).isEqualTo(Currency.CURRENCY_KRW);

    }

    @Test
    void exchange_KRW_to_JPY() {
        long bankId = 123;
        Currency targetCurrency = Currency.CURRENCY_JPY;

        Bank bank = new Bank(123, new Money(3000, Currency.CURRENCY_KRW));
        when(bankRepository.findById(bankId)).thenReturn(bank);

        bankService.exchange(bankId, targetCurrency);
        assertThat(bank.getBankOfAmount()).isEqualTo(300);
        assertThat(bank.getBankOfCurrency()).isEqualTo(Currency.CURRENCY_JPY);

    }

    @Test
    void exchange_USD_to_JPY() {
        long bankId = 123;
        Currency targetCurrency = Currency.CURRENCY_JPY;

        Bank bank = new Bank(123, new Money(20, Currency.CURRENCY_USD));
        when(bankRepository.findById(bankId)).thenReturn(bank);

        bankService.exchange(bankId, targetCurrency);
        assertThat(bank.getBankOfAmount()).isEqualTo(2000);
        assertThat(bank.getBankOfCurrency()).isEqualTo(Currency.CURRENCY_JPY);

    }

    @Test
    void exchange_JPY_to_USD() {
        long bankId = 123;
        Currency targetCurrency = Currency.CURRENCY_USD;

        Bank bank = new Bank(123, new Money(3000, Currency.CURRENCY_JPY));
        when(bankRepository.findById(bankId)).thenReturn(bank);

        bankService.exchange(bankId, targetCurrency);
        assertThat(bank.getBankOfAmount()).isEqualTo(30);
        assertThat(bank.getBankOfCurrency()).isEqualTo(Currency.CURRENCY_USD);

    }

    @Test
    void exchange_JPY_to_KRW() {
        long bankId = 123;
        Currency targetCurrency = Currency.CURRENCY_KRW;

        Bank bank = new Bank(123, new Money(3000, Currency.CURRENCY_JPY));
        when(bankRepository.findById(bankId)).thenReturn(bank);

        bankService.exchange(bankId, targetCurrency);
        assertThat(bank.getBankOfAmount()).isEqualTo(30000);
        assertThat(bank.getBankOfCurrency()).isEqualTo(Currency.CURRENCY_KRW);

    }

}