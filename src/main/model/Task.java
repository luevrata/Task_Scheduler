package model;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.json.JSONObject;
import persistence.Writable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

//Represents a task having a date, time, name, description, and DONE or UNDONE mark
//Implements Comparable interface in order to be compared by date and time
public class Task implements Comparable<Task>, Writable {
    private Calendar dateTime;
    private String name;
    private String description;
    private boolean mark;

    //EFFECTS: Calendar is set to 01.01.1970 00:00:00, name and description are set to empty string,
    // mark is set to false
    public Task() {
        dateTime = Calendar.getInstance();
        name = "";
        description = "";
        mark = false;
    }

    //REQUIRES: name has non-zero length
    //EFFECTS: name of task is set to name, description of task is set to description, dateTime is set to dateTime,
    //mark is set to false
    public Task(Calendar dateTime, String name, String description) {
        this.dateTime = dateTime;
        this.name = name;
        this.description = description;
        mark = false;
    }

    //REQUIRES: name has non-zero length
    //EFFECTS: name of task is set to name, description of task is set to description, dateTime is set to dateTime,
    //mark is set to mark
    public Task(Calendar dateTime, String name, String description, Boolean mark) {
        this(dateTime, name, description);
        setMark(mark);
    }

    public void setDateTime(Calendar calendar) {
        this.dateTime = calendar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    //MODIFIES: this
    //EFFECTS: if mark was false sets it true and otherwise
    public void changeMark() {
        mark = !mark;
    }

    public Calendar getDateTime() {
        return dateTime;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean getMark() {
        return mark;
    }

    //EFFECTS: returns 0 if tasks are arranged at the same date and time
    // returns 1 if compareTask is earlier
    // returns -1 if compareTask is later
    @Override
    public int compareTo(Task compareTask) {
        return this.dateTime.compareTo(compareTask.dateTime);
    }

    //TODO ask if these methods need to be tested (also in Schedule class)
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        SimpleDateFormat jsonCalendarFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sendDateTime = jsonCalendarFormat.format(dateTime.getTime());
        String sendMark = String.valueOf(mark);
        json.put("date and time", sendDateTime);
        json.put("name", name);
        json.put("description", description);
        json.put("mark", sendMark);
        return json;
    }
}
