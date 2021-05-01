package persistence;

import model.Investment;
import model.account.Account;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// source code: JsonSerializationDemo application from Project Phase 2 on edX
public class JsonTest {
    protected void checkAccount(String accName, double balance, String currency, Account a) {
        assertEquals(accName, a.getAccountName());
        assertEquals(balance, a.getBalance());
        assertEquals(currency, a.getCurrency());
    }

    protected void checkInvestments(List<Investment> investments, Account a) {
        assertEquals(investments.size(), a.getNumInvestments());

        if (!investments.isEmpty()) {
            for (int i = 0; i < a.getNumInvestments(); i++) {
                assertEquals(investments.get(i), a.getInvestmentOf(i));
            }
        }

    }
}
