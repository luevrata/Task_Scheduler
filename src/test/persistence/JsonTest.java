package persistence;

import model.Task;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTask(Calendar dateTime, String name, String description, Boolean mark, Task task) {
        assertEquals(dateTime, task.getDateTime());
        assertEquals(name, task.getName());
        assertEquals(description, task.getDescription());
        assertEquals(mark, task.getMark());
    }
}
