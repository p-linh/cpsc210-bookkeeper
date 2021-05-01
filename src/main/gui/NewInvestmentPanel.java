package gui;

import model.Investment;

import javax.swing.*;
import java.awt.event.ActionEvent;

// represents a panel allowing user to create a new investment
public class NewInvestmentPanel extends NewItemPanel {
    private JTextField nameField;
    private JTextField symbolField;
    private Investment investment;
    private InvestmentListPanel investmentsPanel;

    // EFFECTS: creates a new panel with text fields and labels for user input
    public NewInvestmentPanel(InvestmentListPanel investmentsPanel) {
        super(2);
        this.investmentsPanel = investmentsPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates labels and text fields for the panel
    @Override
    protected void addTextFieldsAndLabels() {
        JLabel symbolLabel = new JLabel("Symbol:");
        JLabel nameLabel = new JLabel("Investment Name:");

        symbolField = new JTextField();
        nameField = new JTextField();

        textInputPanel.add(symbolLabel);
        textInputPanel.add(symbolField);

        textInputPanel.add(nameLabel);
        textInputPanel.add(nameField);
    }

    // MODIFIES: this, investmentPanel, investment
    // EFFECTS: if the add button is clicked, and if the investment doesn't already exist,
    //              adds an investment to the investment panel and account
    //          if the cancel button is clicked, this panel is no longer visible
    @Override
    public void actionPerformed(ActionEvent e) {
        clickSound.playSound();
        if (e.getActionCommand().equals(ADD_BUTTON_COMMAND)) {
            investment = new Investment(symbolField.getText(), nameField.getText());
            investmentsPanel.addInvestmentToPanel(investment);
            this.setVisible(false);
            nameField.setText("");
            symbolField.setText("");
            investmentsPanel.setVisible(true);

        } else if (e.getActionCommand().equals(CANCEL_BUTTON_COMMAND)) {
            this.setVisible(false);
        }
    }


}
