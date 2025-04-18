package com.awbd.myreviewer.services;

import com.awbd.myreviewer.domain.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersistenceContextTransaction {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Account updateInTransaction(Long AccountId, String name) {
        Account updatedAccount = entityManager.find(Account.class, AccountId);
        updatedAccount.setName(name);
        entityManager.persist(updatedAccount);
        return updatedAccount;
    }

    public Account update(Long AccountId, String name) {
        Account updatedAccount = entityManager.find(Account.class, AccountId);
        updatedAccount.setName(name);
        entityManager.persist(updatedAccount);
        return updatedAccount;
    }

    public Account find(long id) {
        return entityManager.find(Account.class, id);
    }
}
