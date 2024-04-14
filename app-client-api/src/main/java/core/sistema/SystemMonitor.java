package core.sistema;

import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

import java.util.List;

public class SystemMonitor {

    private SystemInfo systemInfo;
    private HardwareAbstractionLayer hardware;
    private OperatingSystem os;

    public SystemMonitor() {
        systemInfo = new SystemInfo();
        hardware = systemInfo.getHardware();
        os = systemInfo.getOperatingSystem();
    }

    public void monitorarCPU() {
        CentralProcessor processor = hardware.getProcessor();
        System.out.println("Monitoramento da CPU:");
        System.out.println("Identificador do Processador: " + processor.getProcessorIdentifier());
        System.out.println("Cache do Processador: " + processor.getProcessorCaches().toString().replace("[", "").replace("]", ""));
        System.out.println("Trocas de Contexto: " + processor.getContextSwitches());
        System.out.println("Interrupções: " + processor.getInterrupts());
        // System.out.println("Frequência Atual: " + (processor.getCurrentFreq() / 1000000000.0) + " GHz");
        System.out.println("Frequência Máxima: " + (processor.getMaxFreq() / 1000000000.0) + " GHz");
        System.out.println("Quantidade de Threads: " + processor.getLogicalProcessorCount());
        System.out.println("Quantidade de Núcleos Físicos: " + processor.getPhysicalProcessorCount());
        System.out.println("Quantidade de Pacotes Físicos: " + processor.getPhysicalPackageCount());
        System.out.println("Flags do Processador: " + processor.getFeatureFlags().toString().replace("[", "").replace("]", ""));
        //System.out.println("Utilização da CPU: " + String.format("%.2f", processor.getSystemCpuLoadBetweenTicks() * 100) + "%");
    }

    public void monitorarGPU() {
        List<GraphicsCard> graphicsCards = hardware.getGraphicsCards();
        System.out.println("Monitoramento da GPU:");
        for (int i = 0; i < graphicsCards.size(); i++) {
            GraphicsCard gpu = graphicsCards.get(i);
            System.out.println("Informações da GPU " + (i+1) + ":");
            System.out.println("  - Versão da GPU: " + gpu.getVersionInfo());
            System.out.println("  - ID do Dispositivo GPU: " + gpu.getDeviceId());
            System.out.println("  - Nome da GPU: " + gpu.getName());
            System.out.println("  - Fabricante da GPU: " + gpu.getVendor());
            System.out.println("  - VRAM da GPU: " + formatBytes(gpu.getVRam()));
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

    }

    public void monitorarHDD() {
        HWDiskStore[] diskStores = hardware.getDiskStores().toArray(new HWDiskStore[0]);
        System.out.println("Monitoramento do HDD:");

    }

    public void monitorarUSB() {
        System.out.println("Monitoramento de USB:");

    }

    public void monitorarNetwork() {
        System.out.println("Monitoramento de Rede:");

    }
}

