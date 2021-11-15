package ui;

import model.Task;
import output.CustomOutputStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.time.DateTimeException;

//Edit Task Menu/Processor frame
public class EditTaskFrame extends JFrame implements ActionListener {

    private final MainFrame mainFrame;
    private final JFrame previousFrame;
    private final JPanel optionsPanel;
    private JPanel namePanel;
    private JPanel descPanel;
    private JPanel markPanel;
    private JPanel datePanel;
    private final JTabbedPane tabbedPane;
    private JButton nameButton;
    private JButton descButton;
    private JButton markButton;
    private JButton dateButton;
    private JButton backButton;
    private JButton applyNameButton;
    private JButton applyDescButton;
    private JButton applyMarkButton;
    private JButton applyDateButton;
    private JTextField nameTextField;
    private JTextField descTextField;
    private JTextField dateTextField;
    private JTextField hoursTextField;
    private JTextField minutesTextField;
    private JTextArea nameTextArea;
    private JTextArea descTextArea;
    private JTextArea dateTextArea;
    private JTextArea markStatusTextArea;
    private String input;
    private PrintStream printStream;
    private final Task taskToEdit;

    //MODIFIES: this
    //EFFECTS: sets up the layout
    public EditTaskFrame(MainFrame mainFrame, JFrame previousFrame, Task taskToEdit) {
        super("Edit Task");
        this.mainFrame = mainFrame;
        this.previousFrame = previousFrame;
        this.taskToEdit = taskToEdit;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        JPanel mainPanel;
        add(mainPanel = new JPanel());

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(optionsPanel = new JPanel(), BorderLayout.WEST);
        mainPanel.add(tabbedPane = new JTabbedPane(), BorderLayout.AFTER_LINE_ENDS);

        setUpOptionsPanel();
        setUpTabbedPane();
        setUpNamePanel();
        setUpDescPanel();
        setUpMarkPanel();
        setUpDatePanel();
        setUpButtons();

        pack();
    }

    //MODIFIES: this, mainFrame, previousFrame
    //EFFECTS: processes the buttons' click
    @Override
    public void actionPerformed(ActionEvent e) {
        cleanTextAreas();
        if (e.getSource() == nameButton) {
            tabbedPane.setSelectedIndex(0);
        } else if (e.getSource() == descButton) {
            tabbedPane.setSelectedIndex(1);
        } else if (e.getSource() == markButton) {
            tabbedPane.setSelectedIndex(2);
        } else if (e.getSource() == dateButton) {
            tabbedPane.setSelectedIndex(3);
        } else if (e.getSource() == applyNameButton) {
            processName();
        } else if (e.getSource() == applyDescButton) {
            processDesc();
        } else if (e.getSource() == applyMarkButton) {
            processMark();
        } else if (e.getSource() == applyDateButton) {
            processDate();
        } else if (e.getSource() == backButton) {
            setVisible(false);
            previousFrame.setVisible(true);
        }
        pack();
    }

    //MODIFIES: this
    //EFFECTS: sets up the layout of a name panel
    private void setUpNamePanel() {
        namePanel.setLayout(new GridLayout(0,1));
        namePanel.add(new JLabel("Enter new name:"));
        namePanel.add(nameTextField = new JTextField());
        namePanel.add(applyNameButton = new JButton("Apply"));
        namePanel.add(nameTextArea = new JTextArea());
    }

    //MODIFIES: this
    //EFFECTS: sets up the layout of a desc panel
    private void setUpDescPanel() {
        descPanel.setLayout(new GridLayout(0,1));
        descPanel.add(new JLabel("Enter new description:"));
        descPanel.add(descTextField = new JTextField());
        descPanel.add(applyDescButton = new JButton("Apply"));
        descPanel.add(descTextArea = new JTextArea());
    }

    //MODIFIES: this
    //EFFECTS: sets up the layout of a mark panel
    private void setUpMarkPanel() {
        markPanel.setLayout(new GridLayout(0,1));
        markPanel.add(new JLabel("Mark:"));
        markPanel.add(markStatusTextArea = new JTextArea());
        if (taskToEdit.getMark()) {
            markStatusTextArea.setText("DONE");
        } else {
            markStatusTextArea.setText("UNDONE");
        }
        markPanel.add(applyMarkButton = new JButton("Change Mark"));
    }

