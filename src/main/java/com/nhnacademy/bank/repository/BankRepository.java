package com.nhnacademy.bank.repository;

import com.nhnacademy.bank.domain.Bank;

public interface BankRepository {
    Bank findById(long id);
}
