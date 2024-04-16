package core.sistema;

import com.github.britooo.looca.api.core.Looca;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

import java.util.List;


public class SystemMonitor {

    private SystemInfo systemInfo;
    private HardwareAbstractionLayer hardware;
    private Looca looca;
    private OperatingSystem os;

    public SystemMonitor() {
        systemInfo = new SystemInfo();
        hardware = systemInfo.getHardware();
        os = systemInfo.getOperatingSystem();
        looca = new Looca();
    }

    public void monitorarCPU() {
        CentralProcessor processor = hardware.getProcessor();
        System.out.println("Monitoramento da CPU:");
        System.out.println("    - Identificador do Processador: " + processor.getProcessorIdentifier().getName());
        System.out.println("    - Cache do Processador: " + processor.getProcessorIdentifier().getProcessorID());
        System.out.println("    - Trocas de Contexto: " + processor.getContextSwitches());
        System.out.println("    - Interrupções: " + processor.getInterrupts());
        System.out.println("    - Frequência Máxima: " + String.format("%.2f GHz", (double) processor.getMaxFreq() / 1e9));
        System.out.println("    - Quantidade de Threads: " + processor.getLogicalProcessorCount());
        System.out.println("    - Quantidade de Núcleos Físicos: " + processor.getPhysicalProcessorCount());
        System.out.println("    - Quantidade de Pacotes Físicos: " + processor.getPhysicalPackageCount());
        System.out.println("______________________________________________________________________________________");
    }

    public void monitorarGPU() {
        List<GraphicsCard> graphicsCards = hardware.getGraphicsCards();
        for (int i = 0; i < graphicsCards.size(); i++) {
            System.out.println("Monitoramento da GPU:");
            GraphicsCard gpu = graphicsCards.get(i);
            System.out.println("Informações da GPU " + (i + 1) + ":");
            System.out.println("  - Versão da GPU: " + gpu.getVersionInfo());
            System.out.println("  - ID do Dispositivo GPU: " + gpu.getDeviceId());
            System.out.println("  - Nome da GPU: " + gpu.getName());
            System.out.println("  - Fabricante da GPU: " + gpu.getVendor());
            System.out.println("  - VRAM da GPU: " + formatBytes(gpu.getVRam()));
            System.out.println("______________________________________________________________________________________");
        }
    }

    private String formatBytes(long bytes) {
        String[] units = {"B", "KB", "MB", "GB", "TB"};
        int unitIndex = 0;
        double size = bytes;
        while (size >= 1024 && unitIndex < units.length - 1) {
            size /= 1024;
            unitIndex++;
        }
        return String.format("%.2f %s", size, units[unitIndex]);
    }

    public void monitorarRAM() {
        GlobalMemory memory = hardware.getMemory();
        System.out.println("Monitoramento da RAM:");
        System.out.println("    - RAM disponível: " + FormatUtil.formatBytes(memory.getAvailable()));
        memory.getPhysicalMemory().forEach(e -> {
            System.out.println("Memória física: " + FormatUtil.formatBytes(e.getCapacity()));
        });
        System.out.println("    - RAM Total: " + FormatUtil.formatBytes(memory.getTotal()));
        System.out.println("    - Tamanho total dos processos em execução: " + FormatUtil.formatBytes(memory.getTotal() - memory.getAvailable()));
        System.out.println("    - Tamanho da página: " + FormatUtil.formatBytes(memory.getPageSize()));
        System.out.println("    - Memória virtual: " + FormatUtil.formatBytes(memory.getVirtualMemory().getVirtualMax()));
        System.out.println("______________________________________________________________________________________");
    }

