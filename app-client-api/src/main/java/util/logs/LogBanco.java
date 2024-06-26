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
import java.util.concurrent.TimeUnit;

public class LogBanco {

    private static BufferedWriter bwMain;
    private static File logFileMain;
    private static final String LOG_DIRECTORY = "logs";
    private static final long LOG_DURATION = TimeUnit.DAYS.toMillis(7);
    private static final String LOG_START_FILE = LOG_DIRECTORY + "/banco/log_start_time.txt";

    static {
        try {
            createDirectories();
            if (isLogExpired()) {
                openNewLogFile();
            } else {
                openExistingLogFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getLogFilePath() {
        return logFileMain != null ? logFileMain.getAbsolutePath() : null;
    }

    public static void closeLogFiles() throws IOException {
        if (bwMain != null) {
            bwMain.close();
            bwMain = null;
        }
    }

    public enum LogType {
        INFO, WARNING, ERROR, MONITORAMENTO
    }

    public static void logInfo(String message, LogType info) throws IOException {
        generateLog(message, LogType.INFO);
    }

    public static void logWarning(String message) throws IOException {
        generateLog(message, LogType.WARNING);
    }

    public static void logError(String message, String eMessage, Throwable throwable) throws IOException {
        generateLog(message + " - Exception: " + eMessage, LogType.ERROR);
    }

    public static void logError(String message, Exception e) throws IOException {
        String errorMessage = message + " - Exception: " + e.getMessage();
        generateLog(errorMessage, LogType.ERROR);
    }

    public static void logMonitoramento(String message) throws IOException {
        generateLog(message, LogType.MONITORAMENTO);
    }

    private static void generateLog(String message, LogType logType) throws IOException {
        if (isLogExpired()) {
            closeLogFiles();
            openNewLogFile();
        }

        BufferedWriter writer;
        File logFile;

        switch (logType) {
            case MONITORAMENTO:
                writer = bwMain;
                break;
            case ERROR:
            case INFO:
            case WARNING:
            default:
                writer = bwMain;
                break;
        }

        String logMessage = String.format("[%s] [%s] %s",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                logType, message);

        writer.write(logMessage);
        writer.newLine();
        writer.flush();
    }

    private static void openNewLogFile() throws IOException {
        logFileMain = getLogFile(LOG_DIRECTORY + "/banco", "log_banco");
        bwMain = new BufferedWriter(new FileWriter(logFileMain, true));
        saveLogStartTime();
    }

    private static void openExistingLogFile() throws IOException {
        String latestLogFileName = getLatestLogFileName(LOG_DIRECTORY + "/banco");
        if (latestLogFileName != null) {
            logFileMain = new File(LOG_DIRECTORY + "/banco", latestLogFileName);
            bwMain = new BufferedWriter(new FileWriter(logFileMain, true));
        } else {
            openNewLogFile();
        }
    }

    private static boolean isLogExpired() throws IOException {
        Path logStartPath = Paths.get(LOG_START_FILE);
        if (Files.exists(logStartPath)) {
            String startTimeStr = new String(Files.readAllBytes(logStartPath)).trim();
            long logStartTime = Long.parseLong(startTimeStr);
            return System.currentTimeMillis() - logStartTime > LOG_DURATION;
        }
        return true;
    }

    private static void saveLogStartTime() throws IOException {
        long logStartTime = System.currentTimeMillis();
        Files.write(Paths.get(LOG_START_FILE), String.valueOf(logStartTime).getBytes());
    }

    private static File getLogFile(String directory, String prefix) throws IOException {
        createDirectories();
        String logFileName = prefix + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt";
        return new File(directory, logFileName);
    }

    private static String getLatestLogFileName(String directory) throws IOException {
        File dir = new File(directory);
        File[] files = dir.listFiles((d, name) -> name.startsWith("log_") && name.endsWith(".txt"));
        if (files == null || files.length == 0) {
            return null;
        }
        File latestFile = files[0];
        for (File file : files) {
            if (file.lastModified() > latestFile.lastModified()) {
                latestFile = file;
            }
        }
        return latestFile.getName();
    }

    private static void createDirectories() throws IOException {
        Path path = Paths.get(LOG_DIRECTORY);

        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        Path pathMonitoramento = Paths.get(LOG_DIRECTORY, "monitoramento");
        if (!Files.exists(pathMonitoramento)) {
            Files.createDirectories(pathMonitoramento);
        }

        Path pathBanco = Paths.get(LOG_DIRECTORY, "banco");
        if (!Files.exists(pathBanco)) {
            Files.createDirectories(pathBanco);
        }
    }
}
