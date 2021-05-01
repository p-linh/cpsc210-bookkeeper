package model.transaction;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class TransactionList {
    private List<Transaction> transactions;

    // EFFECTS: creates an empty transaction list
    public TransactionList() {
        transactions = new LinkedList<>();
    }


    // EFFECTS: returns the size of the transaction list
    public int size() {
        return transactions.size();
    }

    /**
     * MODIFIES: this
     * <p> EFFECTS: adds transaction to transaction list </p>
     * @param t transaction to be added to the list
     */
    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    // EFFECTS: gets the transaction in the list at index
    public Transaction get(int index) {
        return transactions.get(index);
    }

    // EFFECTS: returns a list of transactions occurring within dates begin and end (including begin and end)
    //          the list will be sorted starting from oldest to newest transactions
    public List<Transaction> getTransactionsInDateRange(LocalDate begin, LocalDate end) {
        TransactionList transactionsInRange = new TransactionList();
        for (Transaction t: transactions) {
            Boolean between = t.getDate().isAfter(begin) && t.getDate().isBefore(end);

            if (t.getDate().equals(begin) | between | t.getDate().equals(end)) {
                transactionsInRange.addTransaction(t);
            }
        }

        return sortByDate(transactionsInRange);
    }

    // code referenced from: https://howtodoinjava.com/java/sort/collections-sort/
    // EFFECTS: sorts transactions by date, starting from oldest to newest, and returns the sorted list
    public static List<Transaction> sortByDate(TransactionList tl) {
        List<Transaction> sorted = tl.transactions;

        Comparator<Transaction> compareByDate = new Comparator<Transaction>() {
            @Override
            public int compare(Transaction t1, Transaction t2) {
                return t1.getDate().compareTo(t2.getDate());
            }
        };

        Collections.sort(sorted, compareByDate);
        return sorted;
    }

    // EFFECTS: returns a list of transactions occurring on date
    public List<Transaction> getTransactionsOnDate(LocalDate date) {
        List<Transaction> onDate = new LinkedList<>();
        for (Transaction t: transactions) {
            if (t.getDate().equals(date)) {
                onDate.add(t);
            }
        }

        return onDate;
    }

    // EFFECTS: returns a list of transactions of the specified type
    public List<Transaction> getTransactionsOfType(Transaction.Type type) {
        List<Transaction> ofType = new LinkedList<>();
        for (Transaction t: transactions) {
            if (t.getTransactionType() == type) {
                ofType.add(t);
            }
        }

        return ofType;
    }

    // EFFECTS: returns the total amount of the transactions of the specified type
    public double totalAmountOfTransactionsOf(Transaction.Type type) {
        double amount = 0;
        for (Transaction t: transactions) {
            if (t.getTransactionType() == type) {
                amount += t.totalTransactionAmount();
            }
        }

        return amount;
    }


    // EFFECTS: returns a list displaying info of all transactions within transaction list
//    public List<String> viewAll() {
//        return null;
//    }

    // EFFECTS: returns true if transaction list is empty, false otherwise
    public Boolean isEmpty() {
        return transactions.isEmpty();
    }


}
