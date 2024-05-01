package app.integration;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.sun.jna.Platform.isWindows;

public class Userinfo {
	private static Boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
	String directory = isWindows ? "/scripts/powershell" : "/scripts/bash";
	ProcessBuilder processBuilder = new ProcessBuilder();

	public String userName() {
		try {

			String command = "";
			if (!isWindows()) {
				String scriptPath = "app-client-api/src/scripts" + directory + "/userInfo.sh";
				command = "sh " + scriptPath;
			} else {
				String scriptPath = "app-client-api/src/scripts" + directory + "/userInfo.ps1";

				command = "powershell.exe -ExecutionPolicy Bypass -File " + scriptPath;
			}

			processBuilder.command(splitCommand(command));

			Process process = processBuilder.start();

			int output = process.waitFor();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			StringBuilder stringBuilder = new StringBuilder();

			String line;

			System.out.println(reader.readLine());

			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line).append("\n");
			}

			System.out.println(stringBuilder);

			if (output == 0) {
				return stringBuilder.toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private String[] splitCommand(String command) {
		// Dividir a string de comando em tokens pelo espaço em branco
		return command.split("\\s+");
	}
}