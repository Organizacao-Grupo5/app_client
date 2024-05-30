package app.integration;

import util.logs.LogGenerator;

import static com.sun.jna.Platform.isWindows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;


public class Userinfo {
	private static LogGenerator logGenerator = new LogGenerator();

	private static Boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

	public String username() throws IOException {
		try {
			String scriptName = isWindows ? "userUsername.cmd" : "userUsername.sh";

			InputStream inputStream = getClass().getResourceAsStream("/scripts" + (isWindows ? "/cmd/" : "/bash/") + "userinfo/" + scriptName);
			if (inputStream == null) {
				throw new IOException("Arquivo BAT não encontrado no recurso.");
			}

			Path tempFile = Files.createTempFile("temp_script", isWindows ? ".bat" : ".sh");
			Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

			if (!isWindows) {
				Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxr-xr-x");
				Files.setPosixFilePermissions(tempFile, permissions);
			}

			String absolutePath = tempFile.toAbsolutePath().toString();

			Process processo = Runtime.getRuntime().exec(absolutePath);

			BufferedReader reader = new BufferedReader(new InputStreamReader(processo.getInputStream()));
			
			List<String> listStrings = new ArrayList<>();
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				listStrings.add(line);
			}

			BufferedReader erroReader = new BufferedReader(new InputStreamReader(processo.getErrorStream()));
			
			while ((line = erroReader.readLine()) != null) {
				System.err.println("Saída de erro: "+ line);
			}

			String res;

			if (isWindows()) {
				res = listStrings.get(2);
			} else {
				res = listStrings.get(0);
			}

			return res;


		} catch (Exception e) {
			LogGenerator.logError("Erro na integração com as informações da máquina do usuário", e.getMessage(), e);
			e.printStackTrace();
		}

		return null;
	}

	public String hostname() throws IOException {
		try {
			String scriptName = isWindows ? "userHostname.cmd" : "hostname.sh";

			InputStream inputStream = getClass().getResourceAsStream("/scripts" + (isWindows ? "/cmd/" : "/bash/") + "userinfo/" + scriptName);
			if (inputStream == null) {
				throw new IOException("Arquivo BAT não encontrado no recurso.");
			}

			Path tempFile = Files.createTempFile("temp_script", isWindows ? ".bat" : ".sh");
			Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

			if (!isWindows) {
				Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxr-xr-x");
				Files.setPosixFilePermissions(tempFile, permissions);
			}

			String absolutePath = tempFile.toAbsolutePath().toString();

			Process processo = Runtime.getRuntime().exec(absolutePath);

			BufferedReader reader = new BufferedReader(new InputStreamReader(processo.getInputStream()));

			List<String> listStrings = new ArrayList<>();
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				listStrings.add(line);
			}

			BufferedReader erroReader = new BufferedReader(new InputStreamReader(processo.getErrorStream()));
			
			while ((line = erroReader.readLine()) != null) {
				System.err.println("Saída de erro: "+ line);
			}

			String res;

			if (isWindows()) {
				res = listStrings.get(2);
			} else {
				res =  listStrings.get(0);
			}

			return res;
		} catch (Exception e) {
			LogGenerator.logError("Erro na integração com as informações da máquina do usuário", e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}

	public String ipv4() throws IOException {
        try {
			String scriptName = isWindows ? "ipv4.cmd" : "ipv4.sh";

			InputStream inputStream = getClass().getResourceAsStream("/scripts" + (isWindows ? "/cmd/" : "/bash/") + "userinfo/" + scriptName);
			if (inputStream == null) {
				throw new IOException("Arquivo BAT não encontrado no recurso.");
			}

			Path tempFile = Files.createTempFile("temp_script", isWindows ? ".bat" : ".sh");
			Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

			if (!isWindows) {
				Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxr-xr-x");
				Files.setPosixFilePermissions(tempFile, permissions);
			}

			String absolutePath = tempFile.toAbsolutePath().toString();

			Process processo = Runtime.getRuntime().exec(absolutePath);

			BufferedReader reader = new BufferedReader(new InputStreamReader(processo.getInputStream()));

			List<String> listStrings = new ArrayList<>();

            String line;

            while((line = reader.readLine()) != null) {
                listStrings.add(line);
            }

            BufferedReader erroReader = new BufferedReader(new InputStreamReader(processo.getErrorStream()));
        
            while ((line = erroReader.readLine()) != null) {
                System.err.println("Saída de erro: "+ line);
            }

			String ipv4;
			if (isWindows()) {
				 ipv4 = listStrings.get(2);
			} else {
				ipv4 = listStrings.get(0);
			}

			return ipv4;

        } catch (Exception e) {
			LogGenerator.logError("Erro na integração com as informações da máquina do usuário", e.getMessage(), e);
			e.printStackTrace();
		}

		return null;
	}
}