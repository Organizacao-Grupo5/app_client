import app.integration.HardwareIntegration;
import app.security.Login;
import app.system.Components.CPUMonitoring;
import app.system.Components.HDDMonitoring;
import app.system.Components.SistemaOPMonitoring;
import app.system.SystemMonitor;
import exception.AutenticationException;
import model.*;
import service.ServiceMonitoring;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import util.logs.*;

public class Main {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final Login login = new Login();
    private static ScheduledExecutorService executorService;
    private static final ServiceMonitoring serviceMonitoring = new ServiceMonitoring();
    private static HardwareIntegration hardwareIntegration = new HardwareIntegration();

    private static CPU cpu = new CPU();
    private static List<GPU> gpu = new ArrayList<>();
    private static List<HDD> hdd = new ArrayList<>();
    private static MemoriaRam ram = new MemoriaRam();
    private static List<APP> app = new ArrayList<>();
    private static List<Bateria> bateria = new ArrayList<>();
    private static List<Volume> volume = new ArrayList<>();
    private static SistemaOp sistemaOp = new SistemaOp();
    private static PlacaMae placaMae = new PlacaMae();
    private static Maquina maquina = new Maquina();
    private static List<ConexaoUSB> usb = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        Logger.logInfo("Servidor iniciando.");
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
        Logger.logInfo("Servidor iniciado com sucesso.");
        System.out.print("\r" + " ".repeat(quadros + 10));
        Usuario usuarioLogado;
        Scanner scanner = new Scanner(System.in);

        System.out.println(""" 
                \n
                __     _____ ____  _   _   _    _           ___  ____  ____ \s
                \\ \\   / /_ _/ ___|| | | | / \\  | |         / _ \\|  _ \\/ ___|\s
                 \\ \\ / / | |\\___ \\| | | |/ _ \\ | |   _____| | | | |_) \\___ \\\s
                  \\ V /  | | ___) | |_| / ___ \\| |__|_____| |_| |  __/ ___) |
                   \\_/  |___|____/ \\___/_/   \\_\\_____|     \\___/|_|   |____/\s
                _____________________________________________________________  
                Vamos verificar suas permissões para iniciar o monitoramento.
                _____________________________________________________________
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
                Logger.logInfo("Usuário logado com sucesso: " + usuarioLogado.getEmail());
                iniciarMonitoramento();
            } else {
                System.out.println("""

                        --- ACESSO NEGADO ---

                        """);
                Logger.logWarning("Tentativa de login falhou para o email: " + email);
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

    public static void iniciarMonitoramento() {
        SystemMonitor systemMonitor = new SystemMonitor();
        CPUMonitoring cpuMonitoring = new CPUMonitoring();
        HDDMonitoring hddMonitoring = new HDDMonitoring();
        SistemaOPMonitoring sistemaOPMonitoring = new SistemaOPMonitoring();

        try {
            executorService = Executors.newScheduledThreadPool(1);
            executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    cpu = cpuMonitoring.monitorarCPU();
                    hdd = hddMonitoring.monitorarHDD();
                    sistemaOp = sistemaOPMonitoring.monitorarSistemaOperacional();
                }
            }, 0, 5, TimeUnit.SECONDS);

            Scanner scanner = new Scanner(System.in);
            boolean running = true;
            System.out.println("""
                    +----------------------------------------------------+
                    | Seu monitoramento iniciou e está em segundo plano  |
                    +----------------------------------------------------+
                    | Escolha uma opção :                                |
                    +--------------------------+--------------+----------+
                    | 1 - Exibir monitoramento | 2 - Ver Logs | 3 - Sair |
                    +--------------------------+--------------+----------+
                    """);

            while (running) {
                Integer input = scanner.nextInt();
                System.out.println("""
                    +--------------------------+--------------+----------+
                    | 1 - Exibir monitoramento | 2 - Ver Logs | 3 - Sair |
                    +--------------------------+--------------+----------+
                    """);
                Logger.logInfo("Ação escolhida: " + (input == 1 ? "Exibir Monitoramento": input == 2 ? "Ver logs" : "Sair"));
                switch (input) {
                    case 1:
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        serviceMonitoring.exibirTabelas(cpu, gpu, hdd, sistemaOp, ram, app, usb, bateria, volume);
                        break;
                    case 2:
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        Logger.displayLogsInConsole();
                        break;
                    case 3:
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.exit(0);
                        break;
                }
            }

            executorService.shutdown();

        } catch (Exception e) {
            Logger.logError("Erro ao iniciar monitoramento: ", e.getMessage(), e);
        }
    }

    public static void limparConsole() {

    }

}
