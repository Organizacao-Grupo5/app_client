import model.*;
import monitoramento.Monitoramento;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) throws Exception {
        User usuario1 = new User("Cláudio", "clau@gmail", "1234");

        String email = "";
        String senha = "";

        Scanner scanner = new Scanner(System.in);

        while (!usuario1.getEmail().equals(email) || !usuario1.getSenha().equals(senha)){
            System.out.println("Digite seu email:");
            email = scanner.nextLine();

            System.out.println("Digite sua senha:");
            senha = scanner.nextLine();

            if (!usuario1.getEmail().equals(email) || !usuario1.getSenha().equals(senha)) {
                System.out.println("Email ou senha incorretos. Tente novamente.\n");
            }
        }

        System.out.println("""
                Bem-vindo!
                Estamos capturando os dados para monitoramento.
                """);


        ScheduledFuture<?> monitoramentoHandle = scheduler.scheduleAtFixedRate(() -> {
            try {
                usuario1.inserirMaquina(Monitoramento.monitorarMaquina());
                Maquina maquina = usuario1.getMaquinas().get(0);
                maquina.adicionarRegistro("CPU", Monitoramento.monitorarCpu());
                maquina.adicionarRegistro("RAM", Monitoramento.monitorarRam());
                maquina.adicionarRegistro("DISCO", Monitoramento.monitorarDisco());
             // maquina.adicionarRegistro("GPU", Monitoramento.monitorarGPU());
                maquina.listarComponentes();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 5, TimeUnit.SECONDS);

        Scanner scanner1 = new Scanner(System.in);
        while (true) {
            System.out.println("Pressione 0 para parar o monitoramento...");
            if (scanner.nextInt() == 0) {
                monitoramentoHandle.cancel(true); // Interrompe o monitoramento
                scheduler.shutdown();
                System.exit(0); // Sai do loop
            }
        }
    }


}
