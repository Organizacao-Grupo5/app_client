package util.logs;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static final String LOG_DIRECTORY = "logs/";
    private static final String LOG_FILE = LOG_DIRECTORY + "app.log";

    static {
        File logFile = new File(LOG_FILE);
        if (logFile.exists()) {
            try (PrintWriter writer = new PrintWriter(LOG_FILE)) {
                writer.print("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void logInfo(String message) {
        log("INFO", message);
    }

    public static void logWarning(String message) {
        log("WARNING", message);
    }

    public static void logError(String message, String eMessage, Throwable throwable) {
        String fullMessage = message + "\n" + getStackTraceAsString(throwable);
        log("ERROR", fullMessage);
    }

    private static void log(String level, String message) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String formattedMessage = String.format("[%s] %s: %s", timeStamp, level, message);

        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println(formattedMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getStackTraceAsString(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }

    public static void displayLogsInConsole() {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
