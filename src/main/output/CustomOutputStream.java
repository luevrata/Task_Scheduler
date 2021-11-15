package output;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

//OutputStream class
public class CustomOutputStream extends OutputStream {

    private final JTextArea textArea;

    //MODIFIES: this
    //EFFECTS: Assigns textArea
    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    //EFFECTS: redirects data to the text area and scrolls the text area to the end of data
    @Override
    public void write(int b) {
        textArea.append(String.valueOf((char)b));
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    //EFFECTS: returns textArea
    public JTextArea getTextArea() {
        return textArea;
    }
}
