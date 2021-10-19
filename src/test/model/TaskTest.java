package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    Task task;
    Calendar calendar;
    String newName;
    String newDescription;
    Boolean mark;

    @BeforeEach
    void setUp() {
        task = new Task();
        calendar = Calendar.getInstance();
        mark = true;
        newName = "Do that";
        newDescription = "Do that properly";
    }

    @Test
    void testConstructorMarkIsSetToFalse() {
        task = new Task(calendar, newName, newDescription);
        assertEquals(calendar,task.getDateTime());
        assertEquals(newName, task.getName());
        assertEquals(newDescription,task.getDescription());
        assertFalse(task.getMark());
    }

    @Test
    void testConstructorMarkIsSetByParameter() {
        task = new Task(calendar, newName, newDescription, mark);
        assertEquals(calendar,task.getDateTime());
        assertEquals(newName, task.getName());
        assertEquals(newDescription,task.getDescription());
        assertTrue(task.getMark());
    }

    @Test
    void testChangeMarkFalseToTrue() {
        assertFalse(task.getMark());
        task.changeMark();
        assertTrue(task.getMark());
    }

    @Test
    void testChangeMarkTrueToFalse() {
        task.setMark(true);
        assertTrue(task.getMark());
        task.changeMark();
        assertFalse(task.getMark());
    }

    @Test
    void testSetMark() {
        task.setMark(true);
        assertTrue(task.getMark());
        task.setMark(false);
        assertFalse(task.getMark());
    }

    @Test
    void testSetDateTime() {
        task.setDateTime(calendar);
        assertEquals(calendar,task.getDateTime());
    }

    @Test
    void testSetName() {
        task.setName(newName);
        assertEquals(newName,task.getName());
    }

    @Test
    void testSetDescription() {
        task.setDescription(newDescription);
        assertEquals(newDescription,task.getDescription());
    }

}
