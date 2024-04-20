package service;

import app.system.SystemMonitor;
import com.mysql.cj.util.StringUtils;
import exception.ExceptionMonitoring;
import model.CPU;
import model.HDD;
import model.SistemaOp;
import util.TablePrinter;
import util.logs.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceMonitoring {

    TablePrinter tablePrinter = new TablePrinter();
    SystemMonitor systemMonitor = new SystemMonitor();

    public void iniciarMonitoramento() {
        try {
            Logger.logInfo("Iniciando o monitoramento dos componentes");

            if (systemMonitor == null || tablePrinter == null) {
                throw new NullPointerException("O sistema de monitoramento ou o printer de tabela são nulos.");
            }

            monitorarCPU();
            monitorarSO();
            monitorarHDD();
        } catch (NullPointerException e) {
            Logger.logError("Erro ao iniciar monitoramento: ", e.getMessage(), e);
        } catch (ExceptionMonitoring e) {
            Logger.logError("Ocorreu um erro durante o monitoramento", e.getMessage(), e);
        }
    }

    public void monitorarCPU() throws ExceptionMonitoring{
        CPU cpu = systemMonitor.monitorarCPU();
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

    public void monitorarSO() throws ExceptionMonitoring{
        SistemaOp sistemaOp = systemMonitor.monitorarSistemaOperacional();
        if (sistemaOp == null) {
            Logger.logWarning("Sistema operacional não encontrado durante o monitoramento.");
            return;
        }
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

    public void monitorarHDD() throws ExceptionMonitoring{
        List<HDD> listaHDD = systemMonitor.monitorarHDD();
        if (StringUtils.isNullOrEmpty(listaHDD.toString())) {
            Logger.logWarning("Nenhum HDD encontrado durante o monitoramento.");
            return;
        }
        listaHDD.forEach(hdd -> {
            Logger.logInfo("Enviando informações para exibir a tabela do HDD %s no sistema de monitoramento no HDD ".formatted(hdd.getSerial()));
            tablePrinter.printTable(
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
        );});
    }


}
