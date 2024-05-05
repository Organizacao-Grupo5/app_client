package app.integration;

import static com.sun.jna.Platform.isWindows;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;


public class Userinfo {

	private static Boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

	public String username() {
		try {
			String scriptName = isWindows ? "userUsername.cmd" : "userUsername.sh";

			InputStream inputStream = getClass().getResourceAsStream("/scripts" + (isWindows ? "/cmd/" : "/bash/") + "userinfo/" + scriptName);
			if (inputStream == null) {
				throw new IOException("Arquivo BAT não encontrado no recurso.");
			}

			Path tempFile = Files.createTempFile("temp_script", isWindows ? ".bat" : ".sh");
			Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

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

			return listStrings.get(2);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String hostname() {
		try {
			String scriptName = isWindows ? "userHostname.cmd" : "hostname.sh";

			InputStream inputStream = getClass().getResourceAsStream("/scripts" + (isWindows ? "/cmd/" : "/bash/") + "userinfo/" + scriptName);
			if (inputStream == null) {
				throw new IOException("Arquivo BAT não encontrado no recurso.");
			}

			Path tempFile = Files.createTempFile("temp_script", isWindows ? ".bat" : ".sh");
			Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

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

			return listStrings.get(2);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String ipv4() {
        try {
			String scriptName = isWindows ? "ipv4.cmd" : "ipv4.sh";

			InputStream inputStream = getClass().getResourceAsStream("/scripts" + (isWindows ? "/cmd/" : "/bash/") + "userinfo/" + scriptName);
			if (inputStream == null) {
				throw new IOException("Arquivo BAT não encontrado no recurso.");
			}

			Path tempFile = Files.createTempFile("temp_script", isWindows ? ".bat" : ".sh");
			Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

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

            String ipv4 = listStrings.get(2);
            
			
			if (isWindows()) {
				Boolean doisPontos = false;
	
				StringBuilder sb = new StringBuilder();
	
				for (int i = 0; i < ipv4.length(); i++) {
					if (ipv4.charAt(i) == ':' || doisPontos) {
						doisPontos = true;
	
						char c = ipv4.charAt(i);
				
						if (Character.isDigit(c) || c == '.') {
							sb.append(c);
						}
					}
				}
				ipv4 = sb.toString();
			}

			return ipv4;


        } catch (Exception e) {
            e.printStackTrace();
		}

		return null;
	}
}