package model;

import model.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

// unit tests for Transaction
public class TransactionTest {
    private Transaction t;

    @BeforeEach
    void runBefore() {
        t = new Transaction(LocalDate.of(2020, 1,1), 1394.5, 10, Transaction.Type.PURCHASE);
    }

    @Test
    void testConstructor() {
        assertEquals(LocalDate.of(2020, 1,1), t.getDate());
        assertEquals(1394.5, t.getPrice());
        assertEquals(Transaction.Type.PURCHASE, t.getTransactionType());
        assertEquals(10, t.getQuantity());

        Transaction anotherTransaction = new Transaction(LocalDate.of(2019, 1, 1),784.2, 15,
                                                        Transaction.Type.SALE);
        assertEquals(LocalDate.of(2019, 1, 1), anotherTransaction.getDate());
        assertEquals(784.2, anotherTransaction.getPrice());
        assertEquals(Transaction.Type.SALE, anotherTransaction.getTransactionType());
        assertEquals(15, anotherTransaction.getQuantity());
    }

    @Test
    void testTotalTransactionAmount() {
        assertEquals(1394.5 * 10, t.totalTransactionAmount());
    }
}
