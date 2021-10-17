package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class SchedulerTest {
    Scheduler schedule;
    Task task1;
    Task task2;
    Task task3;
    Calendar.Builder calendarBuilder;
    Calendar calendar1;
    Calendar calendar2;
    Calendar calendar3;
    ArrayList<Task> tasks;

    @BeforeEach
    void setUp() {
        schedule = new Scheduler("Someone's schedule");
        calendarBuilder = new Calendar.Builder();

        task1 = new Task();
        task2 = new Task();
        task3 = new Task();

        schedule.addTask(task1);

        tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
    }

    @Test
    void testAddTask() {
        calendarBuilder.setDate(2003,7,16);
        calendar1 = calendarBuilder.build();
        calendarBuilder.setDate(2002,7,16);
        calendar2 = calendarBuilder.build();
        task1.setDateTime(calendar1);
        task2.setDateTime(calendar2);
        schedule.addTask(task2);
        assertEquals(2, schedule.getAllTasks().size());
        assertEquals(task2, schedule.getAllTasks().get(0));
    }

    @Test
    void testDeleteTask() {
        schedule.addTask(task2);
        schedule.deleteTask(task1);
        assertEquals(1, schedule.getAllTasks().size());
        assertEquals(task2, schedule.getAllTasks().get(0));
    }

    @Test
    void testFindTaskThatExists() {
        schedule.addTask(task2);
        task2.setName("Homework");
        assertEquals(task2,schedule.findTask("Homework"));
    }

    @Test
    void testFindTaskThatDoesNotExist() {
        schedule.addTask(task2);
        task2.setName("Homework");
        assertNull(schedule.findTask("Laboratory"));
    }

    @Test
    void testSetAllTasks() {
        calendarBuilder.setDate(2005,7,16);
        calendar3 = calendarBuilder.build();
        calendarBuilder.setDate(2003,7,16);
        calendar1 = calendarBuilder.build();
        calendarBuilder.setDate(2002,7,16);
        calendar2 = calendarBuilder.build();

        task1.setDateTime(calendar1);
        task2.setDateTime(calendar2);
        task3.setDateTime(calendar3);
        schedule.setAllTasks(tasks);
        assertEquals(3,schedule.getAllTasks().size());
        assertEquals(task2, schedule.getAllTasks().get(0));
        assertEquals(task1, schedule.getAllTasks().get(1));
        assertEquals(task3, schedule.getAllTasks().get(2));
    }

    @Test
    void testSetName() {
        schedule.setName("Another name");
        assertEquals("Another name", schedule.getName());
    }
}