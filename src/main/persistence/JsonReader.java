package persistence;

import model.Schedule;
import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.stream.Stream;


// Represents a reader that reads schedule from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Schedule read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSchedule(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses schedule from JSON object and returns it
    private Schedule parseSchedule(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Schedule s = new Schedule(name);
        addTasks(s, jsonObject);
        return s;
    }

    // MODIFIES: s
    // EFFECTS: parses allTasks from JSON object and adds them to Schedule
    private void addTasks(Schedule s, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tasks");
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            addTask(s, nextTask);
        }
    }

    // MODIFIES: s
    // EFFECTS: parses task from JSON object and adds it to schedule
    private void addTask(Schedule s, JSONObject jsonObject) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //TODO ask how these lines need to be tested
        try {
            calendar.setTime(simpleDateFormat.parse(jsonObject.getString("date and time")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        Boolean mark =  Boolean.parseBoolean(jsonObject.getString("mark"));
        Task task = new Task(calendar, name, description, mark);
        s.addTask(task);
    }
}
