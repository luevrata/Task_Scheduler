package ui;

import model.Schedule;
import model.Task;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;


//Task Scheduler Application
public class TaskSchedulerApp {
    private static final String JSON_STORE = "./data/schedule.json";
    private Schedule schedule;
    private Scanner input;
    private boolean keepGoing;
    private String command;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: runs Task Scheduler Application
    public TaskSchedulerApp() {
        runTaskScheduler();
    }

    //MODIFIES: this
    //EFFECTS: processes user input
    private void runTaskScheduler() {

        init();

//        while (keepGoing) {
//            displayMenu();
//            command = input.next();
//            processCommandMainMenu(command);
//        }
    }

    //MODIFIES: this
    //EFFECTS: initializes schedule
    private void init() {
        keepGoing = true;
        schedule = new Schedule("My Schedule");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    //MODIFIES: this
    //EFFECTS: processes user command (choice in main menu)
    private void processCommandMainMenu(String command) {
        switch (command) {
            case "0":
                keepGoing = false;
                break;
            case "1":
                displaySchedule();
                break;
            case "2":
                manageTasks();
                break;
            case "3":
                fileOptions();
                break;
            default:
                System.out.println("Selection is not valid...");
        }
    }

    //MODIFIES: this
    //EFFECTS: processes user command (choice in "edit task" menu)
    private void processCommandTaskMenu(String command, Task task) {
        switch (command) {
            case "0":
                keepGoing = false;
                break;
            case "1":
                changeName(task);
                break;
            case "2":
                changeDescription(task);
                break;
            case "3":
                task.changeMark();
                break;
            case "4":
                // todo
                // changeCalendar(task);
            default:
                System.out.println("Selection is not valid...");
        }
    }

    //MODIFIES: this
    //EFFECTS: processes user command (choice in "display schedule" menu)
    private void processCommandDisplaySchedule(String command) {
        switch (command) {
            case "0":
                keepGoing = false;
                break;
            case "1":
                displayWholeSchedule();
                break;
            case "2":
                // todo
                // displayDaySchedule(makeCalendarDate());
                break;
            case "3":
                displayDoneSchedule();
                break;
            case "4":
                displayUndoneSchedule();
                break;
            default:
                System.out.println("Selection is not valid...");
        }
    }

    //MODIFIES: this
    //EFFECTS: processes user command (choice in "Manage Tasks" menu)
    private void processCommandManageTasks(String command) {
        switch (command) {
            case "0":
                keepGoing = false;
                break;
            case "1":
                //todo
                //addTask();
                break;
            case "2":
                deleteTask();
                break;
            case "3":
                //todo
                //findTask();
                break;
            case "4":
                editTask();
                break;
            default:
                System.out.println("Selection is not valid...");
        }
    }

    //EFFECTS: processes user command (choice in "File Options" menu)
    private void processCommandFileOptions(String command) {
        switch (command) {
            case "0":
                keepGoing = false;
                break;
            case "1":
                saveSchedule();
                break;
            case "2":
                loadSchedule();
                break;
            default:
                System.out.println("Selection is not valid...");
        }
    }

    //EFFECTS: displays main menu options
    private void displayMenu() {
        System.out.println("\nSelect option:");
        System.out.println("\t1 -> Display schedule");
        System.out.println("\t2 -> Manage Tasks");
        System.out.println("\t3 -> File Options");
        System.out.println("\t0 -> quit");
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

    //EFFECTS: displays "edit task" menu options
    private void displayEditTaskMenu() {
        System.out.println("\nSelect option:");
        System.out.println("\t1 -> Change name");
        System.out.println("\t2 -> Change description");
        System.out.println("\t3 -> Change mark");
        System.out.println("\t4 -> Change date and time");
        System.out.println("\t0 -> quit");
    }

    //EFFECTS: displays "display schedule" menu options
    private void displayScheduleMenu() {
        System.out.println("\nSelect option:");
        System.out.println("\t1 -> Display whole schedule");
        System.out.println("\t2 -> Display day schedule");
        System.out.println("\t3 -> Display DONE tasks");
        System.out.println("\t4 -> Display UNDONE tasks");
        System.out.println("\t0 -> quit");
    }

    //EFFECTS: displays "Manage tasks" menu options
    private void displayManageTasksMenu() {
        System.out.println("\nSelect option:");
        System.out.println("\t1 -> Add task");
        System.out.println("\t2 -> Delete task");
        System.out.println("\t3 -> Find task");
        System.out.println("\t4 -> Edit task");
        System.out.println("\t0 -> quit");
    }

    //EFFECTS: displays "File options" menu
    private void displayFileOptionsMenu() {
        System.out.println("\nSelect option:");
        System.out.println("\t1 -> Save schedule to a file");
        System.out.println("\t2 -> Load Schedule from a file");
        System.out.println("\t0 -> quit");
    }

    //REQUIRES: user input is in requested format and with existing date
    //EFFECTS: creates Calendar.Builder with a date from user input and returns it
    private Calendar.Builder makeDate(String strDate) throws DateTimeException {
        LocalDate date;
        Calendar.Builder calendarBuilder;

        //todo
        //System.out.println("Enter Date (DDMMYYYY): ");
        //strDate = input.next();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy", Locale.ENGLISH);
        date = LocalDate.parse(strDate, formatter);
        calendarBuilder = new Calendar.Builder();
        calendarBuilder.setDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        return calendarBuilder;

    }

    //REQUIRES: user input is in requested format and with existing date
    //EFFECTS: creates a calendar with a date from user input and returns it
    public Calendar makeCalendarDate(String strDate) throws DateTimeException {
        Calendar calendar;
        calendar = makeDate(strDate).build();
        return calendar;
    }

    //REQUIRES: user input is in requested format and with existing date and time
    //EFFECTS: creates a calendar with a date and time from user input and returns it
    private Calendar makeCalendarDateAndTime(String strDate, String strHours, String strMinutes)
            throws DateTimeException {
        Calendar.Builder calendarBuilder = makeDate(strDate);
//        String strHours;
//        String strMinutes;
        Calendar calendar;
//TODO DELETE

//        System.out.println("Enter hours (HH): ");
//        strHours = input.next();
        int hours = Integer.parseInt(strHours);

//        System.out.println("Enter minutes (MM): ");
//        strMinutes = input.next();
        int minutes = Integer.parseInt(strMinutes);

        calendarBuilder.setTimeOfDay(hours, minutes, 0);

        calendar = calendarBuilder.build();
        return calendar;
    }

    //EFFECTS: creates a task from user input and returns it
    private Task makeTask(String name, String desc, String date, String hours, String minutes)
            throws DateTimeException {
        return new Task(makeCalendarDateAndTime(date, hours, minutes), name, desc);
    }

    //REQUIRES: newName has non-zero length
    //MODIFIES: this
    //EFFECTS: sets new name from user input to a given task
    private void changeName(Task task) {
        System.out.println("Enter new name: ");
        String newName = input.next();
        task.setName(newName);
    }

    //REQUIRES: newDescription has non-zero length
    //MODIFIES: this
    //EFFECTS: sets new description from user input to a given task
    private void changeDescription(Task task) {
        System.out.println("Enter new description: ");
        String newDescription = input.next();
        task.setDescription(newDescription);
    }

    //MODIFIES: this
    //EFFECTS: sets new dateTime from user input to a given task
    //todo
//    private void changeCalendar(Task task) {
//        System.out.print("Enter new date and time: ");
//        task.setDateTime(makeCalendarDateAndTime());
//    }

    //REQUIRES: input.next() has non-zero length
    //EFFECTS: creates string name from user input and returns it
    //todo do not need
    private String makeName() {
        System.out.println("Enter name: ");
        return input.next();
    }

    //REQUIRES: input.next() has non-zero length
    //EFFECTS: creates string description from user input and returns it
    private String makeDescription() {
        System.out.println("Enter description: ");
        return input.next();
    }


    //EFFECTS: compares the dates off two Calendar objects, returns true if dates are the same and false otherwise
    private boolean isSameDate(Calendar c1, Calendar c2) {
        return (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH))
                && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
                && (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR));
    }

    //MODIFIES: this
    //EFFECTS: processes "Edit Task" menu option
    private void editTask() {
        System.out.println("\nEnter the name of the task you want to edit");
        Task taskToEdit = schedule.findTask(makeName());
        if (taskToEdit != null) {
            displayTask(taskToEdit);
            displayEditTaskMenu();

            command = input.next();
            processCommandTaskMenu(command, taskToEdit);

            System.out.println("\nChanges applied");
        } else {
            System.out.println("No task found");
        }
    }

    //MODIFIES: this
    //EFFECTS: processes "File Options" menu option
    private void fileOptions() {
        displayFileOptionsMenu();
        command = input.next();
        processCommandFileOptions(command);
    }

    //EFFECTS: processes "Display Schedule" menu option
    private void displaySchedule() {
        displayScheduleMenu();
        command = input.next();
        processCommandDisplaySchedule(command);
    }

    //MODIFIES: this
    //EFFECTS: processes "Manage Tasks" menu option
    private void manageTasks() {
        displayManageTasksMenu();
        command = input.next();
        processCommandManageTasks(command);
    }

    //MODIFIES: this
    //EFFECTS: processes "Add Task" menu option
    public void addTask(String name, String desc, String date, String hours, String minutes) {
        try {
            schedule.addTask(makeTask(name, desc, date, hours, minutes));
            System.out.println("The task is added to the schedule");
        } catch (DateTimeException ex) {
            System.out.println("Invalid date/time format");
        }

    }

    //MODIFIES: this
    //EFFECTS: processes "Delete Task" menu option
    private void deleteTask() {
        System.out.println("\nEnter the name of the task you want to delete");
        Task taskToDelete = schedule.findTask(makeName());
        if (taskToDelete != null) {
            schedule.deleteTask(taskToDelete);
            System.out.println("\nThe task is deleted");
        } else {
            System.out.println("No task found");
        }

    }

    //EFFECTS: processes "Find Task" menu option
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
    private void saveSchedule() {
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
    private void loadSchedule() {
        try {
            schedule = jsonReader.read();
            System.out.println("Loaded " + schedule.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (ParseException e) {
            System.out.println("The format of date in JSON object is not valid");
        }
    }
}