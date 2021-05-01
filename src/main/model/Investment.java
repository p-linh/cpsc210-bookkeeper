package model;

import model.transaction.Transaction;
import model.transaction.TransactionList;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

// represents an investment, which belongs to an account
public class Investment implements Writable {
    private String symbol;
    private String name;
    private TransactionList transactions;
    private int currentShares;

    // REQUIRES: symbol and string have a nonzero length
    //           symbol must be a unique, fully upper-case sequence of letters representing a specific company's stock
    // EFFECTS: creates an investment with a symbol and a name, an empty transaction list, and zero current shares
    public Investment(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
        transactions = new TransactionList();
        currentShares = 0;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public TransactionList getTransactions() {
        return transactions;
    }

    public int getCurrentShares() {
        return currentShares;
    }


    // REQUIRES: price > 0, quantity > 0
    // MODIFIES: this
    // EFFECTS: purchases the given quantity of shares (with an individual price) of this investment, on the given date
    public void purchaseShares(LocalDate purchaseDate, double price, int quantity) {
        currentShares += quantity;
        Transaction newPurchase = new Transaction(purchaseDate, price, quantity, Transaction.Type.PURCHASE);
        transactions.addTransaction(newPurchase);
    }

    // REQUIRES: price > 0, currentShares >= quantity
    // MODIFIES: this
    // EFFECTS: if currently holding enough shares,
    //          sells the given quantity of shares (with an individual price) of this investment, on the given date
    //          otherwise, does nothing
    public void sellShares(LocalDate sellDate, double price, int quantity) {
        if (currentShares >= quantity) {
            currentShares -= quantity;
            Transaction newSale = new Transaction(sellDate, price, quantity, Transaction.Type.SALE);
            transactions.addTransaction(newSale);
        }
    }

    // EFFECTS: returns the net profit gained from this investment
    public double netProfit() {
        double sales = transactions.totalAmountOfTransactionsOf(Transaction.Type.SALE);
        double purchases = transactions.totalAmountOfTransactionsOf(Transaction.Type.PURCHASE);

        return sales - purchases;
    }


    // EFFECTS: returns true if the given object is an investment with the same symbol (ignoring case) as
    //          this investment
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Investment that = (Investment) o;
        return symbol.equalsIgnoreCase(that.symbol);
    }

    // MODIFIES: this
    // EFFECTS: generates a hashcode for the investment based on its symbol in upper case
    @Override
    public int hashCode() {
        return Objects.hash(symbol.toUpperCase());
    }


    // EFFECTS: stores investment information in a json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("symbol", symbol);
        return json;
    }
}
