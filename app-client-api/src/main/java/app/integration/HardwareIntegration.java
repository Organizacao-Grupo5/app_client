package app.integration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HardwareIntegration {
    Boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
    String directory = isWindows ? "/powershell" : "bash";
    ProcessBuilder builder = new ProcessBuilder();

    public String monitorarBateria() {
        StringBuilder output = new StringBuilder();

        try {
            String scriptPath = "app-client-api/src/scripts" + directory + "/power.ps1";
            String command = "powershell.exe -ExecutionPolicy Bypass -File " + scriptPath;
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        String outputString = output.toString();
        String[] numbers = outputString.replaceAll("\\D+", " ").trim().split("\\s+");
        StringBuilder numbersOutput = new StringBuilder();
        for (String number : numbers) {
            numbersOutput.append(number).append(" ");
        }

        return numbersOutput.toString().trim();
    }

}
