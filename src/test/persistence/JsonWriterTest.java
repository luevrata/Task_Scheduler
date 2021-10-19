package persistence;

import model.Schedule;
import model.Task;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest{
    //TODO NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Schedule s = new Schedule("My schedule");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptySchedule() {
        try {
            Schedule s = new Schedule("My schedule");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySchedule.json");
            writer.open();
            writer.write(s);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySchedule.json");
            s = reader.read();
            assertEquals("My schedule", s.getName());
            assertEquals(0, s.numTasks());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralSchedule() {
        try {
            Calendar.Builder calendarBuilder = new Calendar.Builder();
            Calendar calendar1;
            Calendar calendar2;

            Schedule s = new Schedule("My schedule");
            calendarBuilder.setDate(2003,7,16);
            calendar1 = calendarBuilder.build();
            calendarBuilder.setDate(2002,7,20);
            calendar2 = calendarBuilder.build();

            s.addTask(new Task(calendar1, "work", "hard", true));
            s.addTask(new Task(calendar2, "bake", "tasty", false));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralSchedule.json");
            writer.open();
            writer.write(s);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralSchedule.json");
            s = reader.read();
            assertEquals("My schedule", s.getName());
            List<Task> tasks = s.getAllTasks();
            assertEquals(2, tasks.size());
            checkTask(calendar1, "work", "hard", true, tasks.get(1));
            checkTask(calendar2, "bake", "tasty", false, tasks.get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
