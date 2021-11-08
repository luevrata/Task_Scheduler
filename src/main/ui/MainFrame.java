package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {
    private JButton scheduleButton;
    private JButton taskManagerButton;
    private JButton fileOptionsButton;
    private JPanel mainPanel;

    private JFrame mainFrame;

    private TaskSchedulerApp app;

    public MainFrame() {
        super("Main Menu");
        app = new TaskSchedulerApp();
        mainFrame = new JFrame();
        mainFrame.setSize(900,900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.add(mainPanel);
        scheduleButton.addActionListener(this);
        taskManagerButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainFrame.setVisible(false);
        if (e.getSource() == scheduleButton) {
            new ScheduleFrame(this);
        } else if (e.getSource() == taskManagerButton) {
            new TaskFrame(this);
        }
    }

    public TaskSchedulerApp getApp() {
        return app;
    }
}
