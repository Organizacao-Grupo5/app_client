package app.integration;

import app.system.Conversor;
import com.mysql.cj.util.StringUtils;
import util.logs.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class ShellIntegration {
	private static Boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
	String directory = isWindows ? "/scripts/powershell" : "/scripts/bash";

	Conversor conversor = new Conversor();

	public Double monitorarBateria() {
		String output = null;

		try {
			String scriptName = isWindows ? "power.ps1" : "power.sh";
			InputStream inputStream = getClass().getResourceAsStream(directory + "/" + scriptName);
			if (inputStream == null) {
				throw new IOException("Script não encontrado no recurso.");
			}

			Path tempFile = Files.createTempFile("temp_script", isWindows ? ".ps1" : ".sh");
			Files.copy(inputStream, tempFile, REPLACE_EXISTING);

			if (!isWindows) {
				Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxr-xr-x");
				Files.setPosixFilePermissions(tempFile, permissions);
			}

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
			InputStream inputStream = getClass().getResourceAsStream("/scripts" + (isWindows ? "/cmd/" : "/bash/") + scriptName);
			if (inputStream == null) {
				throw new IOException("Arquivo BAT não encontrado no recurso.");
			}

			Path tempFile = Files.createTempFile("temp_script", isWindows ? ".bat" : ".sh");
			Files.copy(inputStream, tempFile, REPLACE_EXISTING);

			if (!isWindows) {
				Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxr-xr-x");
				Files.setPosixFilePermissions(tempFile, permissions);
			}

			String absolutePath = tempFile.toAbsolutePath().toString();

			Process processo = Runtime.getRuntime().exec(absolutePath);

			BufferedReader reader = new BufferedReader(new InputStreamReader(processo.getInputStream()));
			StringBuilder outputBuilder = new StringBuilder();
			String linha;
			while ((linha = reader.readLine()) != null) {
				outputBuilder.append(linha).append("\n");
			}

			int status = processo.waitFor();
			Files.deleteIfExists(tempFile);

			if (status != 0) {
				throw new IOException("O script terminou com um código de saída diferente de zero: " + status);
			}

			output = outputBuilder.toString().trim();

		} catch (IOException | InterruptedException e) {
			Logger.logError("Não foi possível monitorar a temperatura.", e.getMessage(), e);
		}
		return parseOutput(output);
	}

	public Double monitorarUsoDeRamDaJanela(String pid) {
		String output = null;
		try {
			String scriptName = isWindows ? "ramUsageWindow.bat" : "ramUsageWindow.sh";
			InputStream inputStream = getClass().getResourceAsStream("/scripts" + (isWindows ? "/cmd/" : "/bash/") + scriptName);
			if (inputStream == null) {
				throw new IOException("Arquivo BAT não encontrado no recurso.");
			}

			Path tempFile = Files.createTempFile("temp_script", isWindows ? ".bat" : ".sh");
			Files.copy(inputStream, tempFile, REPLACE_EXISTING);

			if (!isWindows) {
				Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxr-xr-x");
				Files.setPosixFilePermissions(tempFile, permissions);
			}

			String absolutePath = tempFile.toAbsolutePath().toString();

			if (!StringUtils.isNullOrEmpty(pid)){
				Process processo = Runtime.getRuntime().exec(absolutePath + " " + pid.replace(".", ""));

				BufferedReader reader = new BufferedReader(new InputStreamReader(processo.getInputStream()));
				StringBuilder outputBuilder = new StringBuilder();
				String linha;

				while ((linha = reader.readLine()) != null) {
					outputBuilder.append(linha).append("\n");
				}

				int status = processo.waitFor();
				Files.deleteIfExists(tempFile);

				if (status != 0) {
					throw new IOException("O script terminou com um código de saída diferente de zero: " + status);
				}

				output = outputBuilder.toString().trim();

				Logger.logInfo(String.format(
						"A ram utilizada na janela de PID %s foi coletada com sucesso, uso de ram: %s MB",
						pid, output));

			} else {
				Logger.logWarning("Não foi possível captura a ram, pois o PID não pode ser capturado.");
			}

		} catch (IOException | InterruptedException e) {
			Logger.logError("Não foi possível monitorar o uso de RAM do PID: " + pid, e.getMessage(), e);
			e.printStackTrace();
		}
		return Double.parseDouble(output);
	}

	private Double parseOutput(String output) {
		if (output == null || output.isEmpty()) {
			return null;
		}

		try {
			String[] lines = output.split("\n");
			if (lines.length > 0) {
				String outputString = lines[0].replace(',', '.');
				return Double.parseDouble(outputString);
			}
		} catch (NumberFormatException e) {
			Logger.logError("Erro ao converter a saída para número: " + output, e.getMessage(), e);
		}
		return null;
	}
}
