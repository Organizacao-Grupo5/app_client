package service;

import app.system.SystemMonitor;
import com.mysql.cj.util.StringUtils;
import exception.ExceptionMonitoring;
import model.*;
import util.TablePrinter;
import util.logs.Logger;
import java.util.*;
import java.util.stream.Collectors;

public class ServiceMonitoring {

    private final TablePrinter tablePrinter = new TablePrinter();

    public void exibirTabelas(CPU cpu, List<GPU> gpus, List<HDD> hdd, SistemaOp so, MemoriaRam ram, List<APP> apps, List<ConexaoUSB> usb, List<Bateria> bateria, List<Volume> volumes) throws Exception {
        Logger.logInfo("Iniciando o monitoramento dos componentes");

        if (tablePrinter == null) {
            throw new NullPointerException("O sistema de monitoramento ou o printer de tabela falharam.");
        }
        exibirTabelaSO(so);
        exibirTabelaCPU(cpu);
        exibirTabelaHDD(hdd);
        exibirTabelaConexaoUSB(usb);
        exibirTabelaBateria(bateria);
        exibirTabelaMemoriaRAM(ram);
        exibirTabelaGPU(gpus);
        exibirTabelaVolume(volumes);
        exibirTabelaAPP(apps);
    }
    public void exibirTabelaGPU(List<GPU> gpus) {
        if (gpus == null || gpus.isEmpty()) {
            Logger.logWarning("Nenhuma GPU encontrada durante o monitoramento.");
            return;
        }

        for (int i = 0; i < gpus.size(); i++) {
            GPU gpu = gpus.get(i);
            List<List<String>> gpuData = Arrays.asList(
                    Arrays.asList("", "GPU " + (i + 1)),
                    Arrays.asList("ID", String.valueOf(gpu.getIdGpu())),
                    Arrays.asList("Nome", gpu.getNome()),
                    Arrays.asList("Fabricante", gpu.getFabricante()),
                    Arrays.asList("Versão", gpu.getVersao()),
                    Arrays.asList("ID Device", gpu.getIdDevice()),
                    Arrays.asList("VRAM", String.valueOf(gpu.getvRam()))
            );

            tablePrinter.printTable(gpuData);
        }
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

    public void exibirTabelaMemoriaRAM(MemoriaRam memoriaRam) {
        if (memoriaRam == null) {
            Logger.logWarning("Memória RAM não encontrada durante o monitoramento.");
            return;
        }

        List<List<String>> memoriaRamData = Arrays.asList(
                Arrays.asList("", "Memória RAM"),
                Arrays.asList("ID", String.valueOf(memoriaRam.getIdMemoriaRAM())),
                Arrays.asList("Memória Disponível", String.valueOf(memoriaRam.getMemoriaDisponivel())),
                Arrays.asList("Memória em Uso", String.valueOf(memoriaRam.getMemoriaEmUso())),
                Arrays.asList("Memória Total", String.valueOf(memoriaRam.getMemoriaTotal()))
        );

        tablePrinter.printTable(memoriaRamData);
    }

    public void exibirTabelaBateria(List<Bateria> baterias) {
        if (baterias == null || baterias.isEmpty()) {
            Logger.logWarning("Nenhuma bateria encontrada durante o monitoramento.");
            return;
        }

        for (int i = 0; i < baterias.size(); i++) {
            Bateria bateria = baterias.get(i);
            List<List<String>> bateriaData = Arrays.asList(
                    Arrays.asList("", "Bateria " + (i + 1)),
                    Arrays.asList("ID", String.valueOf(bateria.getIdBateria())),
                    Arrays.asList("Amperagem", String.valueOf(bateria.getAmperagem())),
                    Arrays.asList("Nome do Dispositivo", bateria.getNomeDispositivo()),
                    Arrays.asList("Número Serial", bateria.getNumeroSerial()),
                    Arrays.asList("Química", bateria.getQuimica()),
                    Arrays.asList("Nome", bateria.getNome()),
                    Arrays.asList("Voltagem", String.valueOf(bateria.getVoltagem())),
                    Arrays.asList("Unidades de Capacidade", bateria.getUnidadesCapacidade()),
                    Arrays.asList("Capacidade Atual", String.valueOf(bateria.getCapacidadeAtual())),
                    Arrays.asList("Ciclos", String.valueOf(bateria.getCiclos())),
                    Arrays.asList("Capacidade Design", String.valueOf(bateria.getCapacidadeDesign())),
                    Arrays.asList("Tempo Restante Instantâneo", String.valueOf(bateria.getTempoRestanteInstantaneo())),
                    Arrays.asList("Tempo Restante Estimado", String.valueOf(bateria.getTempoRestanteEstimado())),
                    Arrays.asList("Taxa de Uso de Energia", String.valueOf(bateria.getTaxaUsoEnergia())),
                    Arrays.asList("Temperatura", String.valueOf(bateria.getTemperatura())),
                    Arrays.asList("Capacidade Máxima", String.valueOf(bateria.getCapacidadeMaxima())),
                    Arrays.asList("Percentual Capacidade Restante", String.valueOf(bateria.getPercentualCapacidadeRestante())),
                    Arrays.asList("Data de Fabricação", bateria.getDataFabricacao()),
                    Arrays.asList("Fabricante", bateria.getFabricante())
            );

            tablePrinter.printTable(bateriaData);
        }
    }

    private void exibirTabelaAPP(List<APP> apps) {

        if (apps == null || apps.isEmpty()) {
            Logger.logWarning("Nenhum aplicativo encontrado durante o monitoramento.");
            return;
        }

        apps.forEach(app -> {
            List<List<String>> appData = Arrays.asList(
                   Arrays.asList("", "APP"),
                   Arrays.asList("Nome", app.getNome()),
                   Arrays.asList("Comando", app.getComando()),
                   Arrays.asList("Data hora captura", app.getDataHoraCaptura().toString()),
                   Arrays.asList("PID", app.getPid().toString()),
                   Arrays.asList("Id Janela", app.getJanelaID().toString()),
                   Arrays.asList("Localização e tamanho", app.getLocalizacaoEtamanho().toString())
            );
            tablePrinter.printTable(appData);
        });
    }


    public void exibirTabelaConexaoUSB(List<ConexaoUSB> conexoesUSB) {
        if (conexoesUSB == null || conexoesUSB.isEmpty()) {
            Logger.logWarning("Nenhuma conexão USB encontrada durante o monitoramento.");
            return;
        }

        conexoesUSB.forEach(usb -> {
            List<List<String>> usbData = Arrays.asList(
                    Arrays.asList("", "Conexão USB"),
                    Arrays.asList("Nome USB", usb.getNomeUsb()),
                    Arrays.asList("Fornecedor", usb.getFornecedor()),
                    Arrays.asList("Data hora captura", usb.getDataHoraCaptura().toString()),
                    Arrays.asList("Id Fornecedor", usb.getIdFornecedor().toString()),
                    Arrays.asList("Número Série", usb.getNumeroSerie().toString()),
                    Arrays.asList("Id Dispositivo USB exclusivo", usb.getIdDispositivoUSBExclusivo().toString()),
                    Arrays.asList("Id Produto", usb.getIdProduto().toString())
            );
            tablePrinter.printTable(usbData);
        });
    }


    public void exibirTabelaVolume(List<Volume> volumes) {
        if (volumes == null || volumes.isEmpty()) {
            Logger.logWarning("Nenhum volume encontrado durante o monitoramento.");
            return;
        }

        for (int i = 0; i < volumes.size(); i++) {
            Volume volume = volumes.get(i);
            List<List<String>> volumeData = Arrays.asList(
                    Arrays.asList("", "Volume " + (i + 1)),
                    Arrays.asList("Nome", volume.getNome()),
                    Arrays.asList("Volume", volume.getVolume()),
                    Arrays.asList("Disponível", String.valueOf(volume.getDisponivel())),
                    Arrays.asList("Total", String.valueOf(volume.getTotal())),
                    Arrays.asList("Tipo", volume.getTipo()),
                    Arrays.asList("UUID", volume.getUuid()),
                    Arrays.asList("Ponto de Montagem", volume.getPontoDeMontagem())
            );

            tablePrinter.printTable(volumeData);
        }
    }

}
