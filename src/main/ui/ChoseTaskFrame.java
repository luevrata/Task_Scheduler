package ui;

import model.Task;
import output.CustomOutputStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

//Edit Task Processor frame
public class ChoseTaskFrame extends JFrame implements ActionListener {
    private final MainFrame mainFrame;
    private final JFrame previousFrame;
    private final JTextField nameTextField;
    private final JButton choseButton;
    private final JButton backButton;
    private final JTextArea textArea;


    //MODIFIES: this
    //EFFECTS: sets up the layout
    public ChoseTaskFrame(MainFrame mainFrame, JFrame previousFrame) {
        super("Chose Task");
        this.mainFrame = mainFrame;
        this.previousFrame = previousFrame;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        JPanel mainPanel;
        add(mainPanel = new JPanel());

        mainPanel.setLayout(new GridLayout(0,1));
        mainPanel.add(new JLabel("Enter the name of the task you want to edit:"));
        mainPanel.add(nameTextField = new JTextField());
        mainPanel.add(choseButton = new JButton("Chose"));
        mainPanel.add(textArea = new JTextArea());
        mainPanel.add(backButton = new JButton("Task Manager"));

        choseButton.addActionListener(this);
        backButton.addActionListener(this);
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
}
