package model;

import model.account.Account;
import model.transaction.Transaction;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.PublicKey;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

// unit tests for Investment
public class InvestmentTest {
    private Investment i;

    @BeforeEach
    void runBefore() {
        i = new Investment("A", "Company A");
    }

    @Test
    void testConstructor() {
        assertEquals("A", i.getSymbol());
        assertEquals("Company A", i.getName());
        assertTrue(i.getTransactions().isEmpty());
        assertEquals(0, i.getCurrentShares());
    }

    @Test
    void testPurchaseOnce() {
        LocalDate purchaseDate = LocalDate.of(2019, 8, 3);
        i.purchaseShares(purchaseDate, 14.3, 100);

        checkTransactions(purchaseDate, 14.3, 100, Transaction.Type.PURCHASE, 0);

        assertEquals(100, i.getCurrentShares());
    }

    @Test
    void testMultiplePurchases() {
        LocalDate purchaseDate = LocalDate.of(2019, 8, 3);
        i.purchaseShares(purchaseDate, 14.3, 100);
        i.purchaseShares(purchaseDate, 13.2, 250);

        checkTransactions(purchaseDate, 14.3, 100, Transaction.Type.PURCHASE, 0);
        checkTransactions(purchaseDate, 13.2, 250, Transaction.Type.PURCHASE, 1);


        assertEquals(100 + 250, i.getCurrentShares());
    }

    @Test
    void testPurchaseAndSellAllSharesAtOnce() {
        LocalDate date = LocalDate.of(2019, 8, 3);
        i.purchaseShares(date, 14.3, 100);
        i.sellShares(date, 17.45, 100);

        assertEquals(0, i.getCurrentShares());
        assertEquals(i.getTransactions().get(1).totalTransactionAmount()
                - i.getTransactions().get(0).totalTransactionAmount(), i.netProfit());

        checkTransactions(date, 14.3, 100, Transaction.Type.PURCHASE, 0);
        checkTransactions(date, 17.45, 100, Transaction.Type.SALE, 1);

    }

    @Test
    void testPurchaseAndSellSomeShares() {
        LocalDate date = LocalDate.of(2019, 8, 3);
        i.purchaseShares(date, 14.3, 100);
        i.sellShares(date, 17.45, 50);

        assertEquals(50, i.getCurrentShares());
        assertEquals(i.getTransactions().totalAmountOfTransactionsOf(Transaction.Type.SALE) -
                i.getTransactions().totalAmountOfTransactionsOf(Transaction.Type.PURCHASE), i.netProfit());

        checkTransactions(date, 14.3, 100, Transaction.Type.PURCHASE, 0);
        checkTransactions(date, 17.45, 50, Transaction.Type.SALE, 1);

    }

    @Test
    void testSellSharesNotEnoughShares() {
        LocalDate date = LocalDate.of(2019, 8, 3);
        i.purchaseShares(date, 14.3, 100);
        i.sellShares(date, 17.45, 200);

        assertEquals(100, i.getCurrentShares());
        assertEquals(-14.3 * 100, i.netProfit());

        checkTransactions(date, 14.3, 100, Transaction.Type.PURCHASE, 0);
    }

    @Test
    void testNetProfit() {
        assertEquals(0, i.netProfit());
        LocalDate date = LocalDate.of(2019, 8, 3);
        i.purchaseShares(date, 14.3, 100);
        i.purchaseShares(date, 12.2, 300);
        i.sellShares(date, 18.3, 200);
        i.sellShares(LocalDate.of(2019, 8, 4), 14.5, 200);

        double netProfit = i.getTransactions().totalAmountOfTransactionsOf(Transaction.Type.SALE) -
                i.getTransactions().totalAmountOfTransactionsOf(Transaction.Type.PURCHASE);

        assertEquals(netProfit, i.netProfit());

    }

    @Test
    void testHashCodeAndEquals() {
        Investment duplicate = new Investment("A", "Company A");
        Investment duplicate2 = new Investment("a", "Company B");

        assertTrue(i.equals(duplicate2));
        assertTrue(i.equals(duplicate));
        assertTrue(i.equals(i));
        assertFalse(i.equals(null));
        assertFalse(i.equals(new Account("A", 400, "CAD")));

        assertTrue(i.hashCode() == duplicate.hashCode());
        assertTrue(i.hashCode() == duplicate2.hashCode());

        Investment notDuplicate = new Investment("B", "Company B");
        assertFalse(i.hashCode() == notDuplicate.hashCode());
    }

    // EFFECTS: checks whether the transaction at the given index in the list has the given date, price and quantity
    void checkTransactions(LocalDate date, double price, int quantity, Transaction.Type type, int index) {
        assertEquals(date, i.getTransactions().get(index).getDate());
        assertEquals(price, i.getTransactions().get(index).getPrice());
        assertEquals(type, i.getTransactions().get(index).getTransactionType());
        assertEquals(quantity, i.getTransactions().get(index).getQuantity());
    }

    @Test
    void testInvestmentToJson() {
        JSONObject jsonObject = i.toJson();

        assertEquals(i.getName(), jsonObject.getString("name"));
        assertEquals(i.getSymbol(), jsonObject.getString("symbol"));
        assertEquals(2, JSONObject.getNames(jsonObject).length);
    }
}
