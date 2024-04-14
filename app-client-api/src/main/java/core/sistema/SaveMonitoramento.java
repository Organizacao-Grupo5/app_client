package core.sistema;

import com.github.britooo.looca.api.core.Looca;
import exception.ExceptionMonitoring;
import model.*;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import service.ServiceMonitoring;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.logging.Logger;

public class SaveMonitoramento {
    private static final Looca looca = new Looca();

    private static final SystemInfo SYSTEM_INFO = new SystemInfo();
    private static final HardwareAbstractionLayer HARDWARE = SYSTEM_INFO.getHardware();
    private static final Sensors SENSORS = HARDWARE.getSensors();

    SystemInfo systemInfo = new SystemInfo();

    private static final Logger LOGGER = Logger.getLogger(SaveMonitoramento.class.getName());

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
            MemoriaRam memoriaRam = this.inicializarRAM();

            Map<String, Object> hardwares = new HashMap<>();

            hardwares.put("CPU", cpu);
            hardwares.put("GPU", gpu);
            hardwares.put("RAM", memoriaRam);

            return hardwares;

        } catch (Exception e) {
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

            cpu.setTemperaturaComponente(SENSORS.getCpuTemperature());
            cpu.setVelocidadeComponente(SENSORS.getCpuVoltage());

            return cpu;
        } catch (Exception e) {
            throw new ExceptionMonitoring("Ocorreu um erro no monitoramento da CPU: " + e);
        }
    }

    public List<HDD> inicializarHDD() throws ExceptionMonitoring {
        HWDiskStore[] diskStores = HARDWARE.getDiskStores().toArray(new HWDiskStore[0]);
        List<HDD> listaHDD = new ArrayList<>();

        try {
            for (int i = 0; i < diskStores.length; i++) {
                HDD hdd = criarHDD(i);
                listaHDD.add(hdd);
            }

            return listaHDD;
        } catch (Exception e) {
            throw new ExceptionMonitoring("Ocorreu um erro no monitoramento da HDD: " + e);
        }
    }

    public HDD criarHDD(Integer i) {
        HWDiskStore[] diskStores = HARDWARE.getDiskStores().toArray(new HWDiskStore[0]);
        HDD hdd = new HDD();
        hdd.setCapacidadeTotal(diskStores[i].getSize());
        hdd.setNumeroParticoes(diskStores[i].getPartitions().size());
        hdd.setStatusSaude("A verificar");

        return hdd;
    }

    public GPU inicializarGPU() throws ExceptionMonitoring {
        try {
            GraphicsCard[] graphicsCards = HARDWARE.getGraphicsCards().toArray(new GraphicsCard[0]);
            GPU gpu = new GPU();
            gpu.setModelo(graphicsCards[0].getName());
            gpu.setMemoria(graphicsCards[0].getVRam());
            gpu.setUtilizacao(graphicsCards[0].getVRam());
            gpu.setVersaoDriver(graphicsCards[0].getVendor());

            return gpu;
        } catch (Exception e) {
            throw new ExceptionMonitoring("Ocorreu um erro no monitoramento da GPU: " + e);
        }
    }

    public MemoriaRam inicializarRAM() throws ExceptionMonitoring {
        try {
            GlobalMemory memory = HARDWARE.getMemory();
            MemoriaRam memoriaRAM = new MemoriaRam();
            memoriaRAM.setCapacidadeTotal(memory.getTotal());
            memoriaRAM.setNumeroModulo(memory.getPhysicalMemory().size());
            memoriaRAM.setPorcentagemUtilizada(((memory.getTotal() - memory.getAvailable()) / (double) memory.getTotal()) * 100);
            return memoriaRAM;
        } catch (Exception e) {
            throw new ExceptionMonitoring("Ocorreu um erro no monitoramento da Memória RAM: " + e);
        }
    }

    public List<ConexaoUSB> inicializarConexaoUSB() throws ExceptionMonitoring {
        List<ConexaoUSB> listaUSB = new ArrayList<>();
        try {
            for (int i = 0; i < HARDWARE.getUsbDevices(true).size(); i++) {
                listaUSB.add(criarConexao(i, true));
            }
            for (int i = 0; i < HARDWARE.getUsbDevices(false).size(); i++) {
                listaUSB.add(criarConexao(i, false));
            }
        } catch (Exception e) {
            throw new ExceptionMonitoring("Ocorreu um erro no monitoramento da Conexão USB: " + e);
        }
        return listaUSB;
    }

    public ConexaoUSB criarConexao(Integer i, boolean status) {
        ConexaoUSB conexaoUSB = new ConexaoUSB();
        conexaoUSB.setTotalPortas(HARDWARE.getUsbDevices(true).size() + HARDWARE.getUsbDevices(false).size());
        conexaoUSB.setTipoConector("Tipo padrão");
        conexaoUSB.setDeteccaoDispositivo("Detecção padrão");
        conexaoUSB.setEnergiaPorta("Energia");
        conexaoUSB.setHubsConectados("Hubs padrão");
        conexaoUSB.setDispositivoConectado(HARDWARE.getUsbDevices(status).get(i).getName());

        return conexaoUSB;
    }

    public List<APP> inicializarAPP() throws ExceptionMonitoring {
        List<APP> listaAPPs = new ArrayList<>();
        try {
            OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
            List<OSProcess> processes = operatingSystem.getProcesses();
            for (OSProcess process : processes) {
                listaAPPs.add(criarAPP(process));
            }
        } catch (Exception e) {
            throw new ExceptionMonitoring("Ocorreu um erro no monitoramento do APP: " + e);
        }
        return listaAPPs;
    }

    //Analise ainda
    public APP criarAPP(OSProcess process){

        APP app = new APP();
        app.setNomeApp(process.getName());
        app.setDtInstalacao(null);
        app.setUltimaDtInstalacao(new Date());
        app.setTamanhoAplicativo("Tamanho padrão");

        return app;
    }

    public void exibirMonitoramento() {
        System.out.println("""
                CPU
                
                
                """);
    }
}
