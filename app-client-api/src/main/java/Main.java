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
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
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

                Maquina maquina = serviceMonitoring.verificaMaquinaUsuario(usuarioLogado.getIdUsuario());
                if (maquina != null) {
                    System.out.println("Sua máquina foi verificada com sucesso:");
                    System.out.println("ID: "+ maquina.getIdMaquina());
                    System.out.println("IPv4: " + maquina.getIpv4());
                    System.out.println("""
                            Iniciando o monitoramento dos hardwares:
                            """);
                    iniciarMonitoramento(maquina, usuarioLogado);
                } else {
                    System.out.println("Sua máquina não foi verificada. Ela não está autorizado a usar o aplicativo.");
                }
            } else {
                System.out.println("""
                                                
                        --- ACESSO NEGADO ---
                                                
                        """);
            }
        } catch (AutenticationException e) {
            System.out.println("Erro ao fazer login: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    public static void iniciarMonitoramento(Maquina maquina, Usuario usuario) {
        try {
            ServiceMonitoring serviceMonitoring = new ServiceMonitoring();
            scheduler.scheduleAtFixedRate(() -> {
                try {
                    Map<String, Object> hardwares = serviceMonitoring.monitorar(maquina, usuario);
                } catch (ExceptionMonitoring e) {
                    throw new RuntimeException(e);
                }
                    limparConsole();
                }, 0, 5, TimeUnit.SECONDS);

        } catch (Exception e) {
            System.out.println("Erro ao monitorar: " + e);
        }
    }

    public static void limparConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println(" Erro ao limpar o console: " + e);
        }
    }

}
