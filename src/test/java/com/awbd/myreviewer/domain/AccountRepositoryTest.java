package com.awbd.myreviewer.domain;

import com.awbd.myreviewer.repositories.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;


@DataJpaTest
@ActiveProfiles("h2")
@Slf4j

// test the functionalities of AccountRepository
public class AccountRepositoryTest {
    AccountRepository accountRepository;

    @Autowired
    AccountRepositoryTest(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Test
    public void findByName() {
        List<Account> accounts = accountRepository.findByNameLike("%User%");
        assertFalse(accounts.isEmpty());
        log.info("findByLastNameLike...");
        accounts.forEach(account -> log.info(account.getName()));
    }

    @Test
    public void findByIds() {
        List<Account> accounts = accountRepository.findByIdIn(Arrays.asList(1L, 2L));
        assertFalse(accounts.isEmpty());
        log.info("findByIds...");
        accounts.forEach(account -> log.info(account.getName()));
    }
}
