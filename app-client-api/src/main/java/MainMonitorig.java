import core.sistema.SystemMonitor;

public class MainMonitorig {
    public static void main(String[] args) {
        SystemMonitor systemMonitor = new SystemMonitor();
        systemMonitor.monitorarCPU();
        systemMonitor.monitorarGPU();
        systemMonitor.monitorarHDD();
        systemMonitor.monitorarRAM();
        systemMonitor.monitorarDisplay();
        systemMonitor.monitorarSensores();
        systemMonitor.monitorarSoundCard();
        systemMonitor.monitorarUSB();
        systemMonitor.monitorarVolumeLogico();
        systemMonitor.monitorarSistemaOperacional();
        systemMonitor.monitorarBateria();
        systemMonitor.monitorarRede();
        systemMonitor.monitorarProcessos();
    }
}
