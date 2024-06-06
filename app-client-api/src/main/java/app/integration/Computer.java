package app.integration;

import util.logs.Logger;

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
import java.util.Optional;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import oshi.SystemInfo;
import oshi.hardware.NetworkIF;


public class Computer {

	private static Boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

	public String getUsername() {
		try {
			String scriptName = isWindows ? "userUsername.cmd" : "userUsername.sh";

			String absolutePath = createPath(scriptName);
			
			List<String> listStrings = lerTerminal(absolutePath);
			
			String res;

			if (isWindows()) {
				res = listStrings.get(2);

				Boolean apartirDoisPontos = false;
	
				StringBuilder sb = new StringBuilder();
	
				for (int i = 0; i < res.length(); i++) {
					if (res.charAt(i) == ':' || apartirDoisPontos) {
						apartirDoisPontos = true;
	
						char c = res.charAt(i);
				
						if (Character.isDigit(c) || c == '.') {
							sb.append(c);
						}
					}
				}
				res = sb.toString();

			} else {
				res = listStrings.get(0);
			}

			return res;
			
		} catch (Exception e) {
			Logger.logError("Erro na integração com as informações da máquina do usuário", e.getMessage(), e);
			e.printStackTrace();
		}

		return null;
	}

	public String getHostname() {
		try {
			String scriptName = isWindows ? "userHostname.cmd" : "hostname.sh";
			
			String absolutePath = createPath(scriptName);
			
			List<String> listStrings = lerTerminal(absolutePath);
			
			String res;

			if (isWindows()) {
				res = listStrings.get(2);
			} else {
				res =  listStrings.get(0);
			}

			return res;
		} catch (Exception  e) {
			Logger.logError("Erro na integração com as informações da máquina do usuário", e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}

	public String getIpv4() {
        try {
			String scriptName = isWindows ? "ipv4.cmd" : "ipv4.sh";

			String absolutePath = createPath(scriptName);
			
			List<String> listStrings = lerTerminal(absolutePath);

			String ipv4;

			if (isWindows()) {
				ipv4 = listStrings.get(2);
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
			} else {
				ipv4 = listStrings.get(0);
			}

			return ipv4;

        } catch (Exception e) {
			Logger.logError("Erro na integração com as informações da máquina do usuário", e.getMessage(), e);
			e.printStackTrace();
		}

		return null;
	}

	public String getNomeRede() {
        try {
			List<String> listResults = getRede();
	
			Optional<String> nome = listResults.stream()
				.filter(result -> result.toLowerCase().contains("ssid"))
					.map(result -> result.split(":")[1].trim())
						.findFirst();
	
			return nome.get();
		} catch (Exception e) {
			Logger.logError("Erro ao pegar o nome da rede", e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
    }

	public String getInterfaceRede() {
		try {
			List<String> listResults = getRede();
	
			Optional<String> interfaceRede = listResults.stream()
				.filter(result -> result.toLowerCase().contains("nome"))
					.map(result -> result.split(":")[1].trim())
						.findFirst();
	
			return interfaceRede.get();
		} catch (Exception e) {
			Logger.logError("Erro ao pegar a interface da rede", e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
    }

	public Integer getSinalRede() {
		try {
			List<String> listResults = getRede();
	
			Optional<String> sinal = listResults.stream()
				.filter(result -> result.toLowerCase().contains("sinal"))
					.map(result -> result.split(":")[1].replace("%", "").trim())
						.findFirst();
	
			return Integer.parseInt(sinal.get());
		} catch (Exception e) {
			Logger.logError("Erro ao pegar o sinal da rede", e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
    }

    public Double getTransmissaoRede() {
		try {
			List<String> listResults = getRede();
	
			Optional<String> transmissao = listResults.stream()
				.filter(result -> result.toLowerCase().contains("transmiss"))
					.map(result -> result.split(":")[1].trim())
						.findFirst();
	
			return Double.parseDouble(transmissao.get());
		} catch (Exception e) {
			Logger.logError("Erro ao pegar a transmissão da rede", e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
    }

	public String getBssidRede() {
		try {
			List<String> listResults = getRede();
	
			Optional<String> BSSID = listResults.stream()
				.filter(result -> result.toLowerCase().contains("bssid"))
					.map(result -> result.split(": ")[1].trim())
						.findFirst();
	
			return BSSID.get();
		} catch (Exception e) {
			Logger.logError("Erro ao pegar o BSSID da rede", e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
    }

    public String getMac() {
        try {
			SystemInfo systemInfo = new SystemInfo();
			List<NetworkIF> networkIFs = systemInfo.getHardware().getNetworkIFs();

			List<String> enderecosMAC = new ArrayList<>();

			for (NetworkIF net : networkIFs) {
				enderecosMAC.add(net.getMacaddr());
			}

			return enderecosMAC.get(0);
		} catch (Exception e) {
			Logger.logError("Erro ao pegar o endereço MAC do computador", e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
    }

	public List<List<String>> listarDispositivos(String ip) {
        List<List<String>> listAddress = new ArrayList<>();

        Pattern pattern = Pattern.compile("^.*(?=\\.\\d+$)");
        Matcher matcher = pattern.matcher(ip);
        matcher.find();
        String newIp = matcher.group();
        
        try {
            String absolutePath = createPath("listDevices.cmd");

			List<String> listStrings = lerTerminal(absolutePath);

            listStrings = listStrings.stream()
                .filter(result -> result.contains(newIp))
                    .collect(Collectors.toList());


            listStrings.remove(listStrings.size()-1);

            for (String result : listStrings) {
                String[] parts = result.trim().split("\\s+");
                String ipAd = parts[0];
                String macAd = parts[1];

                List<String> miniListIpMac = new ArrayList<>();
                miniListIpMac.add(ipAd);
                miniListIpMac.add(macAd);

                listAddress.add(miniListIpMac);
            }

        } catch (Exception e) {
			Logger.logError("Erro ao listar dispostivos da rede!", e.getMessage(), e);
            e.printStackTrace();
        }

        return listAddress;
    }

	private List<String> getRede() {
		List<String> listStrings = new ArrayList<>();

		try {
			String absolutePath = createPath("rede.cmd");

			listStrings = lerTerminal(absolutePath);
		} catch (Exception e) {
			Logger.logError("Erro ao pegar as informações da rede!", e.getMessage(), e);
			e.printStackTrace();
		}
		return listStrings;
	}

	private String createPath(String scriptName) {
		String path = new String();
		try {			

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

			path = tempFile.toAbsolutePath().toString();	
		
		} catch (Exception e) {
			e.printStackTrace();
			Logger.logError("Ocorreu um erro ao criar um caminho para executar os comandos!", e.getMessage(), e);
		}
		return path;
	}

	private List<String> lerTerminal(String absolutePath) {		
		List<String> listStrings = new ArrayList<>();
		try {			
			Process processo = Runtime.getRuntime().exec(absolutePath);
	
			BufferedReader reader = new BufferedReader(new InputStreamReader(processo.getInputStream()));
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				listStrings.add(line);
			}
	
			BufferedReader erroReader = new BufferedReader(new InputStreamReader(processo.getErrorStream()));
			
			while ((line = erroReader.readLine()) != null) {
				System.err.println("Saída de erro: "+ line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Logger.logError("Ocorreu um erro ao ler os resultados vindos do terminal!", e.getMessage(), e);
		}
		
		return listStrings;
	}

}