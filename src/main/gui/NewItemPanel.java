package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

// represents a panel allowing for user input
public abstract class NewItemPanel extends JPanel implements ActionListener {
    protected static final String ADD_BUTTON_COMMAND = "add";
    protected static final String CANCEL_BUTTON_COMMAND = "cancel";
    protected JPanel textInputPanel;
    protected JPanel buttonPanel;
    private GridLayout textInputLayout;
    protected int numTextFields;
    protected JButton addButton;
    protected JButton cancelButton;
    protected ButtonClickSound clickSound;

    // REQUIRES: numTextFields > 0
    // EFFECTS: creates a new item panel with the given number of text fields
    public NewItemPanel(int numTextFields) {
        super();
        this.numTextFields = numTextFields;
        clickSound = new ButtonClickSound();
        setPreferredSize(new Dimension(ListPanel.WIDTH, ListPanel.HEIGHT));
        add(createWhiteSpace(75));
        setBorder(makeBorder());
        setUpTextInputPanel();
        addTextFieldsAndLabels();
        setUpButtonPanel();
        setOpaque(true);
    }

    // MODIFIES: this
    // EFFECTS: adds white space with the panel width and the given height to the panel
    public static JPanel createWhiteSpace(int height) {
        JPanel blankSpace = new JPanel();
        blankSpace.setPreferredSize(new Dimension(ListPanel.WIDTH - 15, height));
        return blankSpace;
    }

    // EFFECTS: creates the border around the new item panel
    public static Border makeBorder() {
        Border spacing = BorderFactory.createEmptyBorder(5,5,5,5);
        Border etchedBorder = BorderFactory.createEtchedBorder();
        return BorderFactory.createCompoundBorder(etchedBorder, spacing);
    }

    // MODIFIES: this
    // EFFECTS: creates the panel holding text fields requiring user input
    private void setUpTextInputPanel() {
        textInputLayout = new GridLayout(numTextFields, 2, 5, 5);
        textInputPanel = new JPanel(textInputLayout);
        textInputPanel.setPreferredSize(new Dimension(ListPanel.WIDTH - 15, 150));
        add(textInputPanel);
    }

    // MODIFIES: this
    // EFFECTS: creates the panel holding buttons for adding a new object or cancelling
    //          button actions are not yet implemented
    private void setUpButtonPanel() {
        buttonPanel = new JPanel();

        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");

        addButton.setActionCommand(ADD_BUTTON_COMMAND);
        cancelButton.setActionCommand(CANCEL_BUTTON_COMMAND);

        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        buttonPanel.setPreferredSize(new Dimension(ListPanel.WIDTH - 15, 50));
        add(buttonPanel);
        add(createWhiteSpace(75));
        setButtonHandlers();
    }


    // MODIFIES: this
    // EFFECTS: adds labels to text fields
    protected abstract void addTextFieldsAndLabels();

    // MODIFIES: this
    // EFFECTS: set handlers to perform an action when the buttons are clicked
    private void setButtonHandlers() {
        addButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }
}
