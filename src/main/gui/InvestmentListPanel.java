package gui;

import model.Investment;
import model.account.Account;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;

// represents a list panel displaying investments
public class InvestmentListPanel extends ListPanel {
    private Account account;
    private JLabel title;
    private NewInvestmentPanel addInvestmentPanel;
    public ViewInvestmentDetailsPanel viewPanel;
    private JButton viewButton;

    // EFFECTS: creates an empty investment list panel
    public InvestmentListPanel(BookkeeperGUI bookkeeper) {
        super(bookkeeper);
        setTitle();
        addInvestmentPanel = new NewInvestmentPanel(this);
        viewPanel = new ViewInvestmentDetailsPanel(bookkeeper);
        bookkeeper.addPanel(addInvestmentPanel);
        bookkeeper.addPanel(viewPanel);
    }

    // MODIFIES: this
    // EFFECTS: sets the title of the panel to "Investments"
    @Override
    protected void setTitle() {

        title = new JLabel("Investments");
        add(title, BorderLayout.PAGE_START);
    }

    // MODIFIES: this
    // EFFECTS: sets the account from which to display the investment list
    public void setAccount(Account account) {
        this.account = account;
        updateList();
    }

    // MODIFIES: this
    // EFFECTS: updates the displayed list to investments belonging to the current account, also updates the title
    //          of the panel to the current account's name
    private void updateList() {
        String label;
        try {
            String name = account.getAccountName();
            label = "Investments in " + name;
            clearDisplayedList();
            for (int i = 0; i < account.getNumInvestments(); i++) {
                String symbol = account.getInvestmentOf(i).getSymbol();
                String investmentName = account.getInvestmentOf(i).getName();
                addInvestmentToDisplayedList(symbol, investmentName);
            }
        } catch (NullPointerException e) {
            label = "Investments";
        }
        title.setText(label);
    }

    // MODIFIES: this
    // EFFECTS: adds the investment's name and symbol to the displayed list
    private void addInvestmentToDisplayedList(String symbol, String investmentName) {
        listModel.addElement(symbol + " - " + investmentName);
    }

    // MODIFIES: this
    // EFFECTS: clears the displayed list
    public void clearDisplayedList() {
        listModel.removeAllElements();
    }

    // MODIFIES: this
    // EFFECTS: if possible, adds the investment to account and to the displayed list, otherwise, shows an error message
    public void addInvestmentToPanel(Investment i) {
        if (account.addInvestment(i)) {
            super.setSelection();
            addInvestmentToDisplayedList(i.getSymbol(), i.getName());
        } else {
            JOptionPane.showMessageDialog(this,
                    "Aan investment with the given symbol already exists.",
                    "Could not add investment", JOptionPane.ERROR_MESSAGE);
        }
    }


    // MODIFIES: this
    // EFFECTS: removes an investment from the displayed list and from the account
    @Override
    protected void removeItemFromList() {
        int index = displayedList.getSelectedIndex();
        super.removeItemFromList();
        Investment i = account.getInvestmentOf(index);
        account.removeInvestment(i);
    }

    // MODIFIES: this
    // EFFECTS: sets the names of the add and remove buttons
    @Override
    protected void setUpButtons() {
        super.setUpButtons();
        addButton.setText("New Investment");
        removeButton.setText("Remove Investment");

        viewButton = new JButton("View Details");
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(viewButton);

        viewButton.setActionCommand("view");
        viewButton.addActionListener(this);
        viewButton.setEnabled(false);
    }


    // MODIFIES: this
    // EFFECTS: if the add button is clicked, shows the "new investment" panel for new investment creation
    //          if the remove button is clicked, removes the investment from the account and displayed list
    @Override
    public void actionPerformed(ActionEvent e) {
        clickSound.playSound();
        if (e.getActionCommand().equals(ADD_BUTTON_COMMAND)) {
            this.setVisible(false);
            addInvestmentPanel.setVisible(true);
            System.out.println("Adding new investment!");
        } else if (e.getActionCommand().equals(REMOVE_BUTTON_COMMAND)) {
            removeItemFromList();
            System.out.println("Removing investment!");;
        } else if (e.getActionCommand().equals("view")) {
            viewInvestmentDetails();
        }

    }

    private void viewInvestmentDetails() {
        int selected = displayedList.getSelectedIndex();
        Investment view = account.getInvestmentOf(selected);
        bookkeeper.getAccountListPanel().setVisible(false);

        viewPanel.setInvestment(view);
        viewPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: disables the remove button if no investments are selected
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (displayedList.getSelectedIndex() == -1) {
                removeButton.setEnabled(false);
                viewPanel.clearInfo();
                viewButton.setEnabled(false);
            } else {
                removeButton.setEnabled(true);
                viewPanel.setVisible(false);
                viewButton.setEnabled(true);
            }
        }
    }
}
