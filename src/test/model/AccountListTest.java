package model;

import model.account.Account;
import model.account.AccountList;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

// unit tests for the AccountList class
public class AccountListTest {
    private AccountList accList;

    @BeforeEach
    void runBefore() {
        accList = new AccountList();
    }

    @Test
    void testConstructor() {
        assertTrue(accList.isEmpty());
    }


    @Test
    void testWithdrawFromOneAccountSufficientBalance() {
        Account newAccount = new Account("CAD RESP", 4005.34, "CAD");
        accList.add(newAccount);
        assertTrue(accList.withdrawFromAccount(newAccount, 405.6));

        double remainingBalance = accList.getAccountByName("CAD RESP").getBalance();
        assertEquals(4005.34 - 405.6, remainingBalance);
    }

    @Test
    void testWithdrawFromOneAccountInsufficientBalance() {
        Account newAccount = new Account("CAD RESP", 4005.34, "CAD");
        accList.add(newAccount);
        assertFalse(accList.withdrawFromAccount(newAccount, 5672.34));

        double remainingBalance = accList.getAccountByName("CAD RESP").getBalance();
        assertEquals(4005.34, remainingBalance);
    }

    @Test
    void testWithDrawFromAccountNotInList() {
        Account a = new Account("CAD RESP", 4005.34, "CAD");
        assertTrue(accList.isEmpty());
        assertFalse(accList.withdrawFromAccount(a, 5672.34));
        assertFalse(accList.contains(a));

    }

    @Test
    void testWithdrawFromManyAccountsSufficientBalance() {
        Account a1 = new Account("CAD RESP", 4005.34, "CAD");
        Account a2 = new Account("CAD TFSA", 3400.4, "CAD");
        Account a3 = new Account("US TFSA", 2467.13, "USD");

        accList.add(a1);
        accList.add(a2);
        accList.add(a3);

        assertTrue(accList.withdrawFromAccount(a1, 567.8));
        assertTrue(accList.withdrawFromAccount(a2, 546.3));
        assertTrue(accList.withdrawFromAccount(a3, 345.23));

        assertEquals(4005.34-567.8, accList.getAccountByName("CAD RESP").getBalance());
        assertEquals(3400.4-546.3, accList.getAccountByName("CAD TFSA").getBalance());
        assertEquals(2467.13-345.23, accList.getAccountByName("US TFSA").getBalance());
    }

    @Test
    void testDepositToExistingAccount() {
        Account newAccount = new Account("CAD RESP", 4005.34, "CAD");
        accList.add(newAccount);
        assertTrue(accList.depositToAccount(newAccount, 367.8));
        assertEquals(4005.34+367.8, accList.get(0).getBalance());
    }

    @Test
    void testDepositToAccountNotInList() {
        Account newAccount = new Account("CAD RESP", 4005.34, "CAD");
        assertFalse(accList.depositToAccount(newAccount, 367.8));
        assertTrue(accList.isEmpty());
        assertFalse(accList.contains(newAccount));

    }

    @Test
    void testDepositToManyAccounts() {
        Account a1 = new Account("CAD RESP", 4005.34, "CAD");
        Account a2 = new Account("CAD TFSA", 3400.4, "CAD");
        Account a3 = new Account("US TFSA", 2467.13, "USD");

        accList.add(a1);
        accList.add(a2);
        accList.add(a3);

        assertTrue(accList.depositToAccount(a1, 367.8));
        assertTrue(accList.depositToAccount(a2, 287.2));
        assertTrue(accList.depositToAccount(a3, 924.2));

        assertEquals(4005.34+367.8, accList.get(0).getBalance());
        assertEquals(3400.4+287.2, accList.get(1).getBalance());
        assertEquals(2467.13+924.2, accList.get(2).getBalance());
    }

    @Test
    void testRemoveAccountFromEmptyList() {
        Account a1 = new Account("CAD RESP", 4005.34, "CAD");
        assertTrue(accList.isEmpty());
        accList.removeAccount(a1);
        assertFalse(accList.contains(a1));
        assertTrue(accList.isEmpty());
    }

    @Test
    void testRemoveOneAccount() {
        Account a1 = new Account("CAD RESP", 4005.34, "CAD");
        Account a2 = new Account("CAD TFSA", 3400.4, "CAD");
        Account a3 = new Account("US TFSA", 2467.13, "USD");

        accList.add(a1);
        accList.add(a2);
        accList.add(a3);

        accList.removeAccount(a1);

        assertEquals(2, accList.size());
        assertTrue(accList.contains(a2));
        assertTrue(accList.contains(a3));
        assertFalse(accList.contains(a1));
    }

    @Test
    void testRemoveManyAccounts() {
        Account a1 = new Account("CAD RESP", 4005.34, "CAD");
        Account a2 = new Account("CAD TFSA", 3400.4, "CAD");
        Account a3 = new Account("US TFSA", 2467.13, "USD");

        accList.add(a1);
        accList.add(a2);
        accList.add(a3);

        accList.removeAccount(a1);
        accList.removeAccount(a2);

        assertEquals(1, accList.size());
        assertTrue(accList.contains(a3));
        assertFalse(accList.contains(a1));
        assertFalse(accList.contains(a2));

        accList.removeAccount(a3);
        assertTrue(accList.isEmpty());
    }

    @Test
    void testAccountListContainsAccount() {
        Account a1 = new Account("CAD RESP", 4005.34, "CAD");
        accList.add(a1);
        assertTrue(accList.contains(a1));
    }

    @Test
    void testAccountListDoesNotContainAccount() {
        Account a1 = new Account("CAD RESP", 4005.34, "CAD");
        assertFalse(accList.contains(a1));
    }

    @Test
    void testViewAccountsEmptyList() {
        assertTrue(accList.isEmpty());
        List<String> viewingAccounts = accList.viewAccounts();

        assertTrue(viewingAccounts.isEmpty());
    }

    @Test
    void testViewAccounts() {
        Account a1 = new Account("CAD RESP", 4005.34, "CAD");
        Account a2 = new Account("CAD TFSA", 3400.4, "CAD");
        Account a3 = new Account("US TFSA", 2467.13, "USD");

        accList.add(a1);
        accList.add(a2);
        accList.add(a3);

        List<String> expectedViewingAccounts = new LinkedList<>();
        expectedViewingAccounts.add("CAD RESP, 4005.34");
        expectedViewingAccounts.add("CAD TFSA, 3400.4");
        expectedViewingAccounts.add("US TFSA, 2467.13");

        List<String> actualViewingAccounts = accList.viewAccounts();

        assertEquals(expectedViewingAccounts.get(0), actualViewingAccounts.get(0));
        assertEquals(expectedViewingAccounts.get(1), actualViewingAccounts.get(1));
        assertEquals(expectedViewingAccounts.get(2), actualViewingAccounts.get(2));

    }

    @Test
    void testSizeEmptyList() {
        assertTrue(accList.isEmpty());
        assertEquals(0, accList.size());
    }

    @Test
    void testSize() {
        Account a1 = new Account("CAD RESP", 4005.34, "CAD");
        Account a2 = new Account("CAD TFSA", 3400.4, "CAD");
        Account a3 = new Account("US TFSA", 2467.13, "USD");

        accList.add(a1);
        accList.add(a2);
        accList.add(a3);

        assertEquals(3, accList.size());
    }

    @Test
    void testIsEmpty() {
        assertTrue(accList.isEmpty());

        Account a1 = new Account("CAD RESP", 4005.34, "CAD");
        accList.add(a1);
        assertFalse(accList.isEmpty());
    }

    @Test
    void testGetExistingAccountAtIndex() {
        Account a1 = new Account("CAD RESP", 4005.34, "CAD");
        accList.add(a1);
        assertEquals("CAD RESP", accList.get(0).getAccountName());
        assertEquals(4005.34, accList.get(0).getBalance());
        assertEquals("CAD", accList.get(0).getCurrency());
    }


    @Test
    void testGetExistingAccountByName() {
        Account a1 = new Account("CAD RESP", 4005.34, "CAD");
        accList.add(a1);
        assertEquals(accList.get(0), accList.getAccountByName("CAD RESP"));
    }

    @Test
    void testGetNonExistentAccountByName() {
        assertNull(accList.getAccountByName("CAD RESP"));
        Account a1 = new Account("CAD RESP", 4005.34, "CAD");
        Account a2 = new Account("US TFSA", 2467.13, "USD");

        accList.add(a1);
        accList.add(a2);

        assertNull(accList.getAccountByName("CAD TFSA"));
    }

    @Test
    void testAddAccount() {
        Account a = new Account("CAD RESP", 456.12, "USD");
        accList.add(a);
        assertEquals("CAD RESP", accList.get(0).getAccountName());
        assertEquals(456.12, accList.get(0).getBalance());
        assertEquals("USD", accList.get(0).getCurrency());
    }

    @Test
    void testAddMultipleAccounts() {
        Account a1 = new Account("CAD RESP", 456.12, "USD");
        Account a2 = new Account("CAD TFSA", 3400.4, "CAD");
        Account a3 = new Account("US TFSA", 2467.13, "USD");

        accList.add(a1);
        accList.add(a2);
        accList.add(a3);

        assertEquals("CAD RESP", accList.get(0).getAccountName());
        assertEquals(456.12, accList.get(0).getBalance());
        assertEquals("USD", accList.get(0).getCurrency());

        assertEquals("CAD TFSA", accList.get(1).getAccountName());
        assertEquals(3400.4, accList.get(1).getBalance());
        assertEquals("CAD", accList.get(1).getCurrency());

        assertEquals("US TFSA", accList.get(2).getAccountName());
        assertEquals(2467.13, accList.get(2).getBalance());
        assertEquals("USD", accList.get(2).getCurrency());

    }

    @Test
    void testEmptyAccountListToJson() {
        JSONObject jsonObject = accList.toJson();
        assertEquals(0, jsonObject.getJSONArray("Accounts").length());
        assertEquals(1, JSONObject.getNames(jsonObject).length);

    }

    @Test
    void testNonEmptyAccountListToJson() {
        Account a = new Account("CAD RESP", 456.12, "USD");
        accList.add(a);

        JSONObject jsonObject = accList.toJson();
        assertEquals(1, jsonObject.getJSONArray("Accounts").length());
        assertEquals(1, JSONObject.getNames(jsonObject).length);
    }
}
