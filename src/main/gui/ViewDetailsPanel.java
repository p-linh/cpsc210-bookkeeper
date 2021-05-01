package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

// represents a generic panel that will display information about an item
public abstract class ViewDetailsPanel extends JPanel implements ActionListener {
    protected JPanel detailsPanel;
    protected JPanel buttonPanel;
    protected ButtonClickSound clickSound;
    protected BookkeeperGUI bookkeeper;

    // EFFECTS: creates a new panel with the given number of fields to display information
    public ViewDetailsPanel(BookkeeperGUI bookkeeper, int numFields) {
        super();
        this.bookkeeper = bookkeeper;
        setPreferredSize(new Dimension(ListPanel.WIDTH, ListPanel.HEIGHT));
        setBorder(NewItemPanel.makeBorder());
        add(NewItemPanel.createWhiteSpace(75));
        setUpDetailsPanel(numFields);
        setUpLabels();
        setUpButtonPanel();
        setUpButtons();
        clickSound = new ButtonClickSound();
    }

    // EFFECTS: creates the panel holding the buttons
    private void setUpButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setPreferredSize(new Dimension(ListPanel.WIDTH - 15, 50));
        add(buttonPanel);
        add(NewItemPanel.createWhiteSpace(75));
    }

    // EFFECTS: creates the panel holding the labels
    private void setUpDetailsPanel(int numFields) {
        detailsPanel = new JPanel(new GridLayout(numFields, 2, 5, 5));
        detailsPanel.setPreferredSize(new Dimension(ListPanel.WIDTH - 15, 150));
        add(detailsPanel);
    }


    // MODIFIES: this
    // EFFECTS: sets up labels for the panel
    protected abstract void setUpLabels();

    // MODIFIES: this
    // EFFECTS: sets up the buttons needed for the panel
    protected abstract void setUpButtons();

    // MODIFIES: this
    // EFFECTS: clears labels holding info
    protected abstract void clearInfo();

    // MODIFIES: this
    // EFFECTS: updates labels to math the current item's info
    protected abstract void updateInfo();
}
