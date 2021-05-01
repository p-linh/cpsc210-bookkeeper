package model;

import static org.junit.jupiter.api.Assertions.*;

import model.account.Account;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

// unit tests for the Account class
class AccountTest {
    private Account a;

    @BeforeEach
    void runBefore() {
        a = new Account("RESP", 10000, "CAD");
    }

    @Test
    void testConstructor() {
        assertEquals("RESP", a.getAccountName());
        assertEquals(10000, a.getBalance());
        assertEquals("CAD", a.getCurrency());
        assertEquals(0, a.getNumInvestments());
    }

    @Test
    void testSuccessfulWithdrawal() {
        assertTrue(a.withdraw(3050.78));
        assertEquals(10000 - 3050.78, a.getBalance());
    }

    @Test
    void testMultipleSuccessfulWithdrawals() {
        assertTrue(a.withdraw(403.45));
        assertTrue(a.withdraw(1976.12));
        assertTrue(a.withdraw(4568.23));
        assertEquals(10000 - 403.45 - 1976.12 - 4568.23, a.getBalance());
    }

    @Test
    void testFailingWithdrawal() {
        assertFalse(a.withdraw(13490.40));
        assertEquals(10000, a.getBalance());
    }

    @Test
    void testSuccessfulThenFailingWithdrawal() {
        assertTrue(a.withdraw(8782.45));
        assertFalse(a.withdraw(4629.90));
        assertEquals(10000 - 8782.45, a.getBalance());
    }

    @Test
    void testDeposit() {
        a.deposit(4589.30);
        assertEquals(10000 + 4589.30, a.getBalance());
    }

    @Test
    void testMultipleDeposits() {
        a.deposit(345.78);
        a.deposit(5678.20);
        a.deposit(148.65);
        assertEquals(10000 + 345.78 + 5678.20 + 148.65, a.getBalance());
    }

    @Test
    void testGetInvestmentOfIndex() {
        Investment i1 = new Investment("ABC", "Company A");
        Investment i2 = new Investment("DEF", "Company B");
        a.addInvestment(i1);
        a.addInvestment(i2);
        assertEquals(i1, a.getInvestmentOf(0));
        assertEquals(i2, a.getInvestmentOf(1));
    }

    @Test
    void testGetInvestmentWithSymbol() {
        Investment i1 = new Investment("ABC", "Company A");
        Investment i2 = new Investment("DEF", "Company B");
        assertNull(a.getInvestmentWithSymbol("ABC"));
        a.addInvestment(i1);
        a.addInvestment(i2);

        assertEquals(i1, a.getInvestmentWithSymbol("ABC"));
        assertEquals(i2, a.getInvestmentWithSymbol("DEF"));
    }

    @Test
    void testAddOneInvestmentSufficientBalance() {
        Investment i = new Investment("ABC", "Company A");
        i.purchaseShares(LocalDate.of(2020,2,3), 1.34, 400);

        assertTrue(a.addInvestment(i));
        assertTrue(a.getBalance() > 0);
        assertEquals(10000 + i.netProfit(), a.getBalance());
        assertEquals(i, a.getInvestmentWithSymbol("ABC"));
        assertEquals(1, a.getNumInvestments());

    }

    @Test
    void testAddMultipleInvestmentsSufficientBalance() {
        Investment i1 = new Investment("ABC", "Company A");
        i1.purchaseShares(LocalDate.of(2020,2,3), 1.34, 400);

        Investment i2 = new Investment("DEF", "Company B");
        i2.purchaseShares(LocalDate.of(2019, 10,4), 4.56, 100);

        assertTrue(a.addInvestment(i1));
        assertTrue(a.addInvestment(i2));

        assertTrue(a.getBalance() > 0);
        assertEquals(10000 + i1.netProfit() + i2.netProfit(), a.getBalance());
        assertEquals(i1, a.getInvestmentWithSymbol("ABC"));
        assertEquals(i2, a.getInvestmentWithSymbol("DEF"));
        assertEquals(2, a.getNumInvestments());
    }

    @Test
    void testAddInvestmentsInsufficientBalance() {
        Investment i1 = new Investment("ABC", "Company A");
        i1.purchaseShares(LocalDate.of(2020,2,3), 10.34, 400);

        Investment i2 = new Investment("DEF", "Company B");
        i2.purchaseShares(LocalDate.of(2019, 10,4), 60.56, 120);

        assertTrue(a.addInvestment(i1));
        assertFalse(a.addInvestment(i2));

        assertEquals(10000 + i1.netProfit(), a.getBalance());
        assertEquals(i1, a.getInvestmentWithSymbol("ABC"));
        assertEquals(1, a.getNumInvestments());
    }

    @Test
    void testAddDuplicateInvestment() {
        Investment i = new Investment("ABC", "Company A");
        assertTrue(a.addInvestment(i));
        assertEquals(1, a.getNumInvestments());

        Investment duplicate = new Investment("ABC", "Company A");
        assertFalse(a.addInvestment(duplicate));
        assertEquals(1, a.getNumInvestments());
    }

    @Test
    void testRemoveInvestment() {
        Investment i1 = new Investment("ABC", "Company A");
        assertEquals(0, a.getNumInvestments());
        a.removeInvestment(i1);
        assertEquals(0, a.getNumInvestments());

        a.addInvestment(i1);
        assertEquals(1, a.getNumInvestments());
        a.removeInvestment(i1);
        assertEquals(0, a.getNumInvestments());
    }

    @Test
    void testAccountToJson() {
        JSONObject jsonObject = a.toJson();
        assertEquals(a.getAccountName(), jsonObject.getString("name"));
        assertEquals(a.getBalance(), jsonObject.getDouble("balance"));
        assertEquals(a.getCurrency(), jsonObject.getString("currency"));
        assertEquals(0, jsonObject.getJSONArray("investments").length());
        assertEquals(4, JSONObject.getNames(jsonObject).length);

        Investment i1 = new Investment("ABC", "Company A");
        a.addInvestment(i1);
        jsonObject = a.toJson();
        assertEquals(1, jsonObject.getJSONArray("investments").length());
    }
}