package gui;

import model.account.AccountList;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

// represents the menu bar for the Bookkeeper application
public class MenuBar implements ActionListener {
    private JMenuBar menuBar;
    private JMenu menu;
    private BookkeeperGUI bookkeeper;

    // EFFECTS: creates a new menu bar, a new menu, and adds menu options
    public MenuBar(BookkeeperGUI bookkeeper) {
        this.bookkeeper = bookkeeper;

        menuBar = new JMenuBar();
        menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem saveOption = new JMenuItem("Save");
        menu.add(saveOption);
        saveOption.addActionListener(this);
        saveOption.setActionCommand("save");

        JMenuItem loadOption = new JMenuItem("Load");
        menu.add(loadOption);
        loadOption.addActionListener(this);
        loadOption.setActionCommand("load");

        JMenuItem newOption = new JMenuItem("New File");
        menu.add(newOption);
        newOption.addActionListener(this);
        newOption.setActionCommand("new");
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    // EFFECTS: if the "save" option is selected from the menu: saves the account list to file
    //          if the "load" option is selected from the menu: loads the account list from file
    //          if the "new" option is selected from the menu: clears the current account list
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("save")) {
            bookkeeper.getDataHandler().saveAccounts();
        } else if (e.getActionCommand().equals("load")) {
            bookkeeper.getDataHandler().loadAccounts();
        } else if (e.getActionCommand().equals("new")) {
            bookkeeper.getDataHandler().newFile();
            bookkeeper.getAccountListPanel().viewPanel.setVisible(false);
        }
    }
}
