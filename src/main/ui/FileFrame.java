package ui;

import model.Event;
import model.EventLog;
import output.CustomOutputStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;

//File Options Menu frame
public class FileFrame extends JFrame implements ActionListener {

    private final MainFrame mainFrame;
    private JButton saveButton;
    private JButton loadButton;
    private JButton backButton;
    private JTextArea textArea;
    private JPanel mainPanel;

    //MODIFIES: this
    //EFFECTS: sets up the layout
    public FileFrame(MainFrame mainFrame) {
        super("File Options");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
        add(mainPanel = new JPanel());
        this.mainFrame = mainFrame;

        setUpMainPanel();

        PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
        System.setOut(printStream);
        System.setErr(printStream);

        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
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

    //MODIFIES: this
    //EFFECTS: sets up the layout of a main panel
    private void setUpMainPanel() {
        mainPanel.setLayout(new GridLayout(0,1));
        mainPanel.add(saveButton = new JButton("Save schedule to file"));
        mainPanel.add(loadButton = new JButton("Load schedule from file"));
        mainPanel.add(textArea = new JTextArea());
        mainPanel.add(backButton = new JButton("Main Menu"));
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
