package app.integration;

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
                stringBuilder.append(line).append("\n");
            }
            output = stringBuilder.toString();
        } catch (IOException | InterruptedException e) {
            Logger.logWarning("Não conseguimos obter a temperatura: Saída " + output);
        }
        if (output == null) {
            return 0.0;
        } else {
            String outputString = ((String) output).replace(',', '.');
            return Double.parseDouble(outputString);
        }
    }


}
