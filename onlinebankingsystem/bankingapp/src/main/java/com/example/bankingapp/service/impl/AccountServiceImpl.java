package com.example.bankingapp.service.impl;

import com.example.bankingapp.dto.AccountDto;
import com.example.bankingapp.repository.AccountRepository;
import com.example.bankingapp.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        return null;
    }
}
