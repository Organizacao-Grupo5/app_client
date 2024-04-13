package core.sistema;

import com.github.britooo.looca.api.core.Looca;
import exception.ExceptionMonitoring;
import model.*;
import oshi.SystemInfo;
import oshi.hardware.*;
import service.ServiceMonitoring;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Monitoramento {
    private static final Looca looca = new Looca();

    private static final SystemInfo SYSTEM_INFO = new SystemInfo();
    private static final HardwareAbstractionLayer HARDWARE = SYSTEM_INFO.getHardware();
    SystemInfo systemInfo = new SystemInfo();

    private static final Logger LOGGER = Logger.getLogger(Monitoramento.class.getName());

    private static final ServiceMonitoring serviceMonitoring = new ServiceMonitoring();

    public String getIp() throws UnknownHostException {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            System.err.println("Erro ao obter o endereço IP: " + e.getMessage());
            throw e;
        }
    }

    public Map<String, Object> iniarMonitoramento(Maquina maquina, Usuario usuario) throws ExceptionMonitoring {
        try {
            CPU cpu = this.inicializarCPU();
            GPU gpu = this.inicializarGPU();

            // FALTA A LÓGICA PARA O CASO DE MAIS COMPONENTES DE HDD E SSD
            HDD hdd = this.inicializarHDD();
            MemoriaRam memoriaRam = this.inicializarRAM();
            APP app = this.inicializarAPP();
            ConexaoUSB conexaoUSB = this.inicializarConexaoUSB();

            Map<String, Object> hardwares = new HashMap<>();

            hardwares.put("CPU", cpu);
            hardwares.put("GPU", gpu);
            hardwares.put("HDD", hdd);
            hardwares.put("RAM", memoriaRam);

            return hardwares;

        }catch (Exception e){
            throw new ExceptionMonitoring("Houve um problema ao monitorar um dos componentes: " + e);
        }
    }

    public CPU inicializarCPU() throws ExceptionMonitoring {
        try {
            CentralProcessor processor = HARDWARE.getProcessor();

            CPU cpu = new CPU();
            cpu.setModelo(processor.getProcessorIdentifier().getName());
            cpu.setNumeroSerie(processor.getProcessorIdentifier().getProcessorID());
            cpu.setFabricante(processor.getProcessorIdentifier().getVendor());
            cpu.setArquitetura(processor.getProcessorIdentifier().getIdentifier());
            cpu.setCache(String.valueOf(processor.getProcessorCaches().get(0).getCacheSize()));

            return cpu;
        } catch (Exception e){
            throw new ExceptionMonitoring("Ocorreu um erro no monitoramento da CPU: " + e);
        }
    }
    public HDD inicializarHDD() throws ExceptionMonitoring {
        try {
            HWDiskStore[] diskStores = HARDWARE.getDiskStores().toArray(new HWDiskStore[0]);
            HDD hdd = new HDD();
            hdd.setCapacidadeTotal(Double.parseDouble(String.valueOf(diskStores[0].getSize())));
            hdd.setNumeroParticoes(diskStores[0].getPartitions().size());
            hdd.setStatusSaude(diskStores[0].getModel());

            return hdd;
        } catch (Exception e){
            throw new ExceptionMonitoring("Ocorreu um erro no monitoramento da HDD: " + e);
        }
    }
    public GPU inicializarGPU() throws ExceptionMonitoring {
        try {
            GraphicsCard[] graphicsCards = HARDWARE.getGraphicsCards().toArray(new GraphicsCard[0]);
            GPU gpu = new GPU();
            gpu.setModelo(graphicsCards[0].getName());
            gpu.setMemoria(Double.parseDouble(String.valueOf(graphicsCards[0].getVRam())));
            gpu.setUtilizacao(graphicsCards[0].getVRam());
            gpu.setVersaoDriver(graphicsCards[0].getVendor());
            return gpu;
        } catch (Exception e){
            throw new ExceptionMonitoring("Ocorreu um erro no monitoramento da GPU: " + e);
        }
    }
    public MemoriaRam inicializarRAM() throws ExceptionMonitoring {
        try {
            GlobalMemory memory = HARDWARE.getMemory();
            MemoriaRam memoriaRAM = new MemoriaRam();
            memoriaRAM.setCapacidadeTotal(Double.parseDouble(String.valueOf(memory.getTotal())));
            memoriaRAM.setNumeroModulo(memory.getPhysicalMemory().size());
            memoriaRAM.setPorcentagemUtilizada(memory.getAvailable() / (double) memory.getTotal());
            return memoriaRAM;
        } catch (Exception e){
            throw new ExceptionMonitoring("Ocorreu um erro no monitoramento da Memória RAM: " + e);
        }
    }

    // Verificar como coletar ainda
    public ConexaoUSB inicializarConexaoUSB() throws ExceptionMonitoring {
        try {
            ConexaoUSB conexaoUSB = new ConexaoUSB();
            conexaoUSB.setTotalPortas(4);
            conexaoUSB.setTipoConector("Tipo padrão");
            conexaoUSB.setDeteccaoDispositivo("Detecção padrão");
            conexaoUSB.setEnergiaPorta("Energia padrão");
            conexaoUSB.setHubsConectados("Hubs padrão");
            conexaoUSB.setDispositivoConectado("Dispositivo padrão");

            return conexaoUSB;
        } catch (Exception e){
            throw new ExceptionMonitoring("Ocorreu um erro no monitoramento da Conexão USB: " + e);
        }
    }

    // Verificar como coletar ainda
    public APP inicializarAPP() throws ExceptionMonitoring {
        try {
            APP app = new APP();
            app.setNomeApp("App padrão");
            app.setDtInstalacao(new Date());
            app.setUltimaDtInstalacao(new Date());
            app.setTamanhoAplicativo("Tamanho padrão");

            return app;
        } catch (Exception e){
            throw new ExceptionMonitoring("Ocorreu um erro no monitoramento do APP: " + e);
        }
    }
}
