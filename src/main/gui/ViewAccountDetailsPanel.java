package gui;

import model.account.Account;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ViewAccountDetailsPanel extends ViewDetailsPanel {
    private static final String WITHDRAW_COMMAND = "withdraw";
    private static final String DEPOSIT_COMMAND = "deposit";
    private Account account;
    private JLabel balance;
    private JLabel name;
    private JLabel currency;
    public InvestmentListPanel investmentsPanel;

    // EFFECTS: creates a new details panel with an associated account and 3 fields for the account details
    public ViewAccountDetailsPanel(BookkeeperGUI bookkeeper) {
        super(bookkeeper, 3);
        investmentsPanel = new InvestmentListPanel(bookkeeper);
        bookkeeper.addPanel(investmentsPanel);
    }

    // MODIFIES: this
    // EFFECTS: sets the account to be viewed
    public void setAccount(Account account) {
        this.account = account;
        investmentsPanel.setAccount(account);
        updateInfo();
    }

    // MODIFIES: this
    // EFFECTS: updates the balance label
    private void updateBalance() {
        balance.setText(Double.toString(account.getBalance()));
    }

    // MODIFIES: this
    // EFFECTS: clears labels holding account info
    @Override
    public void clearInfo() {
        name.setText("");
        balance.setText("");
        currency.setText("");
    }

    // MODIFIES: this
    // EFFECTS: if the account isn't null, updates labels displaying account info
    @Override
    public void updateInfo() {
        if (account != null) {
            name.setText(account.getAccountName());
            updateBalance();
            currency.setText(account.getCurrency());
        }
    }

    // MODIFIES: this
    // EFFECTS: sets this panel and investment panel to the given visibility
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        investmentsPanel.setVisible(visible);
    }

    // MODIFIES: this
    // EFFECTS: creates labels for account information and adds it to the details panel
    @Override
    protected void setUpLabels() {

        JLabel nameLabel = new JLabel("Name:");
        detailsPanel.add(nameLabel);
        name = new JLabel();
        detailsPanel.add(name);

        JLabel balanceLabel = new JLabel("Balance:");
        detailsPanel.add(balanceLabel);
        balance = new JLabel();
        detailsPanel.add(balance);

        JLabel currencyLabel = new JLabel("Currency Type:");
        detailsPanel.add(currencyLabel);
        currency = new JLabel();
        detailsPanel.add(currency);

    }

    // MODIFIES: this
    // EFFECTS: creates "withdraw" and "deposit" buttons and adds it to the panel
    @Override
    protected void setUpButtons() {
        JButton withdrawButton = new JButton("Withdraw Money");
        JButton depositButton = new JButton("Deposit Money");

        withdrawButton.setActionCommand(WITHDRAW_COMMAND);
        withdrawButton.addActionListener(this);
        depositButton.setActionCommand(DEPOSIT_COMMAND);
        depositButton.addActionListener(this);

        buttonPanel.add(withdrawButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(depositButton);

    }

    // EFFECTS: if "withdraw" button is clicked, performs a withdrawal.
    //          if "deposit" button is clicked, performs a deposit.
    //          if an invalid amount is inputted, an error dialogue box is shown.
    //          any button click plays a sound
    @Override
    public void actionPerformed(ActionEvent e) {
        clickSound.playSound();
        if (e.getActionCommand().equals(WITHDRAW_COMMAND)) {
            try {
                doWithdrawal();
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(this, "That is not a valid amount.");
                return;
            } catch (NullPointerException exception) {
                return;
            }
        } else if (e.getActionCommand().equals(DEPOSIT_COMMAND)) {
            try {
                doDeposit();
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(this, "That is not a valid amount.");
                return;
            }  catch (NullPointerException exception) {
                return;
            }
        }

    }

    // MODIFIES: this
    // EFFECTS: performs a withdrawal on account, throws NumberFormatException if inputted amount is not valid,
    //          if account has insufficient balance, a dialogue pops up
    private void doWithdrawal() throws NumberFormatException, NullPointerException {
        String input = JOptionPane.showInputDialog(this, "Please enter a withdrawal amount");
        try {
            Double amount = Double.parseDouble(input);
            if (account.withdraw(amount)) {
                updateBalance();
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient balance, could not withdraw.",
                        "Insufficient Balance Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException numberFormatException) {
            throw new NumberFormatException();
        } catch (NullPointerException nullPointerException) {
            throw new NullPointerException();
        }
    }

    // MODIFIES: this
    // EFFECTS: performs a deposit on account, throws NumberFormatException if inputted amount is not valid
    private void doDeposit() throws NumberFormatException {
        String input = JOptionPane.showInputDialog(this, "Please enter a deposit amount");
        try {
            Double amount = Double.parseDouble(input);
            account.deposit(amount);
            updateBalance();
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }
}