    public void monitorarHDD() {
        List<HWDiskStore> diskStores = hardware.getDiskStores();
        System.out.println("Monitoramento do HDD:");
        for (HWDiskStore disk : diskStores) {
            System.out.println("    - Nome HDD: " + disk.getName());
            System.out.println("    - Modelo HDD: " + disk.getModel());
            System.out.println("    - Serial HDD: " + disk.getSerial());
            disk.getPartitions().forEach(e -> {
                System.out.println("    - Nome da Partição: " + e.getName());
                System.out.println("        - Identificador da Partição: " + e.getIdentification());
                System.out.println("        - Tipo da Partição: " + e.getType());
                System.out.println("        - UUID da Partição: " + e.getUuid());
                System.out.println("        - Tamanho da Partição: " + FormatUtil.formatBytes(e.getSize()));
            });
            System.out.println("    - Tamanho HDD: " + FormatUtil.formatBytes(disk.getSize()));
            System.out.println("    - Tamanho da fila atual do HDD: " + disk.getCurrentQueueLength());
            System.out.println("    - Bytes lidos do HDD: " + FormatUtil.formatBytes(disk.getReadBytes()));
            System.out.println("    - Leituras do HDD: " + disk.getReads());
            System.out.println("    - Timestamp do HDD: " + disk.getTimeStamp());
            System.out.println("    - Tempo de transferência do HDD: " + disk.getTransferTime());
            System.out.println("    - Escritas do HDD: " + disk.getWrites());
            System.out.println("______________________________________________________________________________________");
        }
    }

    public void monitorarUSB() {
        List<UsbDevice> listaUsbTrue = hardware.getUsbDevices(true);
        listaUsbTrue.forEach(e -> {
            System.out.println("Monitoramento das entradas USB:");
            System.out.println("    - Entrada USB Nome: " + e.getName());
            System.out.println("    - Entrada USB Vendor ID: " + e.getVendorId());
            System.out.println("    - Entrada USB Vendor: " + e.getVendor());
            System.out.println("    - Entrada USB Product ID: " + e.getProductId());
            System.out.println("    - Entrada USB Serial Number: " + e.getSerialNumber());
            System.out.println("    - Entrada USB Unique Id Device: " + e.getUniqueDeviceId());
            e.getConnectedDevices().forEach(f -> {
                System.out.println("    - Dispositivos conectados:");
                System.out.println("        - Dispositivos USB Nome: " + f.getName());
                System.out.println("        - Dispositivos USB Vendor ID: " + f.getVendorId());
                System.out.println("        - Dispositivos USB Vendor: " + f.getVendor());
                System.out.println("        - Dispositivos USB Product ID: " + f.getProductId());
                System.out.println("        - Dispositivos USB Serial Number: " + f.getSerialNumber());
                System.out.println("        - Dispositivos USB Unique Id Device: " + f.getUniqueDeviceId());
            });
            System.out.println("______________________________________________________________________________________");
        });
    }

    public void monitorarBateria() {
        List<PowerSource> listaBateria = hardware.getPowerSources();
        listaBateria.forEach(e -> {
            System.out.println("Monitoramento de bateria:");
            System.out.println("    - Nome bateria: " + e.getName());
            System.out.println("    - Química da bateria: " + e.getChemistry());
            System.out.println("    - Número serial da bateria: " + e.getSerialNumber());
            System.out.println("    - Nome do dispositivo: " + e.getDeviceName());
            System.out.println("    - Amperagem: " + e.getAmperage() + " mA");
            System.out.println("    - Unidade de capacidade: " + e.getCapacityUnits());
        });
        System.out.println("______________________________________________________________________________________");
    }

    public void monitorarDisplay() {
        List<Display> listaDisplay = hardware.getDisplays();
        listaDisplay.forEach(e -> {
            System.out.println("Monitoramento do Display:");
            System.out.println("    - Display Edid: " + e.getEdid());
        });
        System.out.println("______________________________________________________________________________________");
    }

    public void monitorarVolumeLogico() {
        List<LogicalVolumeGroup> listaVolumesLogicos = hardware.getLogicalVolumeGroups();
        listaVolumesLogicos.forEach(e -> {
            System.out.println("Monitoramento do Volume Lógico:");
            System.out.println("    - Nome grupo volume Lógico: " + e.getName());
            e.getPhysicalVolumes().forEach(f -> {
                System.out.println("        - Volume físico: " + f);
            });
        });
        System.out.println("______________________________________________________________________________________");
    }

