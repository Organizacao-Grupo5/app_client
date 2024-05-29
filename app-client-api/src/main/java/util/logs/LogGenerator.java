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
    private static BufferedWriter bwAutenticar;
    private static File logFileMain;
    private static File logFileAutenticar;

    private static final String LOG_DIRECTORY = "/home/diegosouza/Downloads/app_client/logs";

    public static String getLogFilePath() {
        return logFileMain != null ? logFileMain.getAbsolutePath() : null;
    }

    public static void closeLogFile() {
    }

    public enum LogType {
        INFO, WARNING, ERROR
    }

    public static void logInfo(String message, LogType info) throws IOException {
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
        if (bwMain == null) {
            Path path = Paths.get(LOG_DIRECTORY);

            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            String logFileNameMain = "log_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt";
            logFileMain = new File(path.toString(), logFileNameMain);

            if (!logFileMain.exists()) {
                logFileMain.createNewFile();
            }

            bwMain = new BufferedWriter(new FileWriter(logFileMain, true));
        }

        // Cria o arquivo para logs de autenticação se não existir
        if (bwAutenticar == null) {
            Path pathAutenticar = Paths.get(LOG_DIRECTORY, "autenticar");

            if (!Files.exists(pathAutenticar)) {
                Files.createDirectories(pathAutenticar);
            }

            String logFileNameAutenticar = "log_autenticar_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt";
            logFileAutenticar = new File(pathAutenticar.toString(), logFileNameAutenticar);

            if (!logFileAutenticar.exists()) {
                logFileAutenticar.createNewFile();
            }

            bwAutenticar = new BufferedWriter(new FileWriter(logFileAutenticar, true));
        }

        String logMessage = String.format("[%s] [%s] %s",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                logType, message);

        // Escreve no arquivo principal de log
        bwMain.write(logMessage);
        bwMain.newLine();
        bwMain.flush();

        // Se for log de autenticação, escreve no arquivo separado
        if (message.contains("Usuário logado com sucesso")) {
            bwAutenticar.write(logMessage);
            bwAutenticar.newLine();
            bwAutenticar.flush();
        }
    }

    public static void closeLogFiles() throws IOException {
        if (bwMain != null) {
            bwMain.close();
            bwMain = null;
        }
        if (bwAutenticar != null) {
            bwAutenticar.close();
            bwAutenticar = null;
        }
    }

    public static void moveLogFileToAutenticar() throws IOException {
        if (logFileAutenticar != null && logFileAutenticar.exists()) {
            Path sourcePath = logFileAutenticar.toPath();
            Path destinationDir = Paths.get(LOG_DIRECTORY, "autenticar_final");

            if (!Files.exists(destinationDir)) {
                Files.createDirectories(destinationDir);
            }

            Path destinationPath = Paths.get(destinationDir.toString(), logFileAutenticar.getName());

            Files.move(sourcePath, destinationPath);
        }
    }
}
