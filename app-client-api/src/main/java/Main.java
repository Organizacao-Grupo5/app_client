import com.github.britooo.looca.api.core.Looca;
import objects.*;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    static Looca looca = new Looca();

    public static void main(String[] args) {
        User usuario1 = new User("Cláudio", "clau@gmail", "1234");
        String email = "";
        String senha = "";

        Scanner scanner = new Scanner(System.in);
        while (!usuario1.getEmail().equals(email) && !usuario1.getSenha().equals(senha)){
            System.out.println("Digíte seu email");
            email = scanner.next();
            System.out.println("Digíte sua senha");
            senha = scanner.next();
        }

        System.out.println("""
                Bem-vindo
                Estamos capturando os dados para monitoramento
                """);

        Maquina maquina = new Maquina();
        maquina = criarMaquina();
        usuario1.inserirMaquina(maquina);

        System.out.println(maquina.toString());
    }

    public static Maquina criarMaquina(){
        Maquina minhaMaquina = new Maquina();
        minhaMaquina.setNomeMaquina("Máquina 1");
        minhaMaquina.setSistOperacional(looca.getSistema().getSistemaOperacional());
        capturarComponentes(minhaMaquina);
        return minhaMaquina;
    }

    public static void capturarComponentes(Maquina maquina){
        Cpu cpu = new Cpu();
        Disco disco = new Disco();
        Ram ram = new Ram();
        Gpu gpu = new Gpu();

        cpu.setFrequencia(looca.getProcessador().getFrequencia());
        cpu.setModelo(looca.getProcessador().getFabricante());
        cpu.setUso(looca.getProcessador().getUso());
        cpu.setNumeroNucleos(looca.getProcessador().getNumeroCpusFisicas() + looca.getProcessador().getNumeroCpusLogicas());
        cpu.setDataHoraCaptura(LocalDateTime.now());

        disco.setLivre(looca.getMemoria().getDisponivel());
        disco.setTotal(looca.getMemoria().getTotal());
        disco.setUsado(looca.getMemoria().getEmUso());
        disco.setDataHoraCaptura(LocalDateTime.now());

        ram.setLivre(looca.getMemoria().getDisponivel());
        ram.setTotal(looca.getMemoria().getTotal());
        ram.setUsado(looca.getMemoria().getEmUso());
        ram.setDataHoraCaptura(LocalDateTime.now());

        maquina.setCpu(cpu);
        maquina.setDisco(disco);
        maquina.setRam(ram);
    }
}