    public void monitorarSensores() {
        Sensors sensor = hardware.getSensors();
        System.out.println("Monitoramento do Sensor:");
        System.out.println("    - Temperatura CPU: " + sensor.getCpuTemperature() + " °C");
        System.out.println("    - Voltagem CPU: " + sensor.getCpuVoltage() + " V");
        System.out.println("    - Fan Speed CPU: " + sensor.getFanSpeeds() + " RPM");
        System.out.println("______________________________________________________________________________________");
    }

    public void monitorarSoundCard() {
        List<SoundCard> soundCards = hardware.getSoundCards();
        soundCards.forEach(e -> {
            System.out.println("Monitoramento Sound Card:");
            System.out.println("    - Nome Sound Card: " + e.getName());
            System.out.println("    - Codec Sound Card: " + e.getCodec());
            System.out.println("    - Driver Version Sound Card: " + e.getDriverVersion());
        });
        System.out.println("______________________________________________________________________________________");
    }

    public void monitorarSistemaOperacional() {
        OperatingSystem sistemaOperacional = systemInfo.getOperatingSystem();
        System.out.println("Monitoramento do Sistema Operacional:");
        System.out.println("    - Família do Sistema Operacional: " + sistemaOperacional.getFamily());
        System.out.println("    - Fabricante do Sistema Operacional: " + sistemaOperacional.getManufacturer());
        System.out.println("    - Arquitetura do Sistema Operacional: " + sistemaOperacional.getBitness());
        System.out.println("    - Sistema de Arquivos do Sistema Operacional: " + sistemaOperacional.getFileSystem());
        System.out.println("    - Tempo de atividade do Sistema Operacional: " + FormatUtil.formatElapsedSecs(sistemaOperacional.getSystemUptime()));
        System.out.println("    - Processo atual do Sistema Operacional: " + sistemaOperacional.getCurrentProcess());
        System.out.println("    - Thread atual do Sistema Operacional: " + sistemaOperacional.getCurrentThread());
        System.out.println("    - Status de protocolo de Internet do Sistema Operacional: " + sistemaOperacional.getInternetProtocolStats());
        sistemaOperacional.getDesktopWindows(true).forEach(e -> {
            System.out.println("    - Janelas do Desktop: ");
            System.out.println("        - Título da janela: " + e.getTitle());
            System.out.println("        - Comando da janela: " + e.getCommand());
            System.out.println("        - Ordem da janela: " + e.getOrder());
            System.out.println("        - ID da janela: " + e.getWindowId());
            System.out.println("        - Localização e tamanho da janela: " + e.getLocAndSize());
            System.out.println("        - ID do processo proprietário da janela: " + e.getOwningProcessId());
        });
        System.out.println("______________________________________________________________________________________");
    }

    public void monitorarRede() {
        List<NetworkIF> networkIFs = hardware.getNetworkIFs();
        System.out.println("Monitoramento da Rede:");
        for (NetworkIF net : networkIFs) {
            System.out.println("    - Interface de Rede: " + net.getDisplayName());
            System.out.println("        - Endereço IP: " + net.getIPv4addr());
            System.out.println("        - Endereço MAC: " + net.getMacaddr());
            System.out.println("        - Bytes Recebidos: " + FormatUtil.formatBytes(net.getBytesRecv()));
            System.out.println("        - Bytes Enviados: " + FormatUtil.formatBytes(net.getBytesSent()));
            System.out.println("        - Velocidade de Transmissão: " + FormatUtil.formatValue(net.getSpeed(), "bps"));
            System.out.println("______________________________________________________________________________________");
        }
    }

    public void monitorarProcessos() {
        List<OSProcess> processes = os.getProcesses();
        System.out.println("Monitoramento de Processos em Execução:");
        for (OSProcess process : processes) {
            System.out.println("    - PID: " + process.getProcessID() + ", Nome: " + process.getName() +
                    ", CPU: " + String.format("%.2f", 100d * (process.getKernelTime() + process.getUserTime()) / process.getUpTime()) + "%");
        }
        System.out.println("______________________________________________________________________________________");
    }

}