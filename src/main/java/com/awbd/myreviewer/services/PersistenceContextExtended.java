package com.awbd.myreviewer.services;

import com.awbd.myreviewer.domain.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PersistenceContextExtended {
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Transactional
    public Account updateInTransaction(Long accountId, String name)  {
        Account updatedAccount = entityManager.find(Account.class, accountId);
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
