import core.auth.Login;
import exception.AutenticationException;
import model.Maquina;
import model.Usuario;
import service.ServiceMonitoring;
import service.ServiceUser;

import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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
                    System.out.println("ID: " + maquina.getIdMaquina());
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

    public static void iniciarMonitoramento(Maquina maquina, Usuario usuario){
        try {
            Map<String, Object> hardwares = serviceMonitoring.monitorar(maquina, usuario);

        }catch (Exception e){
            System.out.println("Erro ao monitorar: " + e);
        }
    }
}
