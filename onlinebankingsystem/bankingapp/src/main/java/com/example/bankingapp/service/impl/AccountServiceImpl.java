//package com.example.bankingapp.service.impl;
//
//import com.example.bankingapp.dto.AccountDto;
//import com.example.bankingapp.entity.Account;
//import com.example.bankingapp.mapper.AccountMapper;
//import com.example.bankingapp.repository.AccountRepository;
//import com.example.bankingapp.service.AccountService;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.util.logging.Logger;
//
//@Service
//public class AccountServiceImpl implements AccountService {
//    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
//
//    private AccountRepository accountRepository;
//
//    public AccountServiceImpl(AccountRepository accountRepository) {
//
//        this.accountRepository = accountRepository;
//    }
//
//    @Override
//    public AccountDto createAccount(AccountDto accountDto) {
//        Account account = AccountMapper.mapToAccount(accountDto);
//        Account savedAccount = accountRepository.save(account);
//        return AccountMapper.mapToAccountDto(savedAccount);
//    }
//
//    @Override
//    public AccountDto getAccountById(Long id) {
//
//      Account account =  accountRepository
//              .findById(id)
//              .orElseThrow(() -> new RuntimeException("Account does not exists"));
//        return AccountMapper.mapToAccountDto(account);
//    }
//
//    @Override
//    public AccountDto deposit(Long id, double amount) {
//
//        Account account =  accountRepository
//                .findById(id)
//                .orElseThrow(() -> new RuntimeException("Account does not exists"));
//
//        double total = account.getBalance() + amount;
//        account.setBalance(total);
//        Account savedAccount =  accountRepository.save(account);
//        return AccountMapper.mapToAccountDto(savedAccount);
//    }
//
//
//}


package com.example.bankingapp.service.impl;

import com.example.bankingapp.dto.AccountDto;
import com.example.bankingapp.entity.Account;
import com.example.bankingapp.mapper.AccountMapper;
import com.example.bankingapp.repository.AccountRepository;
import com.example.bankingapp.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        logger.info("Creating account with details: {}", accountDto);
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        logger.info("Fetching account with id: {}", id);
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        logger.info("Depositing amount: {} to account with id: {}", amount, id);
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));
        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {

        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient amount");
        }

        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

}
