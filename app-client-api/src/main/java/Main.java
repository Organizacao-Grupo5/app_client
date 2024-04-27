import service.componente.ServiceComponente;
import util.security.Login;

import app.system.SystemMonitor;
import com.mysql.cj.util.StringUtils;
import exception.AutenticationException;
import model.*;
import model.Componentes.*;
import service.ServicePC;

import java.io.Console;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import util.reports.TablePrinter;
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

    private static ServicePC servicePC = new ServicePC();
    private static ServiceComponente serviceComponente = new ServiceComponente();

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
                maquina = servicePC.verificarMaquina(usuarioLogado);

                System.out.println("""

                        --- Máquina ---

                        Lista IPV4: %s
                        """.formatted(maquina.getIpv4().stream().map(ip -> ip).collect(Collectors.joining())));
                if (maquina == null){
                    Logger.logWarning("Não foi possível acessar a máquina do usuário");
                }

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

        serviceComponente.obterComponentes(maquina);

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
                        baixarPDF(Logger.displayLogsInConsole());
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
        ServicePC serviceMonitoring = new ServicePC();
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
                System.out.println(maquina.exibirTabelaComponentes());
                baixarPDF(maquina.layoutPdfComponentes());
                break;
            case "b":
                clearTerminal();
                System.out.println("Mostrando a tabela de CPU...");
                System.out.println(cpu.tabelaConvert());
                baixarPDF(cpu.pdfLayout());
                break;
            case "c":
                clearTerminal();
                System.out.println("Mostrando a tabela de HDD...");
                hdd.forEach(h -> {
                    System.out.println(h.tabelaConvert());
                });
                baixarPDF(new StringBuilder(hdd.stream().map(HDD::pdfLayout).collect(Collectors.joining())).toString());
                break;
            case "d":
                clearTerminal();
                System.out.println("Mostrando as tabelas de GPU...");
                gpu.forEach(g -> {
                    System.out.println(g.tabelaConvert());
                });
                baixarPDF(new StringBuilder(gpu.stream().map(GPU::pdfLayout).collect(Collectors.joining())).toString());
                break;
            case "e":
                clearTerminal();
                System.out.println("Mostrando a tabela de RAM...");
                System.out.println(ram.tabelaConvert());
                baixarPDF(ram.pdfLayout());
                break;
            case "f":
                clearTerminal();
                System.out.println("Mostrando a tabela de APPs abertos...");
                app.forEach(a -> {
                    System.out.println(a.tabelaConvert());
                });
                baixarPDF(new StringBuilder(app.stream().map(APP::tabelaConvert).collect(Collectors.joining())).toString());
                break;
            case "g":
                clearTerminal();
                System.out.println("Mostrando a tabela de Bateria...");
                bateria.forEach(b -> {
                    System.out.println(b.tabelaConvert());
                });
                baixarPDF(new StringBuilder(bateria.stream().map(Bateria::tabelaConvert).collect(Collectors.joining())).toString());
                break;
            case "h":
                clearTerminal();
                System.out.println("Mostrando a tabela de Sistema Operacional...");
                System.out.println(sistemaOp.tabelaConvert());
                baixarPDF(sistemaOp.tabelaConvert());
                break;
            case "i":
                clearTerminal();
                System.out.println("Mostrando a tabela de Volume...");
                volume.forEach(v -> {
                    System.out.println(v.tabelaConvert());
                });
                baixarPDF(volume.stream().map(Volume::tabelaConvert).collect(Collectors.joining()).toString());
                break;
            case "j":
                clearTerminal();
                System.out.println("Mostrando a tabela de USB...");
                usb.forEach(u -> {
                    System.out.println(u.tabelaConvert());
                });
                baixarPDF(usb.stream().map(ConexaoUSB::tabelaConvert).collect(Collectors.joining()).toString());
                break;
            case "1":
                clearTerminal();
                break;
            case "2":
                clearTerminal();
                System.out.println(Logger.displayLogsInConsole());
                baixarPDF(Logger.displayLogsInConsole());
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

    public static void baixarPDF(String tabela) {
        PDFGenerator pdfGenerator = new PDFGenerator();

        System.out.println("""
        +----------------------------------+
        | 5 - Baixar informações em PDF    |
        | Aperte algum botão - Não         |
        +----------------------------------+
        """);

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        switch (choice) {
            case "5":
                if (!StringUtils.isNullOrEmpty(tabela)){
                    pdfGenerator.gerarPDF(tabela);
                } else{
                    System.out.println("Nenhum layout de pdf encontrado");
                }
                break;
        }
    }
}
