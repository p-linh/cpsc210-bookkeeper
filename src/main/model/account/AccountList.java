package model.account;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;
import java.util.List;

// Represents a list of investment accounts
public class AccountList implements Writable {

    private List<Account> activeAccounts;

    // REQUIRES: length of userName is nonzero
    // EFFECTS: creates an empty list of accounts and sets the account list owner's name to userName
    public AccountList() {
        activeAccounts = new LinkedList<>();
    }


    // MODIFIES: this
    // EFFECTS: adds account to account list
    public void add(Account a) {
        activeAccounts.add(a);
    }

    // REQUIRES: amount > 0
    // MODIFIES: Account
    // EFFECTS: if the given account exists, and there is enough balance on the account,
    //          withdraws amount from the account and returns true. Otherwise, returns false.
    public boolean withdrawFromAccount(Account a, double amount) {
        if (contains(a)) {
            return getAccountByName(a.getAccountName()).withdraw(amount);
        } else {
            return false;
        }
    }

    // REQUIRES: amount > 0
    // MODIFIES: Account
    // EFFECTS: if the given account exists, deposits amount into the account and returns true.
    //          Otherwise, returns false.
    public boolean depositToAccount(Account a, double amount) {
        if (contains(a)) {
            getAccountByName(a.getAccountName()).deposit(amount);
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the given account from the list
    public void removeAccount(Account a) {
        activeAccounts.remove(a);
    }

    // EFFECTS: returns true if the list contains the given account
    public boolean contains(Account a) {
        return activeAccounts.contains(a);
    }

    // EFFECTS: returns a list of the names and balances of active accounts
    public List<String> viewAccounts() {
        List<String> viewingAccounts = new LinkedList<>();
        for (Account a : activeAccounts) {
            String accountString = a.getAccountName() + ", " + a.getBalance();
            viewingAccounts.add(accountString);
        }
        return viewingAccounts;
    }

    // EFFECTS: returns the number of all active accounts in the list
    public int size() {
        return activeAccounts.size();
    }

    //EFFECTS: returns true if the accounts list is empty, false otherwise
    public boolean isEmpty() {
        return activeAccounts.isEmpty();
    }

    // EFFECTS: gets the account in the list at position index if it exists
    public Account get(int index) {
        return activeAccounts.get(index);
    }

    // EFFECTS: returns the first occurrence of an account with the given name, if it exists
    public Account getAccountByName(String accountName) {
        Account account = null;
        if (activeAccounts.isEmpty()) {
            return null;
        }
        for (Account a : activeAccounts) {
            if (a.getAccountName().equals(accountName)) {
                return a;
            }
        }

        return account;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Accounts", accountsToJson());
        return json;
    }

    // code referenced from JsonSerializationDemo application from Project Phase 2 on edX
    // EFFECTS: returns accounts in this account list as a JSON array
    private JSONArray accountsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Account a : activeAccounts) {
            jsonArray.put(a.toJson());
        }

        return jsonArray;
    }

}
