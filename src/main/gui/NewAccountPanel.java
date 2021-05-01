package gui;

import model.account.Account;

import javax.swing.*;
import java.awt.event.ActionEvent;

// represents the panel that allows users to create a new account
public class NewAccountPanel extends NewItemPanel {
    private Account account;
    private JTextField nameField;
    private JTextField balanceField;
    private JTextField currencyField;
    private AccountListPanel accountsPanel;

    // EFFECTS: creates a new panel with 3 user input fields
    public NewAccountPanel(AccountListPanel listPanel) {
        super(3);
        this.accountsPanel = listPanel;
    }

    public Account getAccount() {
        return account;
    }

    // MODIFIES: this
    // EFFECTS: creates labels and text fields and adds it to the panel
    @Override
    protected void addTextFieldsAndLabels() {
        JLabel nameLabel = new JLabel("Name:");
        JLabel balanceLabel = new JLabel("Balance:");
        JLabel currencyLabel = new JLabel("Currency Type:");

        nameField = new JTextField();
        balanceField = new JTextField();
        currencyField = new JTextField();

        textInputPanel.add(nameLabel);
        textInputPanel.add(nameField);

        textInputPanel.add(balanceLabel);
        textInputPanel.add(balanceField);

        textInputPanel.add(currencyLabel);
        textInputPanel.add(currencyField);
    }


    // MODIFIES: this, bookkeeper
    // EFFECTS: if the "add" button is clicked: creates a new account with the information inputted by the user,
    //              adds the account to the account list panel and sets this panel to be invisible
    //              if an invalid balance is entered, an error dialogue pops up
    //          if the "cancel" button is clicked: sets this panel to be invisible
    //          a clicking sound is played when any button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        clickSound.playSound();
        if (e.getActionCommand().equals(ADD_BUTTON_COMMAND)) {
            try {
                setAccountInfo();
            } catch (NumberFormatException numberFormatException) {
                JOptionPane.showMessageDialog(this, "That is not a valid balance, please try again");
                balanceField.setText("");
                return;
            }
            this.setVisible(false);
            accountsPanel.addAccountToDisplayedList(account);
            nameField.setText("");
            balanceField.setText("");
            currencyField.setText("");

        } else if (e.getActionCommand().equals(CANCEL_BUTTON_COMMAND)) {
            this.setVisible(false);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new account with information inputted by the user,
    //          throws a number format exception if the inputted balance is not a double
    private void setAccountInfo() throws NumberFormatException {
        try {
            String accountName = nameField.getText();
            Double accountBalance = Double.parseDouble(balanceField.getText());
            String currency = currencyField.getText().toUpperCase();
            account = new Account(accountName, accountBalance, currency);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }
}