    //MODIFIES: this
    //EFFECTS: sets up the layout of a date panel
    private void setUpDatePanel() {
        datePanel.setLayout(new GridLayout(0,1));
        datePanel.add(new JLabel("Enter new date (DDMMYYYY):"));
        datePanel.add(dateTextField = new JTextField());
        datePanel.add(new JLabel("Hours (HH):"));
        datePanel.add(hoursTextField = new JTextField());
        datePanel.add(new JLabel("Minutes (MM):"));
        datePanel.add(minutesTextField = new JTextField());
        datePanel.add(applyDateButton = new JButton("Apply"));
        datePanel.add(dateTextArea = new JTextArea());
    }

    //MODIFIES: this
    //EFFECTS: sets up the layout of a tabbed pane
    private void setUpTabbedPane() {
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.add(namePanel = new JPanel());
        tabbedPane.setTitleAt(0, "Change name");
        tabbedPane.add(descPanel = new JPanel());
        tabbedPane.setTitleAt(1, "Change description");
        tabbedPane.add(markPanel = new JPanel());
        tabbedPane.setTitleAt(2, "Change mark");
        tabbedPane.add(datePanel = new JPanel());
        tabbedPane.setTitleAt(3, "Change date and time");
    }

    //MODIFIES: this
    //EFFECTS: sets up the layout of an options panel
    private void setUpOptionsPanel() {
        optionsPanel.setLayout(new GridLayout(5,1));
        optionsPanel.add(nameButton = new JButton("Change name"));
        optionsPanel.add(descButton = new JButton("Change description"));
        optionsPanel.add(markButton = new JButton("Change mark"));
        optionsPanel.add(dateButton = new JButton("Change date and time"));
        optionsPanel.add(backButton = new JButton("Task Manager"));
    }

    //MODIFIES: buttons
    //EFFECTS: identifies the action listener for the buttons
    private void setUpButtons() {
        nameButton.addActionListener(this);
        descButton.addActionListener(this);
        markButton.addActionListener(this);
        dateButton.addActionListener(this);
        backButton.addActionListener(this);
        applyNameButton.addActionListener(this);
        applyDescButton.addActionListener(this);
        applyMarkButton.addActionListener(this);
        applyDateButton.addActionListener(this);
    }

    //MODIFIES: this, mainFrame
    //EFFECTS: processes "Change Name" button
    private void processName() {
        printStream = new PrintStream(new CustomOutputStream(nameTextArea));
        System.setOut(printStream);
        System.setErr(printStream);
        input = nameTextField.getText();
        mainFrame.changeName(taskToEdit, input);
        System.out.println("Changes applied");
    }

    //MODIFIES: this, mainFrame
    //EFFECTS: processes "Change Desc" button
    private void processDesc() {
        printStream = new PrintStream(new CustomOutputStream(descTextArea));
        System.setOut(printStream);
        System.setErr(printStream);
        input = descTextField.getText();
        mainFrame.changeDescription(taskToEdit, input);
        System.out.println("Changes applied");
    }

    //MODIFIES: taskToEdit, this
    //EFFECTS: processes "Change Name" button
    private void processMark() {
        taskToEdit.changeMark();
        if (taskToEdit.getMark()) {
            markStatusTextArea.setText("DONE");
        } else {
            markStatusTextArea.setText("UNDONE");
        }
    }

    //MODIFIES: this, mainFrame
    //EFFECTS: processes "Change Name" button
    private void processDate() {
        printStream = new PrintStream(new CustomOutputStream(dateTextArea));
        System.setOut(printStream);
        System.setErr(printStream);
        String strDate = dateTextField.getText();
        String strHours = hoursTextField.getText();
        String strMinutes = minutesTextField.getText();
        try {
            mainFrame.changeCalendar(taskToEdit, strDate, strHours, strMinutes);
            System.out.println("Changes applied");
        } catch (DateTimeException ex) {
            System.out.println("Invalid date format");
        } catch (NumberFormatException ex) {
            System.out.println("Invalid time format");
        }
    }

    //MODIFIES: this
    //EFFECTS: cleans up text areas
    private void cleanTextAreas() {
        nameTextArea.setText("");
        descTextArea.setText("");
        dateTextArea.setText("");
    }

}
