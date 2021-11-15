package output;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomOutputStreamTest {

    JTextArea  textArea;
    CustomOutputStream outputStream;
    PrintStream printStream;

    @BeforeEach
    void setUp() {
        textArea = new JTextArea();
        outputStream = new CustomOutputStream(textArea);
        printStream = new PrintStream(new CustomOutputStream(textArea));
        System.setOut(printStream);
        System.setErr(printStream);
    }

    @Test
    void constructorTest() {
        assertEquals(textArea, outputStream.getTextArea());
    }

    @Test
    void writeTest() {
        System.out.println("hello");
        assertEquals("hello", textArea.getText().trim());
    }
}
