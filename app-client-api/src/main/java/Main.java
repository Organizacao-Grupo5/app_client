import core.auth.Login;
import core.sistema.SystemMonitor;
import exception.AutenticationException;
import exception.ExceptionMonitoring;
import model.Maquina;
import model.Usuario;
import service.ServiceMonitoring;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import util.logs.*;

public class Main {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final Login login = new Login();
    private static final ServiceMonitoring serviceMonitoring = new ServiceMonitoring();

    public static void main(String[] args) throws Exception {
        Usuario usuarioLogado;
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Seja bem-vindo ao nosso app client ...
                                
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
