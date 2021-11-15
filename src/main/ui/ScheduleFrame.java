package ui;

import output.CustomOutputStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.time.DateTimeException;

//Display Schedule options and processor frame
public class ScheduleFrame extends JFrame implements ActionListener {

    private final MainFrame mainFrame;
    private final JTabbedPane tabbedPane;
    private final JPanel optionsPanel;
    private JPanel wholePanel;
    private JPanel dayPanel;
    private JPanel donePanel;
    private JPanel undonePanel;
    private JButton wholeButton;
    private JButton dayButton;
    private JButton doneButton;
    private JButton undoneButton;
    private JButton saveDateButton;
    private JButton backButton;
    private final JTextArea wholeTextArea;
    private JTextArea dayTextArea;
    private final JTextArea doneTextArea;
    private final JTextArea undoneTextArea;
    private PrintStream printStream;
    private JTextField dateTextField;

    //MODIFIES: this
    //EFFECTS: sets up the layout
    public ScheduleFrame(MainFrame mainFrame) {
        super("Display Schedule");
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
        wholePanel.add(wholeTextArea = new JTextArea());
        setUpDayPanel();
        donePanel.add(doneTextArea = new JTextArea());
        undonePanel.add(undoneTextArea = new JTextArea());
        setUpButtons();

        pack();
    }

    //MODIFIES: this
    //EFFECTS: processes the buttons' click
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == wholeButton) {
            processWhole();
        } else if (e.getSource() == dayButton) {
            tabbedPane.setSelectedIndex(1);
        } else if (e.getSource() == saveDateButton) {
            processDay();
        } else if (e.getSource() == doneButton) {
            processDone();
        } else if (e.getSource() == undoneButton) {
            processUndone();
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
        optionsPanel.add(wholeButton = new JButton("Whole Schedule"));
        optionsPanel.add(dayButton = new JButton("Day Schedule"));
        optionsPanel.add(doneButton = new JButton("Done Schedule"));
        optionsPanel.add(undoneButton = new JButton("Undone Schedule"));
        optionsPanel.add(backButton = new JButton("Main Menu"));
    }

    //MODIFIES: this
    //EFFECTS: sets up the layout of a tabbed pane
    private void setUpTabbedPane() {
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.add(wholePanel = new JPanel());
        tabbedPane.setTitleAt(0, "Whole Schedule");
        tabbedPane.add(dayPanel = new JPanel());
        tabbedPane.setTitleAt(1, "Day Schedule");
        tabbedPane.add(donePanel = new JPanel());
        tabbedPane.setTitleAt(2, "Done Schedule");
        tabbedPane.add(undonePanel = new JPanel());
        tabbedPane.setTitleAt(3, "Undone Schedule");
    }

    //MODIFIES: this
    //EFFECTS: sets up the layout of a day panel
    private void setUpDayPanel() {
        dayPanel.setLayout(new GridLayout(4,1));
        dayPanel.add(new JLabel("Enter Date (DDMMYYYY):"));
        dayPanel.add(dateTextField = new JTextField());
        dayPanel.add(saveDateButton = new JButton("Save"));
        dayPanel.add(dayTextArea = new JTextArea());
    }

    //MODIFIES: buttons
    //EFFECTS: identifies the action listener for the buttons
    private void setUpButtons() {
        wholeButton.addActionListener(this);
        dayButton.addActionListener(this);
        doneButton.addActionListener(this);
        undoneButton.addActionListener(this);
        saveDateButton.addActionListener(this);
        backButton.addActionListener(this);
    }

    //EFFECTS: processes "Whole schedule button"
    private void processWhole() {
        tabbedPane.setSelectedIndex(0);
        wholeTextArea.setText("");
        printStream = new PrintStream(new CustomOutputStream(wholeTextArea));
        System.setOut(printStream);
        System.setErr(printStream);
        mainFrame.displayWholeSchedule();
    }

    //EFFECTS: processes "Day schedule button"
    private void processDay() {
        dayTextArea.setText("");
        String strDate = dateTextField.getText();
        printStream = new PrintStream(new CustomOutputStream(dayTextArea));
        System.setOut(printStream);
        System.setErr(printStream);
        try {
            mainFrame.displayDaySchedule(mainFrame.makeCalendarDate(strDate));
        } catch (DateTimeException ex) {
            System.out.println("Invalid date format");
        }
    }

    //EFFECTS: processes "DONE schedule button"
    private void processDone() {
        tabbedPane.setSelectedIndex(2);
        doneTextArea.setText("");
        printStream = new PrintStream(new CustomOutputStream(doneTextArea));
        System.setOut(printStream);
        System.setErr(printStream);
        mainFrame.displayDoneSchedule();
    }

    //EFFECTS: processes "UNDONE schedule button"
    private void processUndone() {
        tabbedPane.setSelectedIndex(3);
        undoneTextArea.setText("");
        printStream = new PrintStream(new CustomOutputStream(undoneTextArea));
        System.setOut(printStream);
        System.setErr(printStream);
        mainFrame.displayUndoneSchedule();
    }
}
