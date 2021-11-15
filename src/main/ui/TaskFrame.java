package ui;

import output.CustomOutputStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.time.DateTimeException;

//Task Manager options and processor frame
public class TaskFrame extends JFrame implements ActionListener {

    private final MainFrame mainFrame;
    private final JPanel optionsPanel;
    private JPanel addPanel;
    private JPanel deletePanel;
    private JPanel findPanel;
    private final JTabbedPane tabbedPane;
    private JButton addButton;
    private JButton deleteButton;
    private JButton findButton;
    private JButton editButton;
    private JButton backButton;
    private JButton addTaskButton;
    private JButton findTaskButton;
    private JButton deleteTaskButton;
    private JTextField addNameTextField;
    private JTextField addDescTextField;
    private JTextField addDateTextField;
    private JTextField addHoursTextField;
    private JTextField addMinutesTextField;
    private JTextField findTextField;
    private JTextField deleteTextField;
    private JTextArea addTextArea;
    private JTextArea findTextArea;
    private JTextArea deleteTextArea;
    private String strName;
    private PrintStream printStream;

    //MODIFIES: this
    //EFFECTS: sets up the layout
    public TaskFrame(MainFrame mainFrame) {
        super("Task Manager");
        this.mainFrame = mainFrame;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        JPanel mainPanel;
        add(mainPanel = new JPanel());

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(optionsPanel = new JPanel(), BorderLayout.WEST);
        mainPanel.add(tabbedPane = new JTabbedPane(), BorderLayout.AFTER_LINE_ENDS);

        setUpOptionsPanel();
        setUpTabbedPane();
        setUpAddPanel();
        setUpDeletePanel();
        setUpFindPanel();
        setUpButtons();

        pack();
    }

    //MODIFIES: this, mainFrame
    //EFFECTS: processes the buttons' click
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == findButton) {
            tabbedPane.setSelectedIndex(2);
        } else if (e.getSource() == addButton) {
            tabbedPane.setSelectedIndex(0);
        } else if (e.getSource() == deleteButton) {
            tabbedPane.setSelectedIndex(1);
        } else if (e.getSource() == findTaskButton) {
            processFind();
        } else if (e.getSource() == addTaskButton) {
            processAdd();
        } else if (e.getSource() == deleteTaskButton) {
            processDelete();
        } else if (e.getSource() == editButton) {
            setVisible(false);
            new ChoseTaskFrame(mainFrame, this);
        } else if (e.getSource() == backButton) {
            setVisible(false);
            mainFrame.setVisible(true);
        }
        pack();
    }

    //MODIFIES: this
    //EFFECTS: sets up the layout of an options panel
    private void setUpOptionsPanel() {
        optionsPanel.setLayout(new GridLayout(5,1));
        optionsPanel.add(addButton = new JButton("Add Task"));
        optionsPanel.add(deleteButton = new JButton("Delete Task"));
        optionsPanel.add(findButton = new JButton("Find Task"));
        optionsPanel.add(editButton = new JButton("Edit Task"));
        optionsPanel.add(backButton = new JButton("Main Menu"));
    }

    //MODIFIES: this
    //EFFECTS: sets up the layout of a tabbed pane
    private void setUpTabbedPane() {
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.add(addPanel = new JPanel());
        tabbedPane.setTitleAt(0, "Add Task");
        tabbedPane.add(deletePanel = new JPanel());
        tabbedPane.setTitleAt(1, "Delete Task");
        tabbedPane.add(findPanel = new JPanel());
        tabbedPane.setTitleAt(2, "Find Task");
    }

    //MODIFIES: this
    //EFFECTS: sets up the layout of an add panel
    private void setUpAddPanel() {
        addPanel.setLayout(new GridLayout(0,1));
        addPanel.add(new JLabel("Name:"));
        addPanel.add(addNameTextField = new JTextField());
        addPanel.add(new JLabel("Description:"));
        addPanel.add(addDescTextField = new JTextField());
        addPanel.add(new JLabel("Date (DDMMYYYY):"));
        addPanel.add(addDateTextField = new JTextField());
        addPanel.add(new JLabel("Hours (HH):"));
        addPanel.add(addHoursTextField = new JTextField());
        addPanel.add(new JLabel("Minutes (MM):"));
        addPanel.add(addMinutesTextField = new JTextField());
        addPanel.add(addTaskButton = new JButton("Add"));
        addPanel.add(addTextArea = new JTextArea());
    }

    //MODIFIES: this
    //EFFECTS: sets up the layout of a 'delete' panel
    private void setUpDeletePanel() {
        deletePanel.setLayout(new GridLayout(0,1));
        deletePanel.add(new JLabel("Enter the name of the task:"));
        deletePanel.add(deleteTextField = new JTextField());
        deletePanel.add(deleteTaskButton = new JButton("Delete"));
        deletePanel.add(deleteTextArea = new JTextArea());
    }

    //MODIFIES: this
    //EFFECTS: sets up the layout of a find panel
    private void setUpFindPanel() {
        findPanel.setLayout(new GridLayout(0,1));
        findPanel.add(new JLabel("Enter the name of the task:"));
        findPanel.add(findTextField = new JTextField());
        findPanel.add(findTaskButton = new JButton("Find"));
        findPanel.add(findTextArea = new JTextArea());
    }

    //MODIFIES: buttons
    //EFFECTS: identifies the action listener for the buttons
    private void setUpButtons() {
        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        findButton.addActionListener(this);
        editButton.addActionListener(this);
        backButton.addActionListener(this);
        addTaskButton.addActionListener(this);
        findTaskButton.addActionListener(this);
        deleteTaskButton.addActionListener(this);
    }

    //MODIFIES: this, mainFrame
    //EFFECTS: processes "Add Task"
    private void processAdd() {
        addTextArea.setText("");
        strName = addNameTextField.getText();
        String strDesc = addDescTextField.getText();
        String strDate = addDateTextField.getText();
        String strHours = addHoursTextField.getText();
        String strMinutes = addMinutesTextField.getText();
        printStream = new PrintStream(new CustomOutputStream(addTextArea));
        System.setOut(printStream);
        System.setErr(printStream);
        try {
            mainFrame.addTask(strName, strDesc, strDate, strHours, strMinutes);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid time format");
        } catch (DateTimeException ex) {
            System.out.println("Invalid date format");
        }
    }

    //MODIFIES: this, mainFrame
    //EFFECTS: processes "Delete Task"
    private void processDelete() {
        deleteTextArea.setText("");
        strName = deleteTextField.getText();
        printStream = new PrintStream(new CustomOutputStream(deleteTextArea));
        System.setOut(printStream);
        System.setErr(printStream);
        mainFrame.deleteTask(strName);
    }

    //MODIFIES: this, mainFrame
    //EFFECTS: processes "Find Task"
    private void processFind() {
        findTextArea.setText("");
        strName = findTextField.getText();
        printStream = new PrintStream(new CustomOutputStream(findTextArea));
        System.setOut(printStream);
        System.setErr(printStream);
        mainFrame.findTask(strName);
    }
}
