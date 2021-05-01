package model;

import model.transaction.Transaction;
import model.transaction.TransactionList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// unit tests for TransactionList
public class TransactionListTest {
    private TransactionList tl;
    private Transaction t1;
    private Transaction t2;
    private Transaction t3;

    @BeforeEach
    void runBefore() {
        tl = new TransactionList();
        t1 = new Transaction(LocalDate.of(2020, 7, 4), 100, 5, Transaction.Type.PURCHASE);
        t2 = new Transaction(LocalDate.of(2019, 6, 14), 83.94, 20, Transaction.Type.SALE);
        t3 = new Transaction(LocalDate.of(2014, 12, 21), 780.45, 3, Transaction.Type.SALE);
    }

    @Test
    void testConstructor() {
        assertTrue(tl.isEmpty());
    }

    @Test
    void testAddOneTransaction() {
        tl.addTransaction(t1);

        assertEquals(1, tl.size());
        assertEquals(LocalDate.of(2020,7,4), tl.get(0).getDate());
        assertEquals(100, tl.get(0).getPrice());
        assertEquals(Transaction.Type.PURCHASE, tl.get(0).getTransactionType());
        assertEquals(5, tl.get(0).getQuantity());
    }

    @Test
    void testAddManyTransactions() {
        tl.addTransaction(t1);
        tl.addTransaction(t2);
        tl.addTransaction(t3);

        assertEquals(3, tl.size());
        assertEquals(LocalDate.of(2020,7,4), tl.get(0).getDate());
        assertEquals(100, tl.get(0).getPrice());
        assertEquals(Transaction.Type.PURCHASE, tl.get(0).getTransactionType());
        assertEquals(5, tl.get(0).getQuantity());

        assertEquals(LocalDate.of(2019,6,14), tl.get(1).getDate());
        assertEquals(83.94, tl.get(1).getPrice());
        assertEquals(Transaction.Type.SALE, tl.get(1).getTransactionType());
        assertEquals(20, tl.get(1).getQuantity());

        assertEquals(LocalDate.of(2014,12,21), tl.get(2).getDate());
        assertEquals(780.45, tl.get(2).getPrice());
        assertEquals(Transaction.Type.SALE, tl.get(2).getTransactionType());
        assertEquals(3, tl.get(2).getQuantity());
    }

    @Test
    void testSize() {
        assertTrue(tl.isEmpty());

        tl.addTransaction(t1);
        tl.addTransaction(t2);
        tl.addTransaction(t3);

        assertEquals(3, tl.size());
    }

    @Test
    void testGet() {
        tl.addTransaction(t1);
        tl.addTransaction(t2);
        tl.addTransaction(t3);

        assertEquals(t1, tl.get(0));
        assertEquals(t2, tl.get(1));
        assertEquals(t3, tl.get(2));
    }

    @Test
    void testGetTransactionsInRangeNoneInRange() {

        LocalDate date1 = LocalDate.of(2013, 7, 23);
        LocalDate date2 = LocalDate.of(2013, 8, 2);

        List<Transaction> transactionsInRange = tl.getTransactionsInDateRange(date1, date2);
        assertEquals(0, transactionsInRange.size());

        tl.addTransaction(t1);
        tl.addTransaction(t2);
        tl.addTransaction(t3);

        transactionsInRange = tl.getTransactionsInDateRange(date1, date2);
        assertTrue(transactionsInRange.isEmpty());
    }

    @Test
    void testGetTransactionsInRangeAllInRange() {
        LocalDate date1 = LocalDate.of(2014, 1, 1);
        LocalDate date2 = LocalDate.of(2020, 12, 31);

        tl.addTransaction(t1);
        tl.addTransaction(t2);
        tl.addTransaction(t3);

        List<Transaction> transactionsInRange = tl.getTransactionsInDateRange(date1, date2);
        assertEquals(3, transactionsInRange.size());
        assertEquals(t1, transactionsInRange.get(2));
        assertEquals(t2, transactionsInRange.get(1));
        assertEquals(t3, transactionsInRange.get(0));
    }

    @Test
    void testGetTransactionsInRangeSomeInRange() {
        LocalDate date1 = LocalDate.of(2020, 1, 1);
        LocalDate date2 = LocalDate.of(2020, 12, 31);

        tl.addTransaction(t1);
        tl.addTransaction(t2);
        tl.addTransaction(t3);

        List<Transaction> transactionsInRange = tl.getTransactionsInDateRange(date1, date2);
        assertEquals(1, transactionsInRange.size());
        assertEquals(t1, transactionsInRange.get(0));

        date1 = LocalDate.of(2012, 1, 1);
        date2 = LocalDate.of(2019, 12, 31);

        transactionsInRange = tl.getTransactionsInDateRange(date1, date2);
        assertEquals(2, transactionsInRange.size());
        assertEquals(t3, transactionsInRange.get(0));
        assertEquals(t2, transactionsInRange.get(1));
    }

    @Test
    void testSortByDateEmptyList() {
        List<Transaction> sorted = TransactionList.sortByDate(tl);
        assertTrue(sorted.isEmpty());
    }

    @Test
    void testSortByDateNonEmptyList() {
        tl.addTransaction(t1);
        List<Transaction> sorted = TransactionList.sortByDate(tl);

        assertEquals(t1, sorted.get(0));

        tl.addTransaction(t2);
        sorted = TransactionList.sortByDate(tl);

        assertEquals(t2, sorted.get(0));
        assertEquals(t1, sorted.get(1));

        tl.addTransaction(t3);
        sorted = TransactionList.sortByDate(tl);
        assertEquals(t3, sorted.get(0));
        assertEquals(t2, sorted.get(1));
        assertEquals(t1, sorted.get(2));
    }

    @Test
    void testGetTransactionsOnDateNoneOnDate() {
        LocalDate date = LocalDate.of(2020, 1, 1);
        List<Transaction> onDate = tl.getTransactionsOnDate(date);
        assertTrue(onDate.isEmpty());

        tl.addTransaction(t1);
        tl.addTransaction(t2);
        tl.addTransaction(t3);

        onDate = tl.getTransactionsOnDate(date);
        assertTrue(onDate.isEmpty());
    }

    @Test
    void testGetTransactionsOnDateSomeOnDate() {
        tl.addTransaction(t1);
        LocalDate date = LocalDate.of(2020, 7, 4);
        List<Transaction> onDate = tl.getTransactionsOnDate(date);
        assertEquals(1, onDate.size());
        assertEquals(t1, onDate.get(0));

        Transaction t4 = new Transaction(date, 467.23, 7, Transaction.Type.SALE);
        tl.addTransaction(t4);
        onDate = tl.getTransactionsOnDate(date);
        assertEquals(2, onDate.size());
        assertEquals(t1, onDate.get(0));
        assertEquals(t4, onDate.get(1));
    }

    @Test
    void testGetTransactionsOfTypePurchaseNoPurchases() {
        List<Transaction> purchases = tl.getTransactionsOfType(Transaction.Type.PURCHASE);
        assertTrue(purchases.isEmpty());

        tl.addTransaction(t2);
        tl.addTransaction(t3);

        purchases = tl.getTransactionsOfType(Transaction.Type.PURCHASE);
        assertTrue(purchases.isEmpty());
    }

    @Test
    void testGetTransactionsOfTypePurchaseSomePurchases() {
        tl.addTransaction(t1);
        tl.addTransaction(t2);
        tl.addTransaction(t3);

        Transaction t4 = new Transaction(LocalDate.of(2020, 6, 30), 745, 5, Transaction.Type.PURCHASE);
        tl.addTransaction(t4);

        List<Transaction> purchases = tl.getTransactionsOfType(Transaction.Type.PURCHASE);
        assertEquals(2, purchases.size());
        assertEquals(t1, purchases.get(0));
        assertEquals(t4, purchases.get(1));
    }

    @Test
    void testGetTransactionsOfTypeSaleNoSales() {
        List<Transaction> sales = tl.getTransactionsOfType(Transaction.Type.SALE);
        assertTrue(sales.isEmpty());

        tl.addTransaction(t1);

        sales = tl.getTransactionsOfType(Transaction.Type.SALE);
        assertTrue(sales.isEmpty());
    }

    @Test
    void testGetTransactionsOfTypeSaleSomeSales() {
        tl.addTransaction(t1);
        tl.addTransaction(t2);
        tl.addTransaction(t3);

        List<Transaction> sales = tl.getTransactionsOfType(Transaction.Type.SALE);
        assertEquals(2, sales.size());
        assertEquals(t2, sales.get(0));
        assertEquals(t3, sales.get(1));
    }

    @Test
    void testIsEmpty() {
        assertTrue(tl.isEmpty());

        tl.addTransaction(t1);
        assertFalse(tl.isEmpty());
    }

    @Test
    void testTotalAmountOfPurchaseTransactions() {
        double amount = tl.totalAmountOfTransactionsOf(Transaction.Type.PURCHASE);
        assertEquals(0, amount);

        tl.addTransaction(t1);
        tl.addTransaction(t2);
        tl.addTransaction(t3);

        amount = tl.totalAmountOfTransactionsOf(Transaction.Type.PURCHASE);
        assertEquals(t1.totalTransactionAmount(), amount);

        Transaction newPurchase = new Transaction(LocalDate.of(2018, 1, 3),
                43.6, 400, Transaction.Type.PURCHASE);
        tl.addTransaction(newPurchase);

        amount = tl.totalAmountOfTransactionsOf(Transaction.Type.PURCHASE);
        assertEquals(t1.totalTransactionAmount() + newPurchase.totalTransactionAmount(), amount);
    }

    @Test
    void testTotalAmountOfSaleTransactions() {
        double amount = tl.totalAmountOfTransactionsOf(Transaction.Type.SALE);
        assertEquals(0, amount);

        tl.addTransaction(t1);
        tl.addTransaction(t2);
        tl.addTransaction(t3);

        amount = tl.totalAmountOfTransactionsOf(Transaction.Type.SALE);
        assertEquals(t2.totalTransactionAmount() + t3.totalTransactionAmount(), amount);
    }

}
