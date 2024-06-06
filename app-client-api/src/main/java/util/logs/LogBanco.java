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

    private static BufferedWriter bwMain;
    private static BufferedWriter bwMonitoramento;
    private static BufferedWriter bwBanco;
    private static File logFileMain;
    private static File logFileMonitoramento;
    private static File logFileBanco;

    private static final String LOG_DIRECTORY = "/home/diegosouza/Música/app_client/logs";

    static {
        try {
            createDirectories();
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
        if (bwMonitoramento != null) {
            bwMonitoramento.close();
            bwMonitoramento = null;
        }
        if (bwBanco != null) {
            bwBanco.close();
            bwBanco = null;
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
        createDirectories();

        BufferedWriter writer;
        String logFileName;
        File logFile;

        switch (logType) {
//            case MONITORAMENTO:
//                if (bwMonitoramento == null) {
//                    logFileName = "log_monitoramento_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt";
//                    logFileMonitoramento = new File(LOG_DIRECTORY + "/monitoramento", logFileName);
//
//                    if (!logFileMonitoramento.exists()) {
//                        logFileMonitoramento.createNewFile();
//                    }
//
//                    bwMonitoramento = new BufferedWriter(new FileWriter(logFileMonitoramento, true));
//                }
//                writer = bwMonitoramento;
//                break;

            case ERROR:
            case INFO:
            case WARNING:
            default:
                if (bwMain == null) {
                    logFileName = "log_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt";
                    logFileMain = new File(LOG_DIRECTORY + "/banco", logFileName);

                    if (!logFileMain.exists()) {
                        logFileMain.createNewFile();
                    }

                    bwMain = new BufferedWriter(new FileWriter(logFileMain, true));
                }
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
