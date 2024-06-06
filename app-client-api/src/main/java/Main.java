import service.componente.ServiceComponente;
import util.security.Login;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

import com.mysql.cj.util.StringUtils;
import util.exception.AutenticationException;
import model.*;
import model.componentes.*;
import service.ServicePC;
import service.ServiceRede;
import util.security.Criptografia;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import util.logs.*;
import util.reports.PDFGenerator;

public class Main {
    private static final Login login = new Login();
    private static ScheduledExecutorService executorService;

    private static ServiceRede serviceRede = new ServiceRede();
    private static Maquina maquina = new Maquina();
    private static ServicePC servicePC = new ServicePC();
    private static ServiceComponente serviceComponente = new ServiceComponente();

    public static void main(String[] args) throws Exception {

        LogBanco.logInfo("Servidor iniciando.", LogBanco.LogType.INFO);
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
        LogBanco.logInfo("Servidor iniciado com sucesso.", LogBanco.LogType.INFO);
        System.out.print("\r" + " ".repeat(quadros + 10));
        Usuario usuarioLogado = null;
        
        System.out.println("""
            \n
            __     _____ ____  _   _   _    _           ___  ____  ____ 
            \\ \\   / /_ _/ ___|| | | | / \\  | |         / _ \\|  _ \\/ ___|
            \s\\ \\ / / | |\\___ \\| | | |/ _ \\ | |   _____| | | | |_) \\___ \\
            \s\s\\ V /  | | ___) | |_| / ___ \\| |__|_____| |_| |  __/ ___) |
            \s\s\s\\_/  |___|____/ \\___/_/   \\_\\_____|     \\___/|_|   |____/
            _______
            Vamos verificar suas permissões para iniciar o monitoramento.
            _______
            """);
        while (true) {
            Scanner scanner = new Scanner(System.in);

            System.out.print(" - Insira seu email: ");
            String email = scanner.next();
            Console console = System.console();
            String senha = "";
    
            if (console == null) {
                System.out.print(" - Insira sua senha: ");
                senha = scanner.next();
            } else {
                char[] senhaArray = console.readPassword(" - Insira sua senha: ");
                senha = new String(senhaArray);
                java.util.Arrays.fill(senhaArray, ' ');
            }
    
            try {
                usuarioLogado = login.login(email, senha);
    
                System.out.println(" - Terminamos a verificação de seu acesso...   ");
    
                if (usuarioLogado == null) {
                    usuarioLogado = login.login(email, Criptografia.encrypt(senha, 3));
                }
    
                if (usuarioLogado != null) {
                    System.out.println("""
    
                            --- ACESSO CONCEDIDO ---
    
                            Bem-vindo %s
                            email: %s       
                            
                            Vamos verificar as permissões da sua máquina...
                            """.formatted(usuarioLogado.getNome(), usuarioLogado.getEmail()));
    
                    maquina = servicePC.verificarMaquina(usuarioLogado);
    
                    if (maquina == null) {
                        LogBanco.logWarning("Não foi possível acessar a máquina do usuário");
                        break;
                    }
    
                    if (!serviceRede.maquinaContemIp(maquina)) {
                        Logger.logWarning("Essa maquina não está registrada no ip detectado, verifique sua conexão com a internet.");
                        System.out.println("Essa máquina não está registrada no ip detectado, verifique sua conexão com a internet.");
                        break;
                    }
    
                    LogBanco.logInfo(("Usuário logado com sucesso: " + usuarioLogado.getEmail()), LogBanco.LogType.INFO);
                    int shift = 3;
                    String senhaCriptografada = Criptografia.encrypt(senha, shift);
    
                    if (!usuarioLogado.getSenha().equals(senhaCriptografada)) {
                        System.out.print("Deseja criptografar sua senha? (s/n): ");
                        String resposta = scanner.next();
                        if (resposta.equalsIgnoreCase("s")) {
                            login.updatePasswordUser(senhaCriptografada, usuarioLogado.getIdUsuario());
                            System.out.println("Sua senha foi criptografada com sucesso!");
                            LogBanco.logInfo("Sua senha foi criptograda com sucesso", LogBanco.LogType.INFO);
                        }
                    }

    
                    iniciarMonitoramento();
                    scanner.close();
                    break;
                } else {
                    System.out.println("""
    
                            --- ACESSO NEGADO ---
    
                            """);
                    LogBanco.logWarning("Tentativa de login falhou para o email: " + email);
                }
            } catch (AutenticationException e) {
                LogBanco.logError("Erro ao fazer login: ", e.getMessage(), e);
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }



    public static void iniciarMonitoramento() throws IOException {
        serviceComponente.obterComponentes(maquina);

        try {
            LogBanco.logInfo("Capturando os componentes:\n", LogBanco.LogType.INFO);
            executorService = Executors.newScheduledThreadPool(1);
            executorService.scheduleAtFixedRate(() -> {
                serviceComponente.iniciarCapturas(maquina);
            }, 0, 3, TimeUnit.SECONDS);

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
        } catch (Exception e) {
            LogBanco.logError("Erro ao iniciar monitoramento: ", e.getMessage(), e);
        }
    }

    public static void clearTerminal() {
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                ProcessBuilder processBuilder = new ProcessBuilder("sh", "-c", "clear");
                processBuilder.environment().put("TERM", "xterm-256color"); // Defina a variável TERM
                processBuilder.inheritIO().start().waitFor();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void displayTables() throws Exception {
        System.out.println(exibirOpcoes());
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
                if (maquina.getComponentes().stream().anyMatch(c -> c instanceof CPU)) {
                    clearTerminal();
                    System.out.println("Mostrando a tabela de CPU...");
                    System.out.println(maquina.getComponentes().stream().filter(componente -> componente instanceof CPU)
                            .findFirst().get().tabelaConvert());
                    baixarPDF(maquina.getComponentes().stream().filter(componente -> componente instanceof CPU).findFirst()
                            .get().pdfLayout());
                } else {
                    System.out.println("Opção inválida!");
                }
                break;
            case "c":
                if (maquina.getComponentes().stream().anyMatch(c -> c instanceof HDD)) {
                    clearTerminal();
                    System.out.println("Mostrando a tabela de HDD...");
                    maquina.getComponentes().forEach(componente -> {
                        if (componente instanceof HDD) {
                            System.out.println(componente.tabelaConvert());
                        }
                    });
                    baixarPDF(new StringBuilder(maquina.getComponentes().stream()
                            .filter(componente -> componente instanceof HDD).collect(Collectors.toList()).stream()
                            .map(Componente::pdfLayout).collect(Collectors.joining())).toString());
                } else {
                    System.out.println("Opção inválida!");
                }
                break;
            case "d":
                if (maquina.getComponentes().stream().anyMatch(c -> c instanceof GPU)) {
                    clearTerminal();
                    System.out.println("Mostrando as tabelas de GPU...");
                    maquina.getComponentes().forEach(componente -> {
                        if (componente instanceof GPU) {
                            System.out.println(componente.tabelaConvert());
                        }
                    });
                    baixarPDF(new StringBuilder(maquina.getComponentes().stream()
                            .filter(componente -> componente instanceof GPU).collect(Collectors.toList()).stream()
                            .map(Componente::pdfLayout).collect(Collectors.joining())).toString());
                } else {
                    System.out.println("Opção inválida!");
                }
                break;
            case "e":
                if (maquina.getComponentes().stream().anyMatch(c -> c instanceof MemoriaRam)) {
                    clearTerminal();
                    System.out.println("Mostrando a tabela de RAM...");
                    System.out.println(maquina.getComponentes().stream()
                            .filter(componente -> componente instanceof MemoriaRam).findFirst().get().tabelaConvert());
                    baixarPDF(maquina.getComponentes().stream().filter(componente -> componente instanceof MemoriaRam)
                            .findFirst().get().pdfLayout());
                } else {
                    System.out.println("Opção inválida");
                }
                break;
            case "f":
                if (maquina.getComponentes().stream().anyMatch(c -> c instanceof APP)) {
                    clearTerminal();
                    System.out.println("Mostrando a tabela de APPs abertos...");
                    maquina.getComponentes().forEach(componente -> {
                        if (componente instanceof APP) {
                            System.out.println(componente.tabelaConvert());
                        }
                    });
                    baixarPDF(new StringBuilder(maquina.getComponentes().stream()
                            .filter(componente -> componente instanceof APP).collect(Collectors.toList()).stream()
                            .map(Componente::pdfLayout).collect(Collectors.joining())).toString());
                } else {
                    System.out.println("Opção inválida!");
                }
                break;
            case "g":
                if (maquina.getComponentes().stream().anyMatch(c -> c instanceof Bateria)) {
                    clearTerminal();
                    maquina.getComponentes().forEach(componente -> {
                        if (componente instanceof Bateria) {
                            System.out.println(componente.tabelaConvert());
                        }
                    });
                    baixarPDF(new StringBuilder(maquina.getComponentes().stream()
                            .filter(componente -> componente instanceof Bateria).collect(Collectors.toList()).stream()
                            .map(Componente::pdfLayout).collect(Collectors.joining())).toString());
                } else {
                    System.out.println("Opção inválida!");
                }
                break;
            case "h":
                if (maquina.getComponentes().stream().anyMatch(c -> c instanceof SistemaOp)) {
                    clearTerminal();
                    System.out.println("Mostrando a tabela de Sistema Operacional...");
                    System.out.println(maquina.getComponentes().stream()
                            .filter(componente -> componente instanceof SistemaOp).findFirst().get().tabelaConvert());
                    baixarPDF(maquina.getComponentes().stream().filter(componente -> componente instanceof SistemaOp)
                            .findFirst().get().pdfLayout());
                } else {
                    System.out.println("Opção inválida!");
                }
                break;
            case "i":
                if (maquina.getComponentes().stream().anyMatch(c -> c instanceof Volume)) {
                    clearTerminal();
                    System.out.println("Mostrando a tabela de Volume...");
                    maquina.getComponentes().forEach(componente -> {
                        if (componente instanceof Volume) {
                            System.out.println(componente.tabelaConvert());
                        }
                    });
                    baixarPDF(new StringBuilder(maquina.getComponentes().stream()
                            .filter(componente -> componente instanceof Volume).collect(Collectors.toList()).stream()
                            .map(Componente::pdfLayout).collect(Collectors.joining())).toString());
                } else {
                    System.out.println("Opção inválida!");
                }
                break;
            case "j":
                if (maquina.getComponentes().stream().anyMatch(c -> c instanceof ConexaoUSB)) {
                    clearTerminal();
                    System.out.println("Mostrando a tabela de USB...");
                    maquina.getComponentes().forEach(componente -> {
                        if (componente instanceof ConexaoUSB) {
                            System.out.println(componente.tabelaConvert());
                        }
                    });
                    baixarPDF(new StringBuilder(maquina.getComponentes().stream()
                            .filter(componente -> componente instanceof ConexaoUSB).collect(Collectors.toList()).stream()
                            .map(Componente::pdfLayout).collect(Collectors.joining())).toString());
                } else {
                    System.out.println("Opção inválida!");
                }
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
            default:
                System.out.println("Opção inválida!");
        }
    }

    public static void baixarPDF(String tabela) {
        PDFGenerator pdfGenerator = new PDFGenerator();

        System.out.println("""
                +----------------------------------+
                | 5 - Baixar informações em PDF    |
                | Aperte qualquer botão - Não      |
                +----------------------------------+
                """);

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        switch (choice) {
            case "5":
                if (!StringUtils.isNullOrEmpty(tabela)) {
                    pdfGenerator.gerarPDF(tabela);
                } else {
                    System.out.println("Nenhum layout de pdf encontrado");
                }
                break;
        }
    }
    public static String exibirOpcoes() {
        StringBuilder opcoes = new StringBuilder();
        opcoes.append(
                "\n+----------------------------------------------------------------------------+\n" +
                        "|             Muitos dados foram capturados. Escolha o que exibir            |\n" +
                        "+----------------------------------------------------------------------------+\n" +
                        "| a - Todas as tabelas    |");

        boolean hasHDD = false;
        boolean hasAPPs = false;
        int qtdOpcoesNaLinha = 1;
        int qtdTotalOpcoes = (int) maquina.getComponentes().stream().distinct().count();
        int contadorOpcoes = 1;
        for (int i = 0; i < qtdTotalOpcoes; i++) {
            if (qtdOpcoesNaLinha >= 3) {
                opcoes.append("\n|");
                qtdOpcoesNaLinha = 0;
            }
            Componente componente = maquina.getComponentes().get(i);
            if (componente instanceof CPU) {
                opcoes.append(" b - Tabela CPU          |");
                qtdOpcoesNaLinha++;
                contadorOpcoes++;
            } else if (componente instanceof HDD && !hasHDD) {
                opcoes.append(" c - Tabela de HDD      |");
                hasHDD = true;
                qtdOpcoesNaLinha++;
                contadorOpcoes++;
            } else if (componente instanceof GPU) {
                opcoes.append(" d - Tabelas de GPU      |");
                qtdOpcoesNaLinha++;
                contadorOpcoes++;
            } else if (componente instanceof MemoriaRam) {
                opcoes.append(" e - Tabela de Ram       |");
                qtdOpcoesNaLinha++;
                contadorOpcoes++;
            } else if (componente instanceof APP && !hasAPPs) {
                opcoes.append(" f - Tabela de Apps     |");
                hasAPPs = true;
                qtdOpcoesNaLinha++;
                contadorOpcoes++;
            } else if (componente instanceof Bateria) {
                opcoes.append(" g - Tabela de Bateria  |");
                qtdOpcoesNaLinha++;
                contadorOpcoes++;
            } else if (componente instanceof SistemaOp) {
                opcoes.append(" h - Tabela de Sist.Op   |");
                qtdOpcoesNaLinha++;
                contadorOpcoes++;
            } else if (componente instanceof Volume) {
                opcoes.append(" i - Tabela de Volume    |");
                qtdOpcoesNaLinha++;
                contadorOpcoes++;
            } else if (componente instanceof ConexaoUSB) {
                opcoes.append(" j - Tabela de USB       |");
                qtdOpcoesNaLinha++;
                contadorOpcoes++;
            }
        }

        while (qtdOpcoesNaLinha <= 3 && contadorOpcoes % 3 != 0) {
            opcoes.append("                        |");
            qtdOpcoesNaLinha++;
        }

        opcoes.append("\n+-------------------------+--------------+----------+------------------------+\n" +
                "| 1 - Exibir monitoramento| 2 - Ver Logs | 3 - Sair | 4 - Voltar             |\n" +
                "+-------------------------+--------------+----------+------------------------+\n");

        return opcoes.toString();
    }

}