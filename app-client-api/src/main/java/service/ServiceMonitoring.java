package service;

import app.system.SystemMonitor;
import model.CPU;
import util.TablePrinter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceMonitoring {

    TablePrinter tablePrinter = new TablePrinter();
    SystemMonitor systemMonitor = new SystemMonitor();

    public void iniciarMonitoramento() {
        monitorarCPU();
    }

    public void monitorarCPU() {
        CPU cpu = systemMonitor.monitorarCPU();

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
