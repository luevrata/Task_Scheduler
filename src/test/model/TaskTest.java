package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    Task task1;
    Task task2;
    Task task3;
    Calendar calendar1;
    Calendar calendar2;
    Calendar.Builder calendarBuilder;
    String newName;
    String newDescription;
    Boolean mark;

    @BeforeEach
    void setUp() {
        task1 = new Task();
        task2 = new Task();
        task3 = new Task();
        calendarBuilder = new Calendar.Builder();
        mark = true;
        newName = "Do that";
        newDescription = "Do that properly";
        calendarBuilder.setDate(2003,7,16);
        calendar1 = calendarBuilder.build();
        calendarBuilder.setDate(2002,7,16);
        calendar2 = calendarBuilder.build();
    }

    @Test
    void testConstructorMarkIsSetToFalse() {
        task1 = new Task(calendar1, newName, newDescription);
        assertEquals(calendar1, task1.getDateTime());
        assertEquals(newName, task1.getName());
        assertEquals(newDescription, task1.getDescription());
        assertFalse(task1.getMark());
    }

    @Test
    void testConstructorMarkIsSetByParameter() {
        task1 = new Task(calendar1, newName, newDescription, mark);
        assertEquals(calendar1, task1.getDateTime());
        assertEquals(newName, task1.getName());
        assertEquals(newDescription, task1.getDescription());
        assertTrue(task1.getMark());
    }

    @Test
    void testChangeMarkFalseToTrue() {
        assertFalse(task1.getMark());
        task1.changeMark();
        assertTrue(task1.getMark());
    }

    @Test
    void testChangeMarkTrueToFalse() {
        task1.setMark(true);
        assertTrue(task1.getMark());
        task1.changeMark();
        assertFalse(task1.getMark());
    }

    @Test
    void testSetMark() {
        task1.setMark(true);
        assertTrue(task1.getMark());
        task1.setMark(false);
        assertFalse(task1.getMark());
    }

    @Test
    void testSetDateTime() {
        task1.setDateTime(calendar1);
        assertEquals(calendar1, task1.getDateTime());
    }

    @Test
    void testSetName() {
        task1.setName(newName);
        assertEquals(newName, task1.getName());
    }

    @Test
    void testSetDescription() {
        task1.setDescription(newDescription);
        assertEquals(newDescription, task1.getDescription());
    }

    @Test
    void testCompareTo() {
        task1.setDateTime(calendar1);
        task2.setDateTime(calendar2);
        task3.setDateTime(calendar2);
        assertEquals(-1, task2.compareTo(task1));
        assertEquals(1, task1.compareTo(task2));
        assertEquals(0, task3.compareTo(task2));
    }

}
