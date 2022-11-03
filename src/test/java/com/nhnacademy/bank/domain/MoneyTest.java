package com.nhnacademy.bank.domain;

import com.nhnacademy.bank.domain.Currency;
import com.nhnacademy.bank.domain.Money;
import com.nhnacademy.bank.exception.DifferentCurrencyException;
import com.nhnacademy.bank.exception.InvalidAmountException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MoneyTest {

    /*
     * equals 체크 필요     V
     * 돈 타입이 다를경우 더하기   V
     * 돈 타입이 같을경우 더하기   V
     * 돈 타입이 다를경우 빼기 V
     * 돈 타입이 같을경우 빼기    V
     * 돈 타입이 같지만 결과가 음수일 경우 V
     * 돈 음수 체크 V
     * */

    Currency currency;


    @Test
    void money_invalidCheck() {
        long amount = -1;

        Assertions.assertThatThrownBy(() -> new Money(amount, Currency.CURRENCY_KRW))
                .isInstanceOf(InvalidAmountException.class)
                .hasMessageContaining("Invalid Amount", amount);

    }

    @Test
    void money_currency_equalsCheck() {
        Money krwAccount = new Money(1000, Currency.CURRENCY_KRW);
        Money usdAccount = new Money(2000, Currency.CURRENCY_USD);

        assertThat(krwAccount.equals(usdAccount)).isFalse();
        assertThat(krwAccount.equals(new Money(2000, Currency.CURRENCY_KRW))).isTrue();
    }

    @Test
    void identical_currency_deposit() {
        currency = Currency.CURRENCY_KRW;
        Money myAccount = new Money(1000, currency);
        Money targetAccount = new Money(2000, currency);

        if (myAccount.equals(targetAccount)) {
            myAccount.deposit(targetAccount);
        }

        assertThat(myAccount.getAmount()).isEqualTo(3000);
    }

    @Test
    void different_currency_deposit() {

        Money krwAccount = new Money(1000, Currency.CURRENCY_KRW);
        Money usdAccount = new Money(2000, Currency.CURRENCY_USD);

        Assertions.assertThatThrownBy(() -> krwAccount.deposit(usdAccount))
                .isInstanceOf(DifferentCurrencyException.class)
                .hasMessageContaining("Not Match Currency");
    }

    @Test
    void different_currency_withdraw() {

        Money krwAccount = new Money(1000, Currency.CURRENCY_KRW);
        Money usdAccount = new Money(2000, Currency.CURRENCY_USD);

        Assertions.assertThatThrownBy(() -> krwAccount.withdraw(usdAccount))
                .isInstanceOf(DifferentCurrencyException.class)
                .hasMessageContaining("Not Match Currency");
    }


    @Test
    void identical_currency_withdraw_thenThrowInvalidAmountException() {

        Money myAccount = new Money(1000, Currency.CURRENCY_KRW);
        Money targetAccount = new Money(2000, Currency.CURRENCY_KRW);

        Assertions.assertThatThrownBy(() -> myAccount.withdraw(targetAccount))
                .isInstanceOf(InvalidAmountException.class)
                .hasMessageContaining("Invalid Amount");
    }

    @Test
    void identical_currency_withdraw() {
        Money myAccount = new Money(2000, Currency.CURRENCY_KRW);
        Money targetAccount = new Money(1000, Currency.CURRENCY_KRW);

        myAccount.withdraw(targetAccount);

        assertThat(myAccount.getAmount()).isEqualTo(1000);
    }

}