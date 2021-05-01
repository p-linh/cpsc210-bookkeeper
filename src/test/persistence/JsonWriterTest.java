package persistence;

import model.Investment;
import model.account.Account;
import model.account.AccountList;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// source code: JsonSerializationDemo application from Project Phase 2 on edX
public class JsonWriterTest extends JsonTest {
    private AccountList al;

    @BeforeEach
    void runBefore() {
        al = new AccountList();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterEmptyAccountList() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAccountList.json");
            writer.open();
            writer.write(al);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAccountList.json");
            al = reader.read();
            assertEquals(0, al.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralAccountList() {
        try {
            setUpAccountList();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAccountList.json");
            writer.open();
            writer.write(al);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAccountList.json");
            al = reader.read();
            assertEquals(3, al.size());
            checkAccounts();

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    private void checkAccounts() {
        List<Investment> list1 = new ArrayList<>();
        list1.add(new Investment("ABC", "Company A"));
        List<Investment> list2 = new ArrayList<>();
        list2.add(new Investment("DEF", "Company B"));
        List<Investment> list3 = new ArrayList<>();

        checkAccount("CAD RESP", 389.45, "CAD", al.get(0));
        checkAccount("CAD TFSA", 1203.68, "CAD", al.get(1));
        checkAccount("US TFSA", 7135, "USD", al.get(2));
        checkInvestments(list1, al.get(0));
        checkInvestments(list2, al.get(1));
        checkInvestments(list3, al.get(2));
    }

    private void setUpAccountList() {
        Account a1 = new Account("CAD RESP", 389.45, "CAD");
        Account a2 = new Account("CAD TFSA", 1203.68, "CAD");
        Account a3 = new Account("US TFSA", 7135, "USD");

        Investment i1 = new Investment("ABC", "Company A");
        Investment i2 = new Investment("DEF", "Company B");
        a1.addInvestment(i1);
        a2.addInvestment(i2);

        al.add(a1);
        al.add(a2);
        al.add(a3);
    }
}
