package ui;

import output.CustomOutputStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

//File Options Menu frame
public class FileFrame extends JFrame implements ActionListener {

    private final MainFrame mainFrame;
    private final JButton saveButton;
    private final JButton loadButton;
    private final JButton backButton;
    private final JTextArea textArea;

    //MODIFIES: this
    //EFFECTS: sets up the layout
    public FileFrame(MainFrame mainFrame) {
        super("File Options");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        JPanel mainPanel;
        add(mainPanel = new JPanel());
        this.mainFrame = mainFrame;

        mainPanel.setLayout(new GridLayout(0,1));
        mainPanel.add(saveButton = new JButton("Save schedule to file"));
        mainPanel.add(loadButton = new JButton("Load schedule from file"));
        mainPanel.add(textArea = new JTextArea());
        mainPanel.add(backButton = new JButton("Main Menu"));

        PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
        System.setOut(printStream);
        System.setErr(printStream);

        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        backButton.addActionListener(this);

        pack();
    }

    //MODIFIES: this, mainFrame
    //EFFECTS: processes the buttons' click
    @Override
    public void actionPerformed(ActionEvent e) {
        textArea.setText("");
        if (e.getSource() == saveButton) {
            mainFrame.saveSchedule();
        } else if (e.getSource() == loadButton) {
            mainFrame.loadSchedule();
        } else if (e.getSource() == backButton) {
            mainFrame.setVisible(true);
            setVisible(false);
        }
        pack();
    }
}
