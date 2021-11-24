package ui;

import model.Event;
import model.EventLog;
import model.Schedule;
import model.Task;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

//Main Menu Frame
public class MainFrame extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/schedule.json";
    private Schedule schedule;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private final JPanel mainPanel;
    private JButton scheduleButton;
    private JButton taskManagerButton;
    private JButton fileOptionsButton;
    private JLabel imageLabel;
    private PrintStream oldStream;

    //MODIFIES THIS
    //EFFECTS: sets up main menu layout
    public MainFrame() {
        super("Main Menu");
        oldStream = System.out;
        init();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
        add(mainPanel = new JPanel());

        setUpMainPanel();
        setUpIcon();


        scheduleButton.addActionListener(this);
        taskManagerButton.addActionListener(this);
        fileOptionsButton.addActionListener(this);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.setOut(oldStream);
                for (Event ev: EventLog.getInstance()) {
                    System.out.println(ev.toString());
                }
                System.exit(0);
            }
        });

        pack();
    }

    public PrintStream getOldStream() {
        return oldStream;
    }

    //EFFECTS: performs an action depending on button;
    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        if (e.getSource() == scheduleButton) {
            new ScheduleFrame(this);
        } else if (e.getSource() == taskManagerButton) {
            new TaskFrame(this);
        } else if (e.getSource() == fileOptionsButton) {
            new FileFrame(this);
        }
    }

    //MODIFIES: this
    //EFFECTS: initializes schedule
    private void init() {
        schedule = new Schedule("My Schedule");
        Scanner input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    //MODIFIES: this
    //EFFECTS: sets up main panel layout
    private void setUpMainPanel() {
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(scheduleButton = new JButton("Schedule"), BorderLayout.NORTH);
        mainPanel.add(taskManagerButton = new JButton("Task Manager"), BorderLayout.WEST);
        mainPanel.add(fileOptionsButton = new JButton("File Options"), BorderLayout.EAST);
        mainPanel.add(imageLabel = new JLabel(), BorderLayout.CENTER);
        imageLabel.setSize(300, 180);
    }

    //MODIFIES: this
    //EFFECTS: sets up the image, and it's size and inserts it into JLabel
    private void setUpIcon() {
        String sep = System.getProperty("file.separator");
        ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + sep  + "images" + sep + "todo.jpg");
        Image image = icon.getImage();
        Image modifiedImage = image.getScaledInstance(imageLabel.getWidth(),imageLabel.getHeight(),Image.SCALE_DEFAULT);
        ImageIcon modifiedIcon = new ImageIcon(modifiedImage);

        imageLabel.setIcon(modifiedIcon);
    }

    //EFFECTS: displays information of a particular task
    private void displayTask(Task task) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sendDateTime = simpleDateFormat.format(task.getDateTime().getTime());
        String sendMark;
        if (task.getMark()) {
            sendMark = "DONE";
        } else {
            sendMark = "UNDONE";
        }
        System.out.println("\nDate and time: " + sendDateTime);
        System.out.println("Name: " + task.getName());
        System.out.println("Description: " + task.getDescription());
        System.out.println("Mark: " + sendMark);
    }

    //EFFECTS: creates Calendar.Builder with a date from user input and returns it, throws DateTimeException if
    //date is not written in requested format, or such date does not exist
    private Calendar.Builder makeDate(String strDate) throws DateTimeException {
        LocalDate date;
        Calendar.Builder calendarBuilder;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy", Locale.ENGLISH);
        date = LocalDate.parse(strDate, formatter);
        calendarBuilder = new Calendar.Builder();
        calendarBuilder.setDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        return calendarBuilder;

    }

    //EFFECTS: creates a calendar with a date from user input and returns it, throws DateTimeException if
    //date is not written in requested format, or such date does not exist
    public Calendar makeCalendarDate(String strDate) throws DateTimeException {
        Calendar calendar;
        calendar = makeDate(strDate).build();
        return calendar;
    }

    //EFFECTS: creates a calendar with a date and time from user input and returns it throws DateTimeException if
    //date is not written in requested format, or such date does not exist; throws NumberFormat exception if given
    // time does not exist
    private Calendar makeCalendarDateAndTime(String strDate, String strHours, String strMinutes)
            throws DateTimeException, NumberFormatException {
        Calendar.Builder calendarBuilder = makeDate(strDate);
        Calendar calendar;

        int hours = Integer.parseInt(strHours);
        int minutes = Integer.parseInt(strMinutes);

        if (0 <= hours && hours <= 23 && 0 <= minutes && minutes <= 59) {
            calendarBuilder.setTimeOfDay(hours, minutes, 0);
        } else {
            throw new NumberFormatException();
        }

        calendar = calendarBuilder.build();
        return calendar;
    }

    //REQUIRES: newName has non-zero length
    //MODIFIES: this
    //EFFECTS: sets new name to a given task
    public void changeName(Task task, String name) {
        task.setName(name);
    }

    //MODIFIES: this
    //EFFECTS: sets new description to a given task
    public void changeDescription(Task task, String desc) {
        task.setDescription(desc);
    }

    //MODIFIES: this
    //EFFECTS: sets new dateTime from user input to a given task
    public void changeCalendar(Task task, String date, String hours, String minutes)
            throws DateTimeException, NumberFormatException {
        task.setDateTime(makeCalendarDateAndTime(date, hours, minutes));
    }

    //EFFECTS: compares the dates off two Calendar objects, returns true if dates are the same and false otherwise
    private boolean isSameDate(Calendar c1, Calendar c2) {
        return (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH))
                && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
                && (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR));
    }

    //REQUIRES: name is not empty string
    //MODIFIES: this
    //EFFECTS: adds task to the schedule, if the input is in requested format, otherwise shows error message
    public void addTask(String name, String desc, String date, String hours, String minutes) {
        try {
            schedule.addTask(new Task(makeCalendarDateAndTime(date, hours, minutes), name, desc));
            System.out.println("The task is added to the schedule");
        } catch (DateTimeException ex) {
            System.out.println("Invalid date format");
        }

    }

    //MODIFIES: this
    //EFFECTS: deletes task from the schedule if such task exists, otherwise shows error message
    public void deleteTask(String name) {
        Task taskToDelete = schedule.findTask(name);
        if (taskToDelete != null) {
            schedule.deleteTask(taskToDelete);
            System.out.println("\nThe task is deleted");
        } else {
            System.out.println("No task found");
        }

    }

    //EFFECTS: finds task by name and displays it, if task with such name does not exist, displays error message
    public void findTask(String name) {
        Task taskToFind = schedule.findTask(name);
        if (taskToFind != null) {
            displayTask(taskToFind);
        } else {
            System.out.println("No task found");
        }
    }

    //displays all tasks, if there are no tasks, displays "empty message"
    public void displayWholeSchedule() {
        if (schedule.getAllTasks().size() == 0) {
            System.out.println("Schedule is empty");
        } else {
            for (Task task : schedule.getAllTasks()) {
                displayTask(task);
            }
        }
    }

    //EFFECTS: displays all tasks which are arranged on a particular day, if there are no tasks on this day, displays
    // "empty message"
    public void displayDaySchedule(Calendar calendar) {
        boolean emptyFlag = true;
        for (Task task : schedule.getAllTasks()) {
            if (isSameDate(calendar, task.getDateTime())) {
                displayTask(task);
                emptyFlag = false;
            }
        }
        if (emptyFlag) {
            System.out.println("Schedule is empty");
        }
    }

    //EFFECTS: displays all done tasks, if there are no done tasks displays "empty" message
    public void displayDoneSchedule() {
        boolean emptyFlag = true;
        for (Task task : schedule.getAllTasks()) {
            if (task.getMark()) {
                displayTask(task);
                emptyFlag = false;
            }
        }
        if (emptyFlag) {
            System.out.println("Schedule is empty");
        }
    }

    //EFFECTS: displays all undone tasks, if there are no undone tasks displays "empty" message
    public void displayUndoneSchedule() {
        boolean emptyFlag = true;
        for (Task task : schedule.getAllTasks()) {
            if (!task.getMark()) {
                displayTask(task);
                emptyFlag = false;
            }
        }
        if (emptyFlag) {
            System.out.println("Schedule is empty");
        }
    }

    // EFFECTS: saves the schedule to file
    public void saveSchedule() {
        try {
            jsonWriter.open();
            jsonWriter.write(schedule);
            jsonWriter.close();
            System.out.println("Saved " + schedule.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads schedule from file
    public void loadSchedule() {
        try {
            schedule = jsonReader.read();
            System.out.println("Loaded " + schedule.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (ParseException e) {
            System.out.println("The format of date in JSON object is not valid");
        }
    }

    // EFFECTS: returns existing schedule
    public Schedule getSchedule() {
        return schedule;
    }
}

