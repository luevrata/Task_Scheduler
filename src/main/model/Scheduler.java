package model;

//Represents a schedule which has a set of tasks
import java.util.ArrayList;
import java.util.Collections;

public class Scheduler {
    private ArrayList<Task> allTasks;

    //EFFECTS: declares a new empty array list
    public Scheduler() {
        allTasks = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds task to a particular place in allTasks by its date and time
    public void addTask(Task task) {
        allTasks.add(task);
        Collections.sort(allTasks);
    }

    //MODIFIES: this
    //EFFECTS: deletes task from allTasks
    public void deleteTask(Task task) {
        allTasks.remove(task);
    }

    //EFFECTS: finds a task in the array by cname to existing tasks and returns the task from array that
    // matches, returns null if there is no task with this name
    public Task findTask(String name) {
        name = name.toLowerCase();
        for (Task t: allTasks) {
            String lowerCaseName = t.getName().toLowerCase();
            if (name.equals(lowerCaseName)) {
                return t;
            }
        }
        return null;
    }

    public ArrayList<Task> getAllTasks() {
        return allTasks;//stub
    }

    //MODIFIES: this
    //EFFECTS: sets allTasks to tasks and sorts allTasks by date
    public void setAllTasks(ArrayList<Task> tasks) {
        allTasks = tasks;
        Collections.sort(allTasks);
    }
}