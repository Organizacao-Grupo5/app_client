package app.integration;

import com.mysql.cj.util.StringUtils;
import util.logs.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Optional;

import static com.sun.jna.Platform.isWindows;

public class HardwareIntegration {
	private static Boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
	String directory = isWindows ? "/powershell" : "/bash";
	ProcessBuilder builder = new ProcessBuilder();

	public Double monitorarBateria() {
		String output = null;

		try {
			String command = "";
			if (!isWindows()) {
				String scriptPath = "app-client-api/src/scripts" + directory + "/power.sh";
				command = "sh " + scriptPath;
			} else {
				String scriptPath = "app-client-api/src/scripts" + directory + "/power.ps1";
				command = "powershell.exe -ExecutionPolicy Bypass -File " + scriptPath;
			}
			Process process = Runtime.getRuntime().exec(command);

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line).append("\n");
			}
			process.waitFor();

			output = stringBuilder.toString().trim();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		if (StringUtils.isNullOrEmpty(output)) {
			return null;
		} else {
			String outputString = output.replace(',', '.');
			return Double.parseDouble(outputString);
		}
	}

	public Double monitorarTemperatura() throws IOException, InterruptedException {
		Serializable output = null;
		String command = "";

		try {
			if (!isWindows()) {
				String scriptPath = "app-client-api/src/scripts" + directory + "/cpuTemperature.sh";
				command = "sh " + scriptPath;
			} else {
				String scriptPath = "app-client-api/src/scripts" + directory + "/cpuTemperature.ps1";
				command = "powershell.exe -ExecutionPolicy Bypass -File " + scriptPath;
			}

			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				if (!line.contains("Microsoft Corporation")) {
					stringBuilder.append(line).append("\n");
				}
			}
			output = stringBuilder.toString().trim();
		} catch (IOException | InterruptedException e) {
			Logger.logError("Não foi possível obter a temperatura:", e.getMessage(), e);
			return null;
		}

		if (StringUtils.isNullOrEmpty((String) output)) {
			return null;
		} else {
			String outputString = ((String) output).replace(',', '.');
			return Double.parseDouble(outputString);
		}
	}
}
