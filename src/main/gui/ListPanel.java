package gui;


import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;

// code referenced from java swing list tutorial at: https://docs.oracle.com/javase/tutorial/uiswing/components/list.html
// represents a generic panel displaying a list
public abstract class ListPanel extends JPanel implements ActionListener, ListSelectionListener {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 400;
    protected static final String ADD_BUTTON_COMMAND = "add";
    protected static final String REMOVE_BUTTON_COMMAND = "remove";
    protected DefaultListModel listModel;
    protected JList displayedList;
    protected ButtonClickSound clickSound;
    protected JButton addButton;
    protected JButton removeButton;
    protected JPanel buttonPane;
    protected BookkeeperGUI bookkeeper;

    public ListPanel(BookkeeperGUI bookkeeper) {
        super(new BorderLayout());
        this.bookkeeper = bookkeeper;
        clickSound = new ButtonClickSound();
        setTitle();
        setUpList();
        setUpButtonPanel();
        setUpButtons();
    }

    // MODIFIES: this
    // EFFECTS: sets the title for the list panel
    protected abstract void setTitle();

    // MODIFIES: this
    // EFFECTS: creates the list that's displayed and puts it in a scroll pane
    private void setUpList() {
        listModel = new DefaultListModel();
        displayedList = new JList<>(listModel);
        displayedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        displayedList.setSelectedIndex(0);
        displayedList.addListSelectionListener(this);
        displayedList.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(displayedList);
        add(listScrollPane, BorderLayout.CENTER);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    // MODIFIES: this
    // EFFECTS: creates buttons for managing the account list
    protected void setUpButtons() {
        addButton = new JButton();
        addButton.setActionCommand(ADD_BUTTON_COMMAND);
        addButton.addActionListener(this);

        removeButton = new JButton();
        removeButton.setActionCommand(REMOVE_BUTTON_COMMAND);
        removeButton.addActionListener(this);
        removeButton.setEnabled(false);

        buttonPane.add(addButton);
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(removeButton);

    }

    // MODIFIES: this
    // EFFECTS: sets up the panel holding the buttons
    private void setUpButtonPanel() {
        buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));

        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(buttonPane, BorderLayout.PAGE_END);
    }

    // MODIFIES: this
    // EFFECTS: removes the selected account from the account list
    protected void removeItemFromList() {
        int index = displayedList.getSelectedIndex();
        listModel.remove(index);

        int size = listModel.getSize();
        if (size == 0) {
            removeButton.setEnabled(false);
        } else {
            if (index == listModel.getSize()) {
                index--;
            }

            displayedList.setSelectedIndex(index);
            displayedList.ensureIndexIsVisible(index);
        }
    }

    // MODIFIES: this
    // EFFECTS: if there is a current selection, sets the selection to the next item in the list, otherwise sets the
    //          selection to the first item in the list
    protected void setSelection() {
        int index = displayedList.getSelectedIndex();
        if (index == -1) {
            index = 0;
        } else {
            index++;
        }

        displayedList.setSelectedIndex(index);
        displayedList.ensureIndexIsVisible(index);
    }
}
