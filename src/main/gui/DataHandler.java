package gui;

import model.Investment;
import model.account.AccountList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// represents a file saver and loader
public class DataHandler {
    private static final String SAVE_LOCATION = "./data/bookkeeper.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private BookkeeperGUI bookkeeper;

    // EFFECTS: creates a new Json Reader and Writer for SAVE_LOCATION
    public DataHandler(BookkeeperGUI bookkeeper) {
        this.bookkeeper = bookkeeper;
        jsonReader = new JsonReader(SAVE_LOCATION);
        jsonWriter = new JsonWriter(SAVE_LOCATION);
    }

    // source code: JsonSerializationDemo application from Project Phase 2 on edX
    // EFFECTS: saves the account list to file, displays an error message if the file cannot be found
    public void saveAccounts() {
        try {
            AccountList accounts = bookkeeper.getAccountListPanel().getAccounts();
            jsonWriter.open();
            jsonWriter.write(accounts);
            jsonWriter.close();
            System.out.println("Saved!");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(bookkeeper.getAccountListPanel(), "File could not be found.",
                    "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // source code: JsonSerializationDemo application from Project Phase 2 on edX
    // MODIFIES: this, bookkeeper
    // EFFECTS: loads account list from file, overwriting the current account list,
    //          displays an error message if the file cannot be found
    public void loadAccounts() {
        try {
            AccountList accounts = jsonReader.read();
            newFile();
            AccountListPanel listPanel = bookkeeper.getAccountListPanel();
            listPanel.addAccountsToDisplayedList(accounts);
            System.out.println("Loaded!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(bookkeeper.getAccountListPanel(), "File could not be found.",
                    "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: bookkeeper
    // EFFECTS: creates a new, blank account list
    public void newFile() {
        bookkeeper.getAccountListPanel().addAccountsToDisplayedList(new AccountList());
    }
}
