package service;

import app.system.SystemMonitor;
import model.CPU;
import model.HDD;
import model.SistemaOp;
import util.TablePrinter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceMonitoring {

    TablePrinter tablePrinter = new TablePrinter();
    SystemMonitor systemMonitor = new SystemMonitor();

    public void iniciarMonitoramento() {
        monitorarCPU();
        monitorarSO();
        monitorarHDD();
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

    public void monitorarSO(){
        SistemaOp sistemaOp = systemMonitor.monitorarSistemaOperacional();
        tablePrinter.printTable(Arrays.asList(
                Arrays.asList("", "Sistema Operacional"),
                Arrays.asList("Nome Sistema", sistemaOp.getSistemaOperacional()),
                Arrays.asList("Fabricante", sistemaOp.getFabricante()),
                Arrays.asList("Arquitetura", sistemaOp.getArquitetura()),
                Arrays.asList("Inicializado", sistemaOp.getInicializado().toString()),
                Arrays.asList("Permissão", sistemaOp.isPermissao() + ""),
                Arrays.asList("", sistemaOp.getTempoDeAtividade().toString()))
        );
    }

    public void monitorarHDD() {
        List<HDD> listaHDD = systemMonitor.monitorarHDD();
        listaHDD.forEach(hdd -> tablePrinter.printTable(
                Arrays.asList(
                        Arrays.asList("", "HDD"),
                        Arrays.asList("Nome", hdd.getNome()),
                        Arrays.asList("Serial", hdd.getSerial()),
                        Arrays.asList("Modelo", hdd.getModelo()),
                        Arrays.asList("Escritas", hdd.getEscritas().toString()),
                        Arrays.asList("Leituras", hdd.getLeituras().toString()),
                        Arrays.asList("Bytes de escrita", hdd.getBytesDeEscrita().toString()),
                        Arrays.asList("Bytes de leitura", hdd.getBytesDeLeitura().toString()),
                        Arrays.asList("Tamanho", hdd.getTamanho().toString()),
                        Arrays.asList("Tamanho atual", hdd.getTamanhoAtualDaFita().toString()),
                        Arrays.asList("Tempo de transferência", hdd.getTempoDeTransferencia().toString())
                )
        ));
    }


}
