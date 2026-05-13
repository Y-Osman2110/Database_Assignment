package src;

import java.io.OutputStream;
import java.io.PrintStream;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class ConsoleRedirector {
    public static void redirect(TextArea textArea) {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) {
                Platform.runLater(() -> textArea.appendText(String.valueOf((char) b)));
            }

            @Override
            public void write(byte[] b, int off, int len) {
                final String str = new String(b, off, len);
                Platform.runLater(() -> textArea.appendText(str));
            }
        };
        PrintStream customOut = new PrintStream(out);
        System.setOut(customOut);
        // Also redirect System.err to show error messages
        System.setErr(customOut);
    }
}