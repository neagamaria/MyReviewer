package com.awbd.myreviewer.domain;

import com.awbd.myreviewer.services.PersistenceContextExtended;
import com.awbd.myreviewer.services.PersistenceContextTransaction;
import jakarta.persistence.TransactionRequiredException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes=com.awbd.myreviewer.MyReviewerApplication.class)
@ActiveProfiles("h2")

public class PersistenceContextTest {
    @Autowired
    PersistenceContextExtended persistenceContextExtended;

    @Autowired
    PersistenceContextTransaction persistenceContextTransaction;

    @Test
    public void persistenceContextTransactionThrowException() {
        assertThrows(TransactionRequiredException.class,
                () -> persistenceContextTransaction.update(1L, "AccountTest"));
    }

    @Test
    public void persistenceContextTransactionInTransaction() {
        persistenceContextTransaction.updateInTransaction(1L, "AccountTest");
        Account accountExtended = persistenceContextExtended.find(1L);
        System.out.println(accountExtended.getName());
        assertEquals("AccountTest", accountExtended.getName());
    }


    @Test
    public void persistenceContextExtendedNoTransaction() {
        persistenceContextExtended.update(1L, "AccountTest2");
        Account accountExtended = persistenceContextExtended.find(1L);
        System.out.println(accountExtended.getName());
        assertEquals("AccountTest2", accountExtended.getName());
    }

    @Test
    public void persistenceContextExtendedInTransaction() {
        persistenceContextExtended.update(1L, "Will");
        Account AccountTransaction = persistenceContextTransaction.find(1L);
        System.out.println(AccountTransaction.getName());
        assertNotEquals("Will",AccountTransaction.getName());
    }
}
