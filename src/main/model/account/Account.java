package model.account;

import model.Investment;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// Represents an investment account, with a name, a type of currency, and a balance
public class Account implements Writable {
    private String accountName;
    private String currency;
    private double balance;
    private List<Investment> investments;

    // REQUIRES: accountName must have a length > 0, currencyCode must be a 3 letter code, fully upper case,
    //           representing a country's currency code, initialBalance >= 0
    // EFFECTS: sets the account name to accountName, the account balance to initialBalance, and the
    //          account currency type to currencyCode
    public Account(String accountName, double initialBalance, String currencyCode) {
        this.accountName = accountName;
        balance = initialBalance;
        currency = currencyCode;
        investments = new ArrayList<>();
    }

    public double getBalance() {
        return this.balance;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public String getCurrency() {
        return this.currency;
    }


    // EFFECTS: returns the number of investments the account current holds
    public int getNumInvestments() {
        return investments.size();
    }

    // MODIFIES: this
    // EFFECTS: if account does doesn't already contain the investment, adds the investment to investments
    //          if (balance + net profit) > 0, then the investment is added to the account and the
    //          account balance is adjusted accordingly, and returns true
    //          otherwise returns false
    public boolean addInvestment(Investment i) {
        if (!investments.contains(i)) {
            if (balance + i.netProfit() > 0) {
                investments.add(i);
                balance += i.netProfit();
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns the investment at the index
    public Investment getInvestmentOf(int index) {
        return investments.get(index);
    }

    // EFFECTS: returns the investment with the given symbol if it exists within the list, otherwise, returns null
    public Investment getInvestmentWithSymbol(String symbol) {
        Investment investment = null;
        if (investments.isEmpty()) {
            return null;
        }
        for (Investment i : investments) {
            if (i.getSymbol().equals(symbol)) {
                investment = i;
            }
        }
        return investment;
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: if there is sufficient balance on the account,
    //          - amount is withdrawn from the account's balance
    //          - returns true
    //          otherwise, returns false
    public boolean withdraw(double amount) {
        if (getBalance() > amount) {
            balance = balance - amount;
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: deposits the given amount into the account balance
    public void deposit(double amount) {
        balance = balance + amount;
    }


    // MODIFIES: this
    // EFFECTS: removes the given investment from investments
    public void removeInvestment(Investment i) {
        investments.remove(i);
    }

    // EFFECTS: stores account information in a json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", accountName);
        json.put("currency", currency);
        json.put("balance", balance);
        json.put("investments", investmentsToJson());
        return json;
    }

    // EFFECTS: puts investments into a json array and returns it
    private JSONArray investmentsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Investment i: investments) {
            jsonArray.put(i.toJson());
        }
        return jsonArray;
    }
}
