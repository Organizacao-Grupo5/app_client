package service;

import app.system.SystemMonitor;
import com.mysql.cj.util.StringUtils;
import exception.ExceptionMonitoring;
import model.*;
import util.TablePrinter;
import util.logs.Logger;
import java.util.*;

public class ServiceMonitoring {

    private final TablePrinter tablePrinter = new TablePrinter();

    public void exibirTabelas(CPU cpu, List<GPU> gpus, List<HDD> hdd, SistemaOp so, MemoriaRam ram, List<APP> apps, List<ConexaoUSB> usb, List<Bateria> bateria, List<Volume> volumes) throws Exception {
        Logger.logInfo("Iniciando o monitoramento dos componentes");

        if (tablePrinter == null) {
            throw new NullPointerException("O sistema de monitoramento ou o printer de tabela falharam.");
        }

        exibirTabelaCPU(cpu);
        exibirTabelaSO(so);
        exibirTabelaHDD(hdd);
    }

    private void exibirTabelaCPU(CPU cpu) {
        if (cpu == null) {
            Logger.logWarning("CPU não encontrada durante o monitoramento.");
            return;
        }

        List<List<String>> cpuData = Arrays.asList(
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
        );

        tablePrinter.printTable(cpuData);
    }

    private void exibirTabelaSO(SistemaOp sistemaOp) {
        if (sistemaOp == null) {
            Logger.logWarning("Sistema operacional não encontrado durante o monitoramento.");
            return;
        }

        List<List<String>> soData = Arrays.asList(
                Arrays.asList("", "Sistema Operacional"),
                Arrays.asList("Nome Sistema", Optional.ofNullable(sistemaOp.getSistemaOperacional()).orElse("VALOR NÃO ENCONTRADO")),
                Arrays.asList("Fabricante", Optional.ofNullable(sistemaOp.getFabricante()).orElse("VALOR NÃO ENCONTRADO")),
                Arrays.asList("Arquitetura", Optional.ofNullable(sistemaOp.getArquitetura()).orElse("VALOR NÃO ENCONTRADO")),
                Arrays.asList("Inicializado", Optional.ofNullable(sistemaOp.getInicializado()).map(Object::toString).orElse("VALOR NÃO IDENTIFICADO")),
                Arrays.asList("Permissão", Optional.ofNullable(sistemaOp.isPermissao()).map(Object::toString).orElse("VALOR NÃO ENCONTRADO")),
                Arrays.asList("Tempo de atividade", Optional.ofNullable(sistemaOp.getTempoDeAtividade()).orElse("VALOR NÃO IDENTIFICADO"))
        );

        tablePrinter.printTable(soData);
    }

    private void exibirTabelaHDD(List<HDD> listaHDD) {
        if (StringUtils.isNullOrEmpty(listaHDD.toString())) {
            Logger.logWarning("Nenhum HDD encontrado durante o monitoramento.");
            return;
        }

        listaHDD.forEach(hdd -> {
            Logger.logInfo("Enviando informações para exibir a tabela do HDD %s no sistema de monitoramento no HDD ".formatted(hdd.getSerial()));

            List<List<String>> hddData = Arrays.asList(
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
            );

            tablePrinter.printTable(hddData);
        });
    }
}
