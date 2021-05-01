package gui;

import model.account.Account;
import model.account.AccountList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;


// represents the account selection panel in the main window
public class AccountListPanel extends ListPanel {
    private AccountList accounts;
    private JButton viewDetailsButton;
    private NewAccountPanel newAccountPanel;
    public ViewAccountDetailsPanel viewPanel;

    // EFFECTS: creates an empty account list panel
    public AccountListPanel(BookkeeperGUI bookkeeper) {
        super(bookkeeper);
        newAccountPanel = new NewAccountPanel(this);
        viewPanel = new ViewAccountDetailsPanel(bookkeeper);
        bookkeeper.addPanel(newAccountPanel);
        bookkeeper.addPanel(viewPanel);
        accounts = new AccountList();
    }

    // MODIFIES: this
    // EFFECTS: sets the title of the panel to "Accounts"
    @Override
    protected void setTitle() {
        JLabel title = new JLabel("Accounts");
        add(title, BorderLayout.PAGE_START);
    }

    public AccountList getAccounts() {
        return accounts;
    }


    // MODIFIES: this
    // EFFECTS: creates buttons for managing the account list
    @Override
    protected void setUpButtons() {
        super.setUpButtons();
        addButton.setText("Add Account");
        removeButton.setText("Remove Account");

        viewDetailsButton = new JButton("View Details");
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(viewDetailsButton);

        viewDetailsButton.setActionCommand("view");
        viewDetailsButton.addActionListener(this);
        viewDetailsButton.setEnabled(false);
    }



    // MODIFIES: this, bookkeeper
    // EFFECTS: if the "add" button is clicked:
    //              - this panel gets replaced with a new account panel
    //          if the "remove" button is clicked:
    //              - the account selected is removed from the accounts list
    @Override
    public void actionPerformed(ActionEvent e) {
        clickSound.playSound();
        if (e.getActionCommand().equals(REMOVE_BUTTON_COMMAND)) {
            removeItemFromList();

        } else if (e.getActionCommand().equals(ADD_BUTTON_COMMAND)) {
            newAccountPanel.setVisible(true);
            viewPanel.setVisible(false);
            viewPanel.investmentsPanel.viewPanel.setVisible(false);

        } else if (e.getActionCommand().equals("view")) {
            viewAccountDetails();
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the selected account from the account list
    @Override
    protected void removeItemFromList() {
        int index = displayedList.getSelectedIndex();
        super.removeItemFromList();
        Account account = accounts.get(index);
        accounts.removeAccount(account);
    }

    // MODIFIES: this
    // EFFECTS: adds the given account to the end of the account list
    public void addAccountToDisplayedList(Account a) {
        accounts.add(a);
        setSelection();
        listModel.addElement(a.getAccountName());
    }

    // MODIFIES: this
    // EFFECTS: adds the given account list to the end of the current account list
    public void addAccountsToDisplayedList(AccountList accountsToAdd) {
        if (!accountsToAdd.isEmpty()) {
            for (int i = 0; i < accountsToAdd.size(); i++) {
                listModel.addElement(accountsToAdd.get(i).getAccountName());
            }
        } else {
            listModel.clear();
        }
        accounts = accountsToAdd;
    }

    // MODIFIES: bookkeeper
    // EFFECTS: displays the view account panel
    private void viewAccountDetails() {
        int index = displayedList.getSelectedIndex();
        Account view = accounts.get(index);

        viewPanel.setAccount(view);
        viewPanel.setVisible(true);
    }


    // MODIFIES: this
    // EFFECTS: disables the remove and view details button if no accounts in the panel are selected,
    //          enables them if an account is selected
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (displayedList.getSelectedIndex() == -1) {
                removeButton.setEnabled(false);
                viewDetailsButton.setEnabled(false);
                viewPanel.clearInfo();
                viewPanel.investmentsPanel.clearDisplayedList();
            } else {
                viewPanel.setVisible(false);
                removeButton.setEnabled(true);
                viewDetailsButton.setEnabled(true);
            }
        }
    }
}
