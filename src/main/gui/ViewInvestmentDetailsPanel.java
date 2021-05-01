package gui;

import model.Investment;

import javax.swing.*;
import java.awt.event.ActionEvent;

// represents a panel displaying information from an investment
public class ViewInvestmentDetailsPanel extends ViewDetailsPanel {
    private Investment investment;
    JLabel investmentName;
    JLabel investmentSymbol;
    JLabel shares;

    // EFFECTS: creates a new panel to display investment information
    public ViewInvestmentDetailsPanel(BookkeeperGUI bookkeeper) {
        super(bookkeeper, 3);
    }

    // MODIFIES: this
    // EFFECTS: sets the current investment to the given one, and updates labels
    public void setInvestment(Investment investment) {
        this.investment = investment;
        updateInfo();
    }

    // MODIFIES: this
    // EFFECTS: creates labels for the name, symbol and number of shares of the investment
    @Override
    protected void setUpLabels() {
        JLabel nameLabel = new JLabel("Name:");
        JLabel symbolLabel = new JLabel("Symbol:");
        JLabel sharesLabel = new JLabel("Number of shares:");

        investmentName = new JLabel();
        investmentSymbol = new JLabel();
        shares = new JLabel();

        detailsPanel.add(nameLabel);
        detailsPanel.add(investmentName);
        detailsPanel.add(symbolLabel);
        detailsPanel.add(investmentSymbol);
        detailsPanel.add(sharesLabel);
        detailsPanel.add(shares);
    }


    // MODIFIES: this
    // EFFECTS: clears investment info from labels
    @Override
    public void clearInfo() {
        investmentName.setText("");
        investmentSymbol.setText("");
        shares.setText("");
    }

    // MODIFIES: this
    // EFFECTS: updates labels to match the current investment information
    @Override
    protected void updateInfo() {
        if (investment != null) {
            investmentName.setText(investment.getName());
            investmentSymbol.setText(investment.getSymbol());
            shares.setText(Integer.toString(investment.getCurrentShares()));
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a "back" button
    @Override
    protected void setUpButtons() {
        JButton backButton = new JButton("Back");
        backButton.setActionCommand("back");
        backButton.addActionListener(this);

        buttonPanel.add(backButton);
    }


    // MODIFIES: this, bookkeeper
    // EFFECTS: if the "back" button is clicked, this panel is no longer visible, and previous panels are restored
    @Override
    public void actionPerformed(ActionEvent e) {
        clickSound.playSound();
        if (e.getActionCommand().equals("back")) {
            this.setVisible(false);
            bookkeeper.getAccountListPanel().setVisible(true);
        }
    }
}
