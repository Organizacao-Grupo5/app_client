package util.logs;

import com.sun.jdi.connect.Connector;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	private static final String LOG_FOLDER_NAME = "logs";
	private static final String LOG_FILE_NAME = "app.log";

	public static String getLogPath() {
		String jarDir = new File(Connector.class.getProtectionDomain().getCodeSource().getLocation().getPath())
				.getParentFile().getPath();
		return jarDir + File.separator + LOG_FOLDER_NAME + File.separator + LOG_FILE_NAME;
	}

	static {
		File logFile = new File(LOG_FILE_NAME);
		if (logFile.exists()) {
			try (PrintWriter writer = new PrintWriter(LOG_FILE_NAME)) {
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

		try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE_NAME, true))) {
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

	public static String displayLogsInConsole() {
		try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE_NAME))) {
			String line;
			StringBuilder linhas = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				linhas.append(line);
				linhas.append("\n");
			}
			return linhas.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
