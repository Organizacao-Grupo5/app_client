import app.integration.HardwareIntegration;
import app.security.Login;

import app.system.SystemMonitor;
import exception.AutenticationException;
import model.*;
import service.ServiceMonitoring;

import java.io.Console;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import util.TablePrinter;
import util.logs.*;
import util.reports.PDFGenerator;

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
        Console console = System.console();
        String senha = "";

        if (console == null) {
            System.out.print(" - Insira sua senha: ");
            senha = scanner.next();
        } else{
            char[] senhaArray = console.readPassword(" - Insira sua senha: ");
            senha = new String(senhaArray);

            java.util.Arrays.fill(senhaArray, ' ');
        }

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
                        System.out.println(Logger.displayLogsInConsole());
                        Map<String, Object> mapaLogs = new HashMap<>();
                        mapaLogs.put("LOGSSTRING",Logger.displayLogsInConsole());
                        baixarPDFExcel(mapaLogs);
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
        TablePrinter tablePrinter = new TablePrinter();
        System.out.println("""
                +---------------------------------------------------------------------------+
                |             Muitos dados foram capturados. Escolha o que exibir           |
                +---------------------------------------------------------------------------+
                | a - Todas as tabelas  | b - Tabela CPU        | c - Tabela de HDD         |
                | d - Tabelas de GPU    | e - Tabela de Ram     | f - Tabela de APPs abertos|
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
                Map<String, Object> todasAsTabelas = serviceMonitoring.exibirTabelas(cpu, gpu, hdd, sistemaOp, ram, app, usb, bateria, volume);
                System.out.println(todasAsTabelas.get("TODASSTRING"));
                baixarPDFExcel(todasAsTabelas);
                break;
            case "b":
                clearTerminal();
                System.out.println("Mostrando a tabela de CPU...");
                Map<String, Object> tabelaCpu = serviceMonitoring.exibirTabelaCPU(cpu);
                System.out.println(tabelaCpu.get("CPUSTRING"));
                baixarPDFExcel(tabelaCpu);
                break;
            case "c":
                clearTerminal();
                System.out.println("Mostrando a tabela de HDD...");
                Map<String, Object> tabelaHDD = serviceMonitoring.exibirTabelaHDD(hdd);
                System.out.println(tabelaHDD.get("HDDSTRING"));
                baixarPDFExcel(tabelaHDD);
                break;
            case "d":
                clearTerminal();
                System.out.println("Mostrando as tabelas de GPU...");
                Map<String, Object> tabelaGPU = serviceMonitoring.exibirTabelaGPU(gpu);
                System.out.println(tabelaGPU.get("GPUSTRIG"));
                baixarPDFExcel(tabelaGPU);
                break;
            case "e":
                clearTerminal();
                System.out.println("Mostrando a tabela de RAM...");
                Map<String, Object> tabelaRam = serviceMonitoring.exibirTabelaMemoriaRAM(ram);
                System.out.println(tabelaRam.get("MemoriaRAMSTRING"));
                baixarPDFExcel(tabelaRam);
                break;
            case "f":
                clearTerminal();
                System.out.println("Mostrando a tabela de APPs abertos...");
                Map<String, Object> tabelaApps = serviceMonitoring.exibirTabelaAPP(app);
                System.out.println(tabelaApps.get("APPSTRING"));
                baixarPDFExcel(tabelaApps);
                break;
            case "g":
                clearTerminal();
                System.out.println("Mostrando a tabela de Bateria...");
                Map<String, Object> tabelaBateria = serviceMonitoring.exibirTabelaBateria(bateria);
                System.out.println(tabelaBateria.get("BateriaSTRING"));
                baixarPDFExcel(tabelaBateria);
                break;
            case "h":
                clearTerminal();
                System.out.println("Mostrando a tabela de Sistema Operacional...");
                Map<String, Object> tabelaSistemaOP = serviceMonitoring.exibirTabelaSO(sistemaOp);
                System.out.println(tabelaSistemaOP.get("SOSTRING"));
                baixarPDFExcel(tabelaSistemaOP);
                break;
            case "i":
                clearTerminal();
                System.out.println("Mostrando a tabela de Volume...");
                Map<String, Object> tabelaVolume = serviceMonitoring.exibirTabelaVolume(volume);
                System.out.println(tabelaVolume.get("VolumeSTRING"));
                baixarPDFExcel(tabelaVolume);
                break;
            case "j":
                clearTerminal();
                System.out.println("Mostrando a tabela de USB...");
                Map<String, Object> tabelaUSB = serviceMonitoring.exibirTabelaConexaoUSB(usb);
                System.out.println(tabelaUSB.get("ConexaoUSBSTRING"));
                baixarPDFExcel(tabelaUSB);
                break;
            case "1":
                clearTerminal();
                break;
            case "2":
                clearTerminal();
                System.out.println(Logger.displayLogsInConsole());
                Map<String, Object> mapaLogs = new HashMap<>();
                mapaLogs.put("LOGSSTRING",Logger.displayLogsInConsole());
                baixarPDFExcel(mapaLogs);
                break;
            case "3":
                clearTerminal();
                System.out.println("Saindo...");
                System.exit(0);
                break;
            case "4":
                clearTerminal();
                System.out.println("Voltando...");
                break;
        }
    }

    public static void baixarPDFExcel(Map<String, Object> tabela) {
        PDFGenerator pdfGenerator = new PDFGenerator();

        System.out.println("""
        +----------------------------------+
        | 5 - Baixar informações em PDF    |
        | 6 - Baixar informações no Excell |
        | 7 - Não                          |
        +----------------------------------+
        """);

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();

        switch (choice) {
            case "5":
                String chaveString = null;
                for (String chave : tabela.keySet()) {
                    if (chave.toUpperCase().contains("STRING")) {
                        chaveString = chave;
                        break;
                    }
                }
                if (chaveString != null) {
                    pdfGenerator.gerarPDF(tabela.get(chaveString).toString());
                } else {
                    Logger.logWarning("Não foi possível gerar o PDF.");
                }
                break;
            case "6":
                List<List<String>> listaToExcell = new ArrayList<>();
                for (String chave : tabela.keySet()){
                    if (!chave.toUpperCase().contains("STRING")){
                        listaToExcell.add((List<String>) tabela.get(chave));
                    }
                }
                break;
            case "7":
                break;
        }
    }
}
