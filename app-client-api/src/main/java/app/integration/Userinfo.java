package app.integration;

import static com.sun.jna.Platform.isWindows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;


public class Userinfo {

	private static Boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
	String directory = "app-client-api\\src\\main\\resources\\scripts";
	ProcessBuilder processBuilder;
	String[] arrayCommand;
	
	public String username() {
		try {
			
			if(!isWindows) {
				arrayCommand = new String[] {"sh", directory + "\\bash\\userinfo\\userUsername.sh"  };
			} else {
				arrayCommand = new String[] {"cmd.exe", "/c", directory + "\\cmd\\userinfo\\userUsername.cmd"};
			}

			processBuilder = new ProcessBuilder(arrayCommand);

			Process process = processBuilder.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			List<String> listStrings = new ArrayList<>();
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				listStrings.add(line);
			}

			BufferedReader erroReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			
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

			if(!isWindows) {
				arrayCommand = new String[] {"sh", directory + "\\bash\\userinfo\\userHostname.sh"};
			} else {
				arrayCommand = new String[] {"cmd.exe", "/c", directory + "\\cmd\\userinfo\\userHostname.cmd"};
			}

			processBuilder = new ProcessBuilder(arrayCommand);

			Process process = processBuilder.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			List<String> listStrings = new ArrayList<>();
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				listStrings.add(line);
			}

			BufferedReader erroReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			
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
            if (!isWindows) {
				arrayCommand = new String[] {"sh", directory + "\\bash\\userinfo\\ipv4.sh"};
            } else {
				arrayCommand = new String[] {"cmd.exe", "/c", directory + "\\cmd\\userinfo\\ipv4.cmd"};
            }
            
            processBuilder = new ProcessBuilder(arrayCommand);
            Process process = processBuilder.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            List<String> listStrings = new ArrayList<>();

            String line;

            while((line = reader.readLine()) != null) {
                listStrings.add(line);
            }

            BufferedReader erroReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        
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