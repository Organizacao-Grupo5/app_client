import java.io.IOException;

public class Launcher {
    public static void main(String[] args) {
        try {
            String command = "powershell.exe -NoExit -Command \"Start-Process java -ArgumentList ('-jar', 'app-client-api-1.0-SNAPSHOT-jar-with-dependencies.jar') -Verb RunAs\"";
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}