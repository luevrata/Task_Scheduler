package persistence;

import model.Schedule;
import model.Task;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest{
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Schedule s = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptySchedule() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySchedule.json");
        try {
            Schedule s = reader.read();
            assertEquals("My schedule", s.getName());
            assertEquals(0, s.numTasks());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralSchedule() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralSchedule.json");
        try {
            Schedule s = reader.read();
            assertEquals("My schedule", s.getName());
            List<Task> tasks = s.getAllTasks();
            assertEquals(2, tasks.size());

            Calendar.Builder calendarBuilder = new Calendar.Builder();
            Calendar calendar1;
            Calendar calendar2;
            calendarBuilder.setDate(2003,7,16);
            calendar1 = calendarBuilder.build();
            calendarBuilder.setDate(2002,7,20);
            calendar2 = calendarBuilder.build();

            checkTask(calendar1, "work", "hard", true, tasks.get(1));
            checkTask(calendar2, "bake", "tasty", false, tasks.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
