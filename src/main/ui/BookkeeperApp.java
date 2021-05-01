package ui;

import model.account.Account;
import model.account.AccountList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Bookkeeper Application
public class BookkeeperApp {
    private AccountList accounts;
    private String userName;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String SAVE_LOCATION = "./data/bookkeeper.json";

    // runs the Bookkeeper application
    public BookkeeperApp() throws IOException {
        runBookkeeper();
    }

    // code copied from TellerApp on eDX
    // MODIFIES: this
    // EFFECTS: processes user input
    private void runBookkeeper() throws IOException {
        boolean keepGoing = true;
        String command = null;

        init();
        loadDataMenu();

        while (keepGoing) {
            displayMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("Exited");
    }

    // code referenced from TellerApp on eDX
    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("1")) {
            addAccount();
        } else if (command.equals("2")) {
            withdrawMoney();
        } else if (command.equals("3")) {
            depositMoney();
        } else if (command.equals("4")) {
            viewAccounts();
        } else if (command.equals("5")) {
            removeAccount();
        } else if (command.equals("6")) {
            saveAccounts();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates an empty accounts list and initializes the user's name
    private void init() {
        input = new Scanner(System.in);
        String name = null;
        System.out.println("\nHello, please enter your name:");

        name = input.nextLine();
        userName = name;
        accounts = new AccountList();

    }

    // code referenced from TellerApp on eDX
    // EFFECTS: displays a menu of options to user
    private void displayMenu() {
        System.out.println("\nHi " + userName + " what would you like to do today?");
        System.out.println("\nPlease choose a number from one of the follow options:");
        System.out.println("\t1 - add a new account");
        System.out.println("\t2 - withdraw money from an existing account");
        System.out.println("\t3 - deposit money to an existing account");
        System.out.println("\t4 - view all active accounts");
        System.out.println("\t5 - remove an existing account");
        System.out.println("\t6 - save accounts");
        System.out.println("\tq - quit");
    }

    // EFFECTS: displays menu to create a new file or to load an existing one
    private void loadDataMenu() throws IOException {
        System.out.println("Load an existing file? \n1 - Yes \n2 - No");

        String command = input.nextLine();

        Boolean invalidCommand = true;

        while (invalidCommand) {
            if (command.equals("1")) {
                jsonReader = new JsonReader(SAVE_LOCATION);
                try {
                    loadAccounts();
                    invalidCommand = false;
                } catch (IOException e) {
                    System.out.println("There are no files associated with the name you have entered.");
                    throw new IOException();
                }
            } else if (command.equals("2")) {
                invalidCommand = false;
            } else {
                System.out.println("Selection not valid. Please try again.");
                invalidCommand = true;
            }
        }

        jsonWriter = new JsonWriter(SAVE_LOCATION);

    }


    // MODIFIES: this
    // EFFECTS: adds a new account to account list based on user input
    private void addAccount() {

        System.out.println("Enter the name of the account:");
        String accountName = input.nextLine();

        System.out.println("Enter the starting balance on the account: ");

        double initialBalance = checkValidMoneyInput();

        System.out.println("Enter the currency type of the account:");
        String currencyCode = input.nextLine();

        Account newAccount = new Account(accountName, initialBalance, currencyCode);
        accounts.add(newAccount);

        System.out.println("Successfully added account. Here are your current accounts:");
        accountSelectionMenu();
    }

    // MODIFIES: this
    // EFFECTS: returns a valid money amount entered by the user
    private double checkValidMoneyInput() {
        double userInputAmount = Double.parseDouble(input.nextLine());
        boolean invalidBalance = true;

        while (invalidBalance) {
            if (userInputAmount >= 0) {
                invalidBalance = false;
            } else {
                System.out.println("The inputted amount cannot be negative, please try again.");
            }
        }
        return userInputAmount;
    }

    // MODIFIES: this, Account
    // EFFECTS: conducts a withdrawal transaction
    private void withdrawMoney() {
        System.out.println("Please select which account to withdraw from:");

        accountSelectionMenu();
        Account selectedAccount = processAccountSelection();

        System.out.println("How much would you like to withdraw?");
        double amount = checkValidMoneyInput();

        boolean canWithdraw = accounts.withdrawFromAccount(selectedAccount, amount);
        if (canWithdraw) {
            double newBalance = accounts.getAccountByName(selectedAccount.getAccountName()).getBalance();
            System.out.println("Success! Your new balance is: " + newBalance);
        } else {
            System.out.println("Could not withdraw, insufficient balance.");
        }
    }

    // EFFECTS: displays a menu listing the current active accounts
    private void accountSelectionMenu() {
        int numberedList = 1;

        if (accounts.isEmpty()) {
            System.out.println("There are no available accounts.");
        } else {
            for (String accountInfo : accounts.viewAccounts()) {
                System.out.println(numberedList++ + ")" + accountInfo);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input from account selection menu and returns the selected account
    private Account processAccountSelection() {
        boolean invalidSelection = true;
        int userSelection = 0;

        while (invalidSelection) {
            userSelection = Integer.parseInt(input.nextLine());
            if (userSelection > accounts.size() | userSelection < 0) {
                System.out.println("That is not a valid selection, please try again.");
            } else {
                invalidSelection = false;
            }
        }
        return accounts.get(userSelection - 1);
    }

    // MODIFIES: Account
    // EFFECTS: conducts deposit transaction
    private void depositMoney() {
        System.out.println("Please select which account to deposit into:");
        accountSelectionMenu();
        Account selectedAccount = processAccountSelection();

        System.out.println("How much would you like to deposit?");
        double amount = checkValidMoneyInput();

        boolean canDeposit = accounts.depositToAccount(selectedAccount, amount);
        if (canDeposit) {
            double newBalance = accounts.getAccountByName(selectedAccount.getAccountName()).getBalance();
            System.out.println("Success! Your new balance is: " + newBalance);
        } else {
            System.out.println("Could not deposit.");
        }
    }

    // EFFECTS: displays a list of currently active accounts
    private void viewAccounts() {
        accountSelectionMenu();
    }

    // MODIFIES: this
    // EFFECTS: removes the selected account from the accounts list
    private void removeAccount() {
        System.out.println("Please select which account you would like to remove:");
        accountSelectionMenu();
        Account selectedAccount = processAccountSelection();

        accounts.removeAccount(selectedAccount);
        System.out.println("Success! Here are your remaining accounts:");
        accountSelectionMenu();
    }

    // source code: JsonSerializationDemo application from Project Phase 2 on edX
    // EFFECTS: saves the account list to file
    private void saveAccounts() {
        try {
            jsonWriter.open();
            jsonWriter.write(accounts);
            jsonWriter.close();
            System.out.println("Saved!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file");
        }
    }

    // source code: JsonSerializationDemo application from Project Phase 2 on edX
    // MODIFIES: this
    // EFFECTS: loads account list from file
    private void loadAccounts() throws IOException {
        try {
            accounts = jsonReader.read();
            System.out.println("Loaded!");
        } catch (IOException e) {
            throw new IOException();
        }
    }
}
