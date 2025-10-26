package se411;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final String LOG_FILE = "application.log";
    private static PrintWriter writer;
    
    static {
        try {
            writer = new PrintWriter(new FileWriter(LOG_FILE, true));
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }
    
    private static void log(String level, String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logEntry = String.format("[%s] %s: %s", timestamp, level, message);
        
        if (writer != null) {
            writer.println(logEntry);
            writer.flush();
        }
        
        System.out.println(logEntry);
    }
    
    public static void error(String message) {
        log("ERROR", message);
    }
    
    public static void warn(String message) {
        log("WARNING", message);
    }
    
    public static void info(String message) {
        log("INFO", message);
    }
    
    public static void close() {
        if (writer != null) {
            writer.close();
        }
    }
}

