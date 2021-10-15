package ui;

import model.Scheduler;
import model.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;


//Task Scheduler Application
public class TaskSchedulerApp {
    private Scheduler schedule;
    private Scanner input;
    private boolean keepGoing;
    private int command;

    //EFFECTS: runs Task Scheduler Application
    public TaskSchedulerApp() {
        runTaskScheduler();
    }

    //MODIFIES: this
    //EFFECTS: processes user input
    private void runTaskScheduler() {

        init();

        while (keepGoing) {
            displayMenu();
            command = input.nextInt();
            processCommandMainMenu(command);
        }
        init();
    }

    //MODIFIES: this
    //EFFECTS: initializes schedule
    private void init() {
        keepGoing = true;
        schedule = new Scheduler();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    //MODIFIES: this
    //EFFECTS: processes user command (choice in main menu)
    private void processCommandMainMenu(Integer command) {
        switch (command) {
            case 0:
                keepGoing = false;
                break;
            case 1:
                displaySchedule();
                break;
            case 2:
                addTask();
                break;
            case 3:
                deleteTask();
                break;
            case 4:
                editTask();
                break;
            default:
                System.out.println("Selection is not valid...");
        }
    }

    //MODIFIES: this
    //EFFECTS: processes user command (choice in "edit task" menu)
    private void processCommandTaskMenu(Integer command, Task task) {
        switch (command) {
            case 0:
                keepGoing = false;
                break;
            case 1:
                changeName(task);
                break;
            case 2:
                changeDescription(task);
                break;
            case 3:
                task.changeMark();
                break;
            case 4:
                changeCalendar(task);
            default:
                System.out.println("Selection is not valid...");
        }
    }

    //MODIFIES: this
    //EFFECTS: processes user command (choice in "display schedule" menu)
    private void processCommandDisplaySchedule(Integer command) {
        switch (command) {
            case 0:
                keepGoing = false;
                break;
            case 1:
                displayWholeSchedule();
                break;
            case 2:
                displayDaySchedule(makeCalendarDate());
                break;
            case 3:
                displayDoneSchedule();
                break;
            case 4:
                findTask();
                break;
            case 5:
                displayUndoneSchedule();
                break;
            default:
                System.out.println("Selection is not valid...");
        }
    }

    //EFFECTS: displays main menu options
    private void displayMenu() {
        System.out.println("\nSelect option:");
        System.out.println("\t1 -> Display schedule");
        System.out.println("\t2 -> Add Task");
        System.out.println("\t3 -> Delete Task");
        System.out.println("\t4 -> Find task");
        System.out.println("\t5 -> Edit task");
        System.out.println("\t0 -> quit");
    }

    //EFFECTS: displays information of a particular task
    private void displayTask(Task task) {
        System.out.println("\nName: " + task.getName());
        System.out.println("Description: " + task.getDescription());
        System.out.println("Date and time: " + task.getDateTime().getTime());
        System.out.println("Done: " + task.getMark());
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

    //REQUIRES: user input is in requested format and with existing date
    //EFFECTS: creates Calendar.Builder with a date from user input and returns it
    private Calendar.Builder makeDate() {
        LocalDate date;
        Calendar.Builder calendarBuilder;
        String strDate;

        System.out.println("Enter Date (DDMMYYYY): ");
        strDate = input.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy", Locale.ENGLISH);
        date = LocalDate.parse(strDate, formatter);

        calendarBuilder = new Calendar.Builder();
        calendarBuilder.setDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth());

        return calendarBuilder;
    }

    //REQUIRES: user input is in requested format and with existing date
    //EFFECTS: creates a calendar with a date from user input and returns it
    private Calendar makeCalendarDate() {
        Calendar calendar;
        calendar = makeDate().build();
        return calendar;
    }

    //REQUIRES: user input is in requested format and with existing date and time
    //EFFECTS: creates a calendar with a date and time from user input and returns it
    private Calendar makeCalendarDateAndTime() {
        int hours;
        int minutes;

        Calendar.Builder calendarBuilder = makeDate();
        String strHours;
        String strMinutes;
        Calendar calendar;

        System.out.println("Enter hours (HH): ");
        strHours = input.next();
        hours = Integer.parseInt(strHours);

        System.out.println("Enter minutes (MM): ");
        strMinutes = input.next();
        minutes = Integer.parseInt(strMinutes);

        calendarBuilder.setTimeOfDay(hours, minutes, 0);

        calendar = calendarBuilder.build();
        return calendar;
    }

    //EFFECTS: creates a task from user input and returns it
    private Task makeTask() {
        return new Task(makeCalendarDateAndTime(), makeName(), makeDescription());
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
    private void changeCalendar(Task task) {
        System.out.print("Enter new date and time: ");
        task.setDateTime(makeCalendarDateAndTime());
    }

    //REQUIRES: input.next() has non-zero length
    //EFFECTS: creates string name from user input and returns it
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

            command = input.nextInt();
            processCommandTaskMenu(command, taskToEdit);

            System.out.println("\nChanges applied");
        } else {
            System.out.println("No task found");
        }
    }


    //EFFECTS: processes "Display Schedule" menu option
    private void displaySchedule() {
        displayScheduleMenu();
        command = input.nextInt();
        processCommandDisplaySchedule(command);
    }

    //MODIFIES: this
    //EFFECTS: processes "Add Task" menu option
    private void addTask() {
        System.out.println("To add task enter the information below");
        schedule.addTask(makeTask());
        System.out.println("The task is added to the schedule");
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
    private void findTask() {
        System.out.println("\nEnter the name of the task you want to find");
        Task taskToFind = schedule.findTask(makeName());
        if (taskToFind != null) {
            displayTask(taskToFind);
        } else {
            System.out.println("No task found");
        }

    }

    //displays all tasks, if there are no tasks, displays "empty message"
    private void displayWholeSchedule() {
        System.out.println("\nWhole schedule");
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
    private void displayDaySchedule(Calendar calendar) {
        System.out.println("\nDay schedule");
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
    private void displayDoneSchedule() {
        System.out.println("\nDONE schedule");
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
    private void displayUndoneSchedule() {
        System.out.println("\nUNDONE schedule");
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
}