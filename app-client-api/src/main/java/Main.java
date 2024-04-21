import app.integration.HardwareIntegration;
import app.security.Login;

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
                """);
        System.out.print(" - Insira seu email: ");
        String email = scanner.next();
        System.out.print(" - Insira sua senha: ");
        String senha = scanner.next();
        try {
            usuarioLogado = login.login(email, senha);
            System.out.println(" - Terminamos a verificação de seu acesso...   ");
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

        try {
            executorService = Executors.newScheduledThreadPool(1);
            executorService.scheduleAtFixedRate(() -> {
                sistemaOp = systemMonitor.monitorarSistemaOperacional();
                cpu = systemMonitor.monitorarCPU();
                hdd = systemMonitor.monitorarHDD();
                bateria = systemMonitor.monitorarBateria();
                ram = systemMonitor.monitorarRAM();
                gpu = systemMonitor.monitorarGPU();
                usb = systemMonitor.monitorarUSB();
                volume = systemMonitor.monitorarVolumeLogico();
                app = systemMonitor.monitorarDisplay();
            }, 0, 10, TimeUnit.SECONDS);

            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("""
                        +------------------------------------------+----------------------+
                        | Seu monitoramento está em segundo plano                         |
                        +-----------------------------------------------------------------+
                        | Escolha uma opção :                                             |
                        +--------------------------+--------------+----------+------------+
                        | a - Exibir monitoramento | b - Ver Logs | c - Sair | d - Voltar |
                        +--------------------------+--------------+----------+------------+
                        """);
                String input = scanner.next();

                switch (input) {
                    case "a":
                        clearTerminal();
                        displayTables();
                        break;
                    case "b":
                        clearTerminal();
                        Logger.displayLogsInConsole();
                        break;
                    case "c":
                        clearTerminal();
                        System.exit(0);
                        break;
                    case "d":
                        clearTerminal();
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            }

            executorService.shutdown();

        } catch (Exception e) {
            Logger.logError("Erro ao iniciar monitoramento: ", e.getMessage(), e);
        }
    }

    public static void clearTerminal() {
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void displayTables() throws Exception {
        ServiceMonitoring serviceMonitoring = new ServiceMonitoring();
        System.out.println("""
                +---------------------------------------------------------------------------+
                |             Muitos dados foram capturados. Escolha o que exibir            |
                +---------------------------------------------------------------------------+
                | a - Todas as tabelas  | b - Tabela CPU        | c - Tabela de HDD         |
                | d - Tabelas de GPU    | e - Tabela de Ram     | f - Tabela de APPs abertos|                                             |
                | g - Tabela de Bateria | h - Tabela de Sist.Op | i - Tabela de Volume      | 
                | j - Tabela de USB     |                       |                           |
                +--------------------------+--------------+----------+----------------------+
                | 1 - Exibir monitoramento | 2 - Ver Logs | 3 - Sair | 4 - Voltar           |
                +--------------------------+--------------+----------+----------------------+
                """);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();

        switch (input) {
            case "a":
                clearTerminal();
                System.out.println("Mostrando todas as tabelas...");
                serviceMonitoring.exibirTabelas(cpu, gpu, hdd, sistemaOp, ram, app, usb, bateria, volume);
                break;
            case "b":
                System.out.println("Mostrando a tabela de CPU...");
                serviceMonitoring.exibirTabelaCPU(cpu);
                break;
            case "c":
                System.out.println("Mostrando a tabela de HDD...");
                serviceMonitoring.exibirTabelaHDD(hdd);
                break;
            case "d":
                System.out.println("Mostrando as tabelas de GPU...");
                serviceMonitoring.exibirTabelaGPU(gpu);
                break;
            case "e":
                System.out.println("Mostrando a tabela de RAM...");
                serviceMonitoring.exibirTabelaMemoriaRAM(ram);
                break;
            case "f":
                System.out.println("Mostrando a tabela de APPs abertos...");
                serviceMonitoring.exibirTabelaAPP(app);
                break;
            case "g":
                System.out.println("Mostrando a tabela de Bateria...");
                serviceMonitoring.exibirTabelaBateria(bateria);
                break;
            case "h":
                System.out.println("Mostrando a tabela de Sistema Operacional...");
                serviceMonitoring.exibirTabelaSO(sistemaOp);
                break;
            case "i":
                System.out.println("Mostrando a tabela de Volume...");
                serviceMonitoring.exibirTabelaVolume(volume);
                break;
            case "j":
                System.out.println("Mostrando a tabela de USB...");
                serviceMonitoring.exibirTabelaConexaoUSB(usb);
                break;
            case "1":
                clearTerminal();
                break;
            case "2":
                Logger.displayLogsInConsole();
                break;
            case "3":
                clearTerminal();
                System.out.println("Saindo...");
                System.exit(0);
                break;
            case "4":
                System.out.println("Voltando...");
                break;
        }
    }

}
