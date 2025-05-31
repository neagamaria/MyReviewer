package com.awbd.myreviewer.services;

import com.awbd.myreviewer.domain.Account;
import com.awbd.myreviewer.dtos.AccountDTO;
import com.awbd.myreviewer.exceptions.ResourceNotFoundException;
import com.awbd.myreviewer.repositories.AccountRepository;
import com.awbd.myreviewer.services.AccountService;
import org.springframework.stereotype.Service;

import com.awbd.myreviewer.mappers.AccountMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<AccountDTO> findAll() {
        List<Account> accounts = new LinkedList<>();

        accountRepository.findAll().iterator().forEachRemaining(accounts::add);

        return accounts.stream().map(accountMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public AccountDTO findById(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);

        if(accountOptional.isEmpty()) {
            throw new ResourceNotFoundException("account " + id + " not found");
        }

        return accountMapper.toDto(accountOptional.get());
    }

    @Override
    public AccountDTO findByName(String name) {
        Optional<Account> accountOptional = accountRepository.findByName(name);

        if(accountOptional.isEmpty()) {
            throw new ResourceNotFoundException("account " + name + " not found");
        }

        return accountMapper.toDto(accountOptional.get());
    }

    @Override
    public AccountDTO save(AccountDTO accountDTO) {
        Account account = accountRepository.save(accountMapper.toAccount(accountDTO));

        return accountMapper.toDto(account);
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }



}
