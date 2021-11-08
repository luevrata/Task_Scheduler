package ui;

import output.CustomOutputStream;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

public class TaskFrame extends JFrame implements ActionListener {
    private JFrame taskFrame;
    private JPanel mainPanel;
    private JPanel optionsPanel;
    private JTabbedPane tabbedPane1;
    private JButton findTaskButton;
    private JTextField nameFindFieldText;
    private JButton findButton;
    private JTextArea foundTask;
    private JButton addTaskButton;
    private JTextField nameAddField;
    private JTextField descField;
    private JTextField dateField;
    private JTextField hoursField;
    private JTextField minutesField;
    private JButton saveSettingsButton;
    private JTextArea addedMessage;
    private MainFrame mainFrame;
    private String strName;
    private String strDesc;
    private String strDate;
    private String strHours;
    private String strMinutes;
    private PrintStream printStream;

    public TaskFrame(MainFrame mainFrame) {
        super("Task Menu");
        this.mainFrame = mainFrame;
        taskFrame = new JFrame();
        taskFrame.setSize(900,900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        taskFrame.setVisible(true);
        taskFrame.add(mainPanel);
        findTaskButton.addActionListener(this);
        findButton.addActionListener(this);
        addTaskButton.addActionListener(this);
        saveSettingsButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == findTaskButton) {
            tabbedPane1.setSelectedIndex(2);
        } else if (e.getSource() == findButton) {
            foundTask.setText("");
            strName = nameFindFieldText.getText();
            printStream = new PrintStream(new CustomOutputStream(foundTask));
            System.setOut(printStream);
            System.setErr(printStream);
            mainFrame.getApp().findTask(strName);
        } else if (e.getSource() == addTaskButton) {
            tabbedPane1.setSelectedIndex(0);
        } else if (e.getSource() == saveSettingsButton) {
            addedMessage.setText("");
            strName = nameAddField.getText();
            strDesc = descField.getText();
            strDate = dateField.getText();
            strHours = hoursField.getText();
            strMinutes = minutesField.getText();
            printStream = new PrintStream(new CustomOutputStream(addedMessage));
            System.setOut(printStream);
            System.setErr(printStream);
            mainFrame.getApp().addTask(strName, strDesc, strDate, strHours,strMinutes);
        }
    }
}
