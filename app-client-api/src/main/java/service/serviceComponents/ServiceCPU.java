package service.serviceComponents;

import model.CPU;
import service.ServiceMonitoring;
import util.logs.Logger;

import java.util.Arrays;

public class ServiceCPU extends ServiceMonitoring {

    public void exibirTabelaCPU(CPU cpu) {
        if (cpu == null) {
            Logger.logWarning("CPU não encontrada durante o monitoramento.");
            return;
        }
        tablePrinter.printTable(Arrays.asList(
                Arrays.asList("", "CPU"),
                Arrays.asList("Nome", cpu.getNome()),
                Arrays.asList("Fabricante", cpu.getFabricante()),
                Arrays.asList("Microarquitetura", cpu.getMicroarquitetura()),
                Arrays.asList("Identificador", cpu.getIdentificador()),
                Arrays.asList("ID CPU", cpu.getIdCpuLooca()),
                Arrays.asList("Número de CPUs lógicas", cpu.getNumeroDeCpusLogicas().toString()),
                Arrays.asList("Número de CPUs Físicas", cpu.getNumeroDeCpusFisicas().toString()),
                Arrays.asList("Número de pacotes físicos", cpu.getNumeroPacotesFisicos().toString()),
                Arrays.asList("Frequência", cpu.getFrequencia().toString()),
                Arrays.asList("Uso", cpu.getUso().toString()),
                Arrays.asList("Temperatura", cpu.getTemperatura().toString())
        ));
    }
}
