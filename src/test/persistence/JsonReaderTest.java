package persistence;

import model.Investment;
import model.account.AccountList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// source code: JsonSerializationDemo application from Project Phase 2 on edX
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            AccountList al = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAccountList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAccountList.json");
        try {
            AccountList al = reader.read();
            assertEquals(0, al.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAccountList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAccountList.json");
        try {
            AccountList al = reader.read();
            assertEquals(3, al.size());
            checkAccounts(al);

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    private void checkAccounts(AccountList al) {
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
}
