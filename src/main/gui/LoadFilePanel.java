package gui;

import model.account.AccountList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

// represents popup window for loading a file or creating a new file
public class LoadFilePanel extends JPanel implements ActionListener {
    private JButton loadButton;
    private JButton newButton;
    private ButtonClickSound clickSound;
    private BookkeeperGUI bookkeeper;

    // EFFECTS: creates a new panel for with buttons for loading and creating a file,
    //          sets up button clicking sound and a data handler
    public LoadFilePanel(BookkeeperGUI bookkeeper) {
        this.bookkeeper = bookkeeper;
        clickSound = new ButtonClickSound();
        setUpButtons();
    }

    // MODIFIES: this
    // EFFECTS: creates buttons for loading and creating a file
    private void setUpButtons() {
        loadButton = new JButton("Load File");
        loadButton.setActionCommand("load");
        loadButton.addActionListener(this);

        newButton = new JButton("New File");
        newButton.setActionCommand("new");
        newButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loadButton);
        buttonPanel.add(newButton);

        add(buttonPanel, BorderLayout.PAGE_START);
    }


    // MODIFIES: bookkeeper, this
    // EFFECTS: if "load" button is clicked:
    //              - accounts are loaded onto the application from file and this window is closed
    //          if "new" button is clicked: closes this window
    //          a clicking sound is played when any button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        clickSound.playSound();
        if (e.getActionCommand().equals("load")) {
            bookkeeper.getDataHandler().loadAccounts();
            bookkeeper.getLoadWindow().dispose();
        } else if (e.getActionCommand().equals("new")) {
            bookkeeper.getLoadWindow().dispose();
        }
    }



}

