package ui;

import model.Event;
import model.EventLog;
import model.Task;
import output.CustomOutputStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;

//Edit Task Processor frame
public class ChoseTaskFrame extends JFrame implements ActionListener {
    private final MainFrame mainFrame;
    private final JFrame previousFrame;
    private JTextField nameTextField;
    private JButton choseButton;
    private JButton backButton;
    private JTextArea textArea;
    private JPanel mainPanel;


    //MODIFIES: this
    //EFFECTS: sets up the layout
    public ChoseTaskFrame(MainFrame mainFrame, JFrame previousFrame) {
        super("Chose Task");
        this.mainFrame = mainFrame;
        this.previousFrame = previousFrame;
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
        add(mainPanel = new JPanel());

        setUpMainPanel();

        choseButton.addActionListener(this);
        backButton.addActionListener(this);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.setOut(mainFrame.getOldStream());
                for (Event ev: EventLog.getInstance()) {
                    System.out.println(ev.toString());
                }
                System.exit(0);
            }
        });

        pack();
    }

    //MODIFIES: this, previousFrame
    //EFFECTS: processes the buttons' click
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == choseButton) {
            textArea.setText("");
            PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
            System.setOut(printStream);
            System.setErr(printStream);

            String input = nameTextField.getText();
            Task taskToEdit = mainFrame.getSchedule().findTask(input);

            if (taskToEdit != null) {
                setVisible(false);
                new EditTaskFrame(mainFrame, previousFrame, taskToEdit);
            } else {
                System.out.println("No task found");
            }
        } else if (e.getSource() == backButton) {
            setVisible(false);
            previousFrame.setVisible(true);
        }

        pack();
    }


    //MODIFIES: this
    //EFFECTS: sets up the layout of a main panel
    private void setUpMainPanel() {
        mainPanel.setLayout(new GridLayout(0,1));
        mainPanel.add(new JLabel("Enter the name of the task you want to edit:"));
        mainPanel.add(nameTextField = new JTextField());
        mainPanel.add(choseButton = new JButton("Chose"));
        mainPanel.add(textArea = new JTextArea());
        mainPanel.add(backButton = new JButton("Task Manager"));
    }
}
