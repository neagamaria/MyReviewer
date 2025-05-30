package com.awbd.myreviewer.services;

import com.awbd.myreviewer.dtos.AccountDTO;

import java.util.List;

public interface AccountService {
    List<AccountDTO> findAll();
    AccountDTO findById(Long id);
    AccountDTO findByName(String name);
    AccountDTO save(AccountDTO account);

    void deleteById(Long id);
}
