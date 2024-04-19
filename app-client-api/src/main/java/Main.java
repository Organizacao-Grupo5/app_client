import app.integration.HardwareIntegration;
import app.security.Login;
import exception.AutenticationException;
import model.Maquina;
import model.Usuario;
import service.ServiceMonitoring;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import util.logs.*;

public class Main {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final Login login = new Login();
    private static final ServiceMonitoring serviceMonitoring = new ServiceMonitoring();
    private static HardwareIntegration hardwareIntegration = new HardwareIntegration();



    public static void main(String[] args) throws Exception {
        int quadros = 50;

        for (int i = 0; i <= quadros; i++) {
            int porcentagem = i * 100 / quadros;

            StringBuilder barraCarregamento = new StringBuilder("\u001B[34m[");
            for (int j = 0; j < quadros; j++) {
                barraCarregamento.append(j < i ? "=" : " ");
            }
            barraCarregamento.append("] ");

            barraCarregamento.append(porcentagem).append("%");

            System.out.print("\r" + barraCarregamento);

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.print("\r" + " ".repeat(quadros + 10));

        System.out.println("\rSeja Bem Vindo a VisualOps!");

        Usuario usuarioLogado;
        Scanner scanner = new Scanner(System.in);
        System.out.println("""       
                Vamos verificar suas permissões para iniciar o monitoramento.
                                
                Insira seu email:
                """);
        String email = scanner.next();
        System.out.println("""
                Insira sua senha:
                """);
        String senha = scanner.next();
        try {
            usuarioLogado = login.login(email, senha);
            System.out.println("Terminamos a verificação de seu acesso.");
            if (usuarioLogado != null) {
                System.out.println("""

                        --- ACESSO CONCEDIDO ---

                        Bem-vindo %s
                        email: %s

                        Vamos verificar as permissões da sua máquina...
                        """.formatted(usuarioLogado.getNome(), usuarioLogado.getEmail()));
                serviceMonitoring.iniciarMonitoramento();
                System.out.println(hardwareIntegration.monitorarBateria());
            } else {
                System.out.println("""

                        --- ACESSO NEGADO ---

                        """);
            }
        } catch (AutenticationException e) {
            Logger.logError("Erro ao fazer login: ", e.getMessage(), e);
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    public static void iniciarMonitoramento(Maquina maquina, Usuario usuario) {
        try {

        } catch (Exception e) {
            Logger.logError("Erro : ", e.getMessage(), e);
        }
    }

    public static void limparConsole() {

    }

}
