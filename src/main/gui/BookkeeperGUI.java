package gui;

import model.account.AccountList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// represents the main frame of the application
public class BookkeeperGUI extends WindowAdapter {
    public static final int WIDTH = 1600;
    public static final int HEIGHT = 1000;
    private JFrame mainWindow;
    private JFrame loadWindow;
    private JPanel mainPanel;
    private AccountListPanel accountListPanel;
    private DataHandler dataHandler;

    // EFFECTS: creates a new application frame and adds all the necessary panels and a data handler
    public BookkeeperGUI() {
        dataHandler = new DataHandler(this);
        initializeMainWindowAndPanel();
        loadFilePopup();
        accountListPanel();
    }

    public DataHandler getDataHandler() {
        return dataHandler;
    }

    public JFrame getLoadWindow() {
        return loadWindow;
    }

    public AccountListPanel getAccountListPanel() {
        return accountListPanel;
    }


    // MODIFIES: this
    // EFFECTS: creates a new popup window prompting user to load or start a new file
    private void loadFilePopup() {
        loadWindow = new JFrame("Load/New File");
        loadWindow.add(new LoadFilePanel(this));
        loadWindow.toFront();
        loadWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        loadWindow.setLocationRelativeTo(null);
        loadWindow.setVisible(true);
        loadWindow.pack();

    }

    // MODIFIES: this
    // EFFECTS: draws the main window and panel in which the app will operate
    private void initializeMainWindowAndPanel() {
        mainWindow = new JFrame("Bookkeeper");
        mainWindow.setLayout(new CardLayout());
        mainWindow.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        mainWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainWindow.addWindowListener(this);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
        mainPanel = new JPanel();
        mainPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        mainWindow.add(mainPanel);

        MenuBar menuBar = new MenuBar(this);
        mainWindow.setJMenuBar(menuBar.getMenuBar());
    }

    // MODIFIES: this
    // EFFECTS: draws an account list panel and adds it to the main panel
    private void accountListPanel() {
        accountListPanel = new AccountListPanel(this);
        mainPanel.add(accountListPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }


    // MODIFIES: this
    // EFFECTS: adds the given panel to the main panel
    public void addPanel(JPanel panel) {
        mainPanel.add(panel);
        panel.setVisible(false);
        mainPanel.revalidate();
        mainPanel.repaint();
    }


    // MODIFIES: this, dataHandler, accountListPanel
    // EFFECTS: displays a popup window prompting user to save when the close button on the application is clicked
    @Override
    public void windowClosing(WindowEvent we) {
        int n = JOptionPane.showConfirmDialog(mainPanel, "Would you like to save before exiting?",
                "Exiting", JOptionPane.YES_NO_OPTION);

        if (n == JOptionPane.YES_OPTION) {
            dataHandler.saveAccounts();
            mainWindow.dispose();
        } else if (n == JOptionPane.NO_OPTION) {
            mainWindow.dispose();
        }
    }

    // EFFECTS: runs the application
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BookkeeperGUI();
            }
        });
    }

}
