package util.logs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogGenerator {

    private static BufferedWriter bw;
    private static File logFile;
    private static final String BASE_LOG_PATH = "/home/diegosouza/Downloads/app_client/logs";

    public enum LogType {
        INFO, WARNING, ERROR
    }

    public static void logInfo(String message) throws IOException {
        generateLog(message, LogType.INFO);
    }

    public static void logWarning(String message) throws IOException {
        generateLog(message, LogType.WARNING);
    }

    public static void logError(String message, String eMessage, Throwable throwable) throws IOException {
        generateLog(message, LogType.ERROR);
    }

    public static void logError(String message, Exception e) throws IOException {
        String errorMessage = message + " - Exception: " + e.getMessage();
        generateLog(errorMessage, LogType.ERROR);
    }

    private static void generateLog(String message, LogType logType) throws IOException {
        Path path = Paths.get(BASE_LOG_PATH);

        if (!Files.exists(path)) {
            Files.createDirectories(path);
            createSubDirectories(path);
        }

        Path logFilePath = getLogFilePath(logType);
        logFile = logFilePath.toFile();

        if (!logFile.exists()) {
            logFile.createNewFile();
        }

        bw = new BufferedWriter(new FileWriter(logFile, true));

        String logMessage = String.format("[%s] [%s] %s",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                logType, message);
        bw.write(logMessage);
        bw.newLine();
        bw.flush();
        bw.close();
    }

    private static void createSubDirectories(Path basePath) throws IOException {
        Files.createDirectories(basePath.resolve("banco"));
        Files.createDirectories(basePath.resolve("autenticar"));
        Files.createDirectories(basePath.resolve("monitoramento"));
    }

    private static Path getLogFilePath(LogType logType) {
        String subDir;
        switch (logType) {
            case INFO:
                subDir = "autenticar";
                break;
            case WARNING:
                subDir = "monitoramento";
                break;
            case ERROR:
                subDir = "banco";
                break;
            default:
                throw new IllegalArgumentException("Invalid log type");
        }
        String logFileName = logType.name().toLowerCase() + "_log_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt";
        return Paths.get(BASE_LOG_PATH, subDir, logFileName);
    }

    public static void closeLogFile() throws IOException {
        if (bw != null) {
            bw.close();
            bw = null;
        }
    }
}
