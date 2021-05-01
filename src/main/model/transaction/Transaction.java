package model.transaction;

import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;

// represents a transaction related to a stock
public class Transaction  {
    private LocalDate date;
    private double price;
    private Type transactionType;
    private int quantity;

    public enum Type {
        PURCHASE, SALE;
    }

    // REQUIRES: price > 0, quantity > 0
    // EFFECTS: creates a transaction occurring at date with an individual price, a quantity and a transaction type
    public Transaction(LocalDate date, double price, int quantity, Type transactionType) {
        this.date = date;
        this.price = price;
        this.quantity = quantity;
        this.transactionType = transactionType;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public Type getTransactionType() {
        return transactionType;
    }

    public int getQuantity() {
        return quantity;
    }

    // EFFECTS: returns the total purchase or sale amount of the transaction
    public double totalTransactionAmount() {
        return quantity * price;
    }

//    @Override
//    public JSONObject toJson() {
//        JSONObject json = new JSONObject();
//        json.put("date", date);
//        json.put("price", price);
//        json.put("type", transactionType);
//        return json;
//    }
}
