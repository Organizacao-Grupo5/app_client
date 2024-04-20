package app.system.Components;

import app.system.Conversor;
import model.Bateria;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import util.logs.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class BateriaMonitoring {
    SystemInfo systemInfo = new SystemInfo();
    HardwareAbstractionLayer hardware = systemInfo.getHardware();
    Conversor conversor = new Conversor();
    public List<Bateria> monitorarBateria() {
        Logger.logInfo("Capturando dados da sua bateria.");
        return hardware.getPowerSources().stream()
                .map(power -> new Bateria(power.getAmperage(), power.getDeviceName(), power.getSerialNumber(),
                        power.getChemistry(), power.getName(), power.getVoltage(), "mAh",
                        conversor.formatarBytes(power.getCurrentCapacity()), power.getCycleCount(),
                        conversor.formatarBytes(power.getDesignCapacity()), power.getTimeRemainingInstant(),
                        power.getTimeRemainingEstimated(), power.getPowerUsageRate(), power.getTemperature(),
                        conversor.formatarBytes(power.getMaxCapacity()),
                        conversor.convertePorcentagem(power.getMaxCapacity(),
                                power.getRemainingCapacityPercent() * power.getMaxCapacity() / 100),
                        power.getManufactureDate().toString(), power.getManufacturer()))
                .collect(Collectors.toList());
    }
}
