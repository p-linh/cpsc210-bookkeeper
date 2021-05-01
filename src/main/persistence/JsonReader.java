package persistence;

import model.Investment;
import model.account.Account;
import model.account.AccountList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// source code: JsonSerializationDemo application from Project Phase 2 on edX
// represents a reader that reads account list from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads account list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AccountList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccountList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses accounts from JSON object and returns it
    private AccountList parseAccountList(JSONObject jsonObject) {
        AccountList al = new AccountList();
        addAccounts(al, jsonObject);
        return al;
    }

    // MODIFIES: al
    // EFFECTS: parses accounts from JSON object and adds them to account list
    private void addAccounts(AccountList al, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Accounts");
        for (Object json : jsonArray) {
            JSONObject nextAccount = (JSONObject) json;
            addAccount(al, nextAccount);
        }
    }

    // MODIFIES: al
    // EFFECTS: parses account from JSON object and adds it to account list
    private void addAccount(AccountList al, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String currency = jsonObject.getString("currency");
        double balance = jsonObject.getDouble("balance");
        JSONArray jsonArray = jsonObject.getJSONArray("investments");
        Account account = new Account(name, balance, currency);

        for (Object json : jsonArray) {
            JSONObject nextInvestment = (JSONObject) json;
            addInvestment(account, nextInvestment);
        }

        al.add(account);
    }

    // MODIFIES: a
    // EFFECTS: parses investment from JSON object and adds it to the given account
    private void addInvestment(Account a, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String symbol = jsonObject.getString("symbol");

        Investment investment = new Investment(symbol, name);
        a.addInvestment(investment);
    }
}
