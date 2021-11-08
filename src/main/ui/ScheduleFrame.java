package ui;

import output.CustomOutputStream;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.time.DateTimeException;

public class ScheduleFrame extends JFrame implements ActionListener {

    private JFrame scheduleFrame;
    private MainFrame mainFrame;
    private JPanel mainPanel;
    private JPanel optionsPanel;
    private JButton wholeScheduleButton;
    private JTabbedPane tabbedPane1;
    private JTextArea wholeSchedule;
    private JTextField dateFieldText;
    private JLabel dateField;
    private JButton dayScheduleButton;
    private JTextArea daySchedule;
    private JButton saveDateButton;
    private JButton doneScheduleButton;
    private JTextArea doneSchedule;
    private JButton undoneScheduleButton;
    private JTextArea undoneSchedule;
    private PrintStream printStream;
    private String strDate;

    public ScheduleFrame(MainFrame mainFrame) {
        super("Schedule Menu");
        this.mainFrame = mainFrame;
        scheduleFrame = new JFrame();
        scheduleFrame.setSize(900,900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        scheduleFrame.setVisible(true);
        scheduleFrame.add(mainPanel);

        //todo
        //mainPanel.remove();

        wholeScheduleButton.addActionListener(this);
        dayScheduleButton.addActionListener(this);
        saveDateButton.addActionListener(this);
        doneScheduleButton.addActionListener(this);
        undoneScheduleButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == wholeScheduleButton) {
            tabbedPane1.setSelectedIndex(0);
            wholeSchedule.setText("");
            printStream = new PrintStream(new CustomOutputStream(wholeSchedule));
            System.setOut(printStream);
            System.setErr(printStream);
            mainFrame.getApp().displayWholeSchedule();
        } else if (e.getSource() == dayScheduleButton) {
            tabbedPane1.setSelectedIndex(1);
        } else if (e.getSource() == saveDateButton) {
            daySchedule.setText("");
            strDate = dateFieldText.getText();
            printStream = new PrintStream(new CustomOutputStream(daySchedule));
            System.setOut(printStream);
            System.setErr(printStream);
            try {
                mainFrame.getApp().displayDaySchedule(mainFrame.getApp().makeCalendarDate(strDate));
            } catch (DateTimeException ex) {
                System.out.println("Invalid date format");
            }

        } else if (e.getSource() == doneScheduleButton) {
            tabbedPane1.setSelectedIndex(2);
            doneSchedule.setText("");
            printStream = new PrintStream(new CustomOutputStream(doneSchedule));
            System.setOut(printStream);
            System.setErr(printStream);
            mainFrame.getApp().displayDoneSchedule();
        } else if (e.getSource() == undoneScheduleButton) {
            tabbedPane1.setSelectedIndex(3);
            undoneSchedule.setText("");
            printStream = new PrintStream(new CustomOutputStream(undoneSchedule));
            System.setOut(printStream);
            System.setErr(printStream);
            mainFrame.getApp().displayUndoneSchedule();
        }
    }
}
