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

public class LogBanco {

    private static BufferedWriter bwMain;
    private static BufferedWriter bwMonitoramento;
    private static File logFileMain;
    private static File logFileMonitoramento;

    private static final String LOG_DIRECTORY = "logs"; // Diretório de logs relativo ao diretório de execução do aplicativo

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
        File logFile;

        switch (logType) {
//            case MONITORAMENTO:
//                if (bwMonitoramento == null) {
//                    logFile = getLogFile(LOG_DIRECTORY + "/monitoramento", "log_monitoramento_");
//                    bwMonitoramento = new BufferedWriter(new FileWriter(logFile, true));
//                }
//                writer = bwMonitoramento;
//                break;

            case ERROR:
            case INFO:
            case WARNING:
            default:
                if (bwMain == null) {
                    logFile = getLogFile(LOG_DIRECTORY + "/banco", "log_");
                    bwMain = new BufferedWriter(new FileWriter(logFile, true));
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

    private static File getLogFile(String directory, String prefix) throws IOException {
        createDirectories();
        String logFileName = prefix + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt";
        return new File(directory, logFileName);
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
