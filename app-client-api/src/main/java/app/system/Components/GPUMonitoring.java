package app.system.Components;

import app.system.Conversor;
import model.GPU;
import oshi.SystemInfo;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;
import util.logs.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class GPUMonitoring {
    SystemInfo systemInfo = new SystemInfo();
    HardwareAbstractionLayer hardware = systemInfo.getHardware();
    Conversor conversor = new Conversor();

    public List<GPU> monitorarGPU() {
        Logger.logInfo("Capturando dados da sua GPU");
        List<GraphicsCard> graphicsCards = hardware.getGraphicsCards();

        if (graphicsCards == null || graphicsCards.isEmpty()) {
            Logger.logWarning("Dados da GPU não puderam ser capturados: nenhuma placa gráfica encontrada.");
        }
        return hardware.getGraphicsCards().stream().map(gpu -> new GPU(gpu.getName(), gpu.getVendor(),
                        gpu.getVersionInfo(), gpu.getDeviceId(), conversor.formatarBytes(gpu.getVRam())))
                .collect(Collectors.toList());
    }
}
