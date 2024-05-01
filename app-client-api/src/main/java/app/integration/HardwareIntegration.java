package app.integration;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class HardwareIntegration {
	private static Boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
	String directory = isWindows ? "/scripts/powershell" : "/scripts/bash";

	public Double monitorarBateria() {
		String output = null;

		try {
			String scriptName = isWindows ? "power.ps1" : "power.sh";
			InputStream inputStream = getClass().getResourceAsStream(directory + "/" + scriptName);
			if (inputStream == null) {
				throw new IOException("Script não encontrado no recurso.");
			}

			Path tempFile = Files.createTempFile("temp_script", isWindows ? ".ps1" : ".sh");
			Files.copy(inputStream, tempFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

			String absolutePath = tempFile.toAbsolutePath().toString();
			String command = isWindows ? "powershell.exe -ExecutionPolicy Bypass -File " + absolutePath : "sh " + absolutePath;

			Process process = Runtime.getRuntime().exec(command);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line).append("\n");
			}
			process.waitFor();

			output = stringBuilder.toString().trim();
			Files.deleteIfExists(tempFile);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		return parseOutput(output);
	}

	public Double monitorarTemperatura() {
		String output = null;

		try {
			String scriptName = isWindows ? "cpuTemperature.bat" : "cpuTemperature.sh";
			InputStream inputStream = getClass().getResourceAsStream("/scripts/cmd/" + scriptName);
			if (inputStream == null) {
				throw new IOException("Arquivo BAT não encontrado no recurso.");
			}

			Path tempFile = Files.createTempFile("temp_script", isWindows ? ".bat" : ".sh");
			Files.copy(inputStream, tempFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

			String absolutePath = tempFile.toAbsolutePath().toString();

			Process processo = Runtime.getRuntime().exec(absolutePath);

			BufferedReader reader = new BufferedReader(new InputStreamReader(processo.getInputStream()));
			String linha;
			while ((linha = reader.readLine()) != null) {
				output = linha;
			}

			int status = processo.waitFor();
			Files.deleteIfExists(tempFile);

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return parseOutput(output);
	}

	private Double parseOutput(String output) {
		if (output == null || output.isEmpty()) {
			return null;
		} else {
			String outputString = output.replace(',', '.');
			return Double.parseDouble(outputString);
		}
	}
}
