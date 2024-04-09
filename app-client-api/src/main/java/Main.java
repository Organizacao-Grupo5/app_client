import com.github.britooo.looca.api.core.Looca;
import objects.*;
import objects.Historicos.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    static Looca looca = new Looca();

    static {
        // Carrega a biblioteca JNI
        System.loadLibrary("NvidiaGPUInfo");
    }

    public static void main(String[] args) {
        User usuario1 = new User("Cláudio", "clau@gmail", "1234");
        String email = "";
        String senha = "";

        Scanner scanner = new Scanner(System.in);
        while (!usuario1.getEmail().equals(email) && !usuario1.getSenha().equals(senha)){
            System.out.println("Digite seu email:");
            email = scanner.next();
            System.out.println("Digite sua senha:");
            senha = scanner.next();
        }

        System.out.println("""
                Bem-vindo!
                Estamos capturando os dados para monitoramento.
                """);

        Maquina maquina = criarMaquina();
        usuario1.inserirMaquina(maquina);

        System.out.println("Conexão feita com sucesso!");
        System.out.println(maquina.toString());
        System.out.println("Iniciando o monitoramento contínuo");

        for (int i = 0; i < 10; i++) {
            iniciarMonitoramento(maquina);
            System.out.println(maquina.getRam().getHistoricosRam().toString());
            System.out.println(maquina.getDisco().getHistoricosDisco().toString());
            System.out.println(maquina.getCpu().getHistoricosCpu().toString());
            int gpuUsage = getGPUUsage();
            System.out.println("Uso da GPU: " + gpuUsage + "%");
        }
    }

    public static void iniciarMonitoramento(Maquina maquina){
        HistoricoCpu cpu = new HistoricoCpu();
        HistoricoDisco disco = new HistoricoDisco();
        HistoricoRam ram = new HistoricoRam();

        cpu.setFrequencia(looca.getProcessador().getFrequencia());
        cpu.setUso(looca.getProcessador().getUso());
        cpu.setDataHoraCaptura(LocalDateTime.now());

        ram.setLivre(looca.getMemoria().getDisponivel());
        ram.setTotal(looca.getMemoria().getTotal());
        ram.setUsado(looca.getMemoria().getEmUso());
        ram.setDataHoraCaptura(LocalDateTime.now());

        disco.setLivre(looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel());
        disco.setTotal(looca.getGrupoDeDiscos().getVolumes().get(0).getTotal());
        disco.setUsado(looca.getGrupoDeDiscos().getVolumes().get(0).getTotal() - looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel());
        disco.setDataHoraCaptura(LocalDateTime.now());

        maquina.getCpu().gravarHistorico(cpu);
        maquina.getDisco().gravarHistorico(disco);
        maquina.getRam().gravarHistorico(ram);
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

        cpu.setFrequencia(looca.getProcessador().getFrequencia());
        cpu.setModelo(looca.getProcessador().getFabricante());
        cpu.setUso(looca.getProcessador().getUso());
        cpu.setNumeroNucleos(looca.getProcessador().getNumeroCpusFisicas() + looca.getProcessador().getNumeroCpusLogicas());
        cpu.setDataHoraCaptura(LocalDateTime.now());

        ram.setLivre(looca.getMemoria().getDisponivel());
        ram.setTotal(looca.getMemoria().getTotal());
        ram.setUsado(looca.getMemoria().getEmUso());
        ram.setDataHoraCaptura(LocalDateTime.now());

        disco.setLivre(looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel());
        disco.setTotal(looca.getGrupoDeDiscos().getVolumes().get(0).getTotal());
        disco.setUsado(looca.getGrupoDeDiscos().getVolumes().get(0).getTotal() - looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel());
        disco.setDataHoraCaptura(LocalDateTime.now());

        maquina.setCpu(cpu);
        maquina.setDisco(disco);
        maquina.setRam(ram);
    }
    public static native int getGPUUsage();

}
