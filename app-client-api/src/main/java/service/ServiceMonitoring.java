package service;

import app.system.SystemMonitor;
import com.itextpdf.text.pdf.PdfAction;
import com.mysql.cj.util.StringUtils;
import exception.ExceptionMonitoring;
import model.*;
import util.CreatePDFInfos;
import util.TablePrinter;
import util.logs.Logger;

import java.awt.geom.GeneralPath;
import java.util.*;
import java.util.stream.Collectors;

public class ServiceMonitoring {

    public final TablePrinter tablePrinter = new TablePrinter();
    public final CreatePDFInfos createPDFInfos = new CreatePDFInfos();

    public Map<String, Object> exibirTabelas(CPU cpu, List<GPU> gpus, List<HDD> hdd, SistemaOp so, MemoriaRam ram, List<APP> apps, List<ConexaoUSB> usb, List<Bateria> bateria, List<Volume> volumes) throws Exception {
        Logger.logInfo("Iniciando o monitoramento dos componentes");

        if (tablePrinter == null) {
            throw new NullPointerException("O sistema de monitoramento ou o printer de tabela falharam.");
        }

        Map<String, Object> mapaTodasTabelas = new HashMap<>();

        mapaTodasTabelas.put("SO", exibirTabelaSO(so));
        mapaTodasTabelas.put("CPU", exibirTabelaCPU(cpu));
        mapaTodasTabelas.put("HDD", exibirTabelaHDD(hdd));
        mapaTodasTabelas.put("ConexaoUSB", exibirTabelaConexaoUSB(usb));
        mapaTodasTabelas.put("Bateria", exibirTabelaBateria(bateria));
        mapaTodasTabelas.put("MemoriaRAM", exibirTabelaMemoriaRAM(ram));
        mapaTodasTabelas.put("GPU", exibirTabelaGPU(gpus));
        mapaTodasTabelas.put("Volume", exibirTabelaVolume(volumes));
        mapaTodasTabelas.put("APP", exibirTabelaAPP(apps));

        StringBuilder todasAsTabelasString = new StringBuilder();
        for (Object tabela : mapaTodasTabelas.values()) {
            if (tabela instanceof String) {
                todasAsTabelasString.append((String) tabela);
            } else if (tabela instanceof Map) {
                Map<String, Object> mapaTabela = (Map<String, Object>) tabela;
                for (Object value : mapaTabela.values()) {
                    if (value instanceof String) {
                        todasAsTabelasString.append((String) value);
                    }
                }
            }
        }
        mapaTodasTabelas.put("TODASSTRING", todasAsTabelasString.toString());

        return mapaTodasTabelas;
    }

    public Map<String, Object> exibirTabelaGPU(List<GPU> gpus) {
        if (gpus == null || gpus.isEmpty()) {
            Logger.logWarning("Nenhuma GPU encontrada durante o monitoramento.");
            return new HashMap<>();
        }
        StringBuilder table = new StringBuilder();
        StringBuilder pdf =  new StringBuilder();
        Map<String, Object> mapaGpu = new HashMap<>();
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
            mapaGpu.put("GPU " + i, gpuData);
            table.append(tablePrinter.printTable(gpuData));
            pdf.append(createPDFInfos.gerarLayoutPDF(gpuData));
        }
        mapaGpu.put("GPUSTRING", table);
        mapaGpu.put("GPUPDF", pdf);
        return mapaGpu;
    }

    public Map<String, Object> exibirTabelaCPU(CPU cpu) {
        if (cpu == null) {
            Logger.logWarning("CPU não encontrada durante o monitoramento.");
            return new HashMap<>();
        }
        Map<String, Object> mapaCpu = new HashMap<>();
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
                Arrays.asList("Temperatura", Optional.ofNullable(cpu.getTemperatura()).orElse(0.0).toString())
        );
        mapaCpu.put("CPU", cpu);
        mapaCpu.put("CPUSTRING", tablePrinter.printTable(cpuData));
        mapaCpu.put("CPUPDF", createPDFInfos.gerarLayoutPDF(cpuData));

        return mapaCpu;
    }

    public Map<String, Object> exibirTabelaSO(SistemaOp sistemaOp) {
        if (sistemaOp == null) {
            Logger.logWarning("Sistema operacional não encontrado durante o monitoramento.");
            return new HashMap<>();
        }
        Map<String, Object> mapaSO = new HashMap<>();
        List<List<String>> soData = Arrays.asList(
                Arrays.asList("", "Sistema Operacional"),
                Arrays.asList("Nome Sistema", Optional.ofNullable(sistemaOp.getSistemaOperacional()).orElse("VALOR NÃO ENCONTRADO")),
                Arrays.asList("Fabricante", Optional.ofNullable(sistemaOp.getFabricante()).orElse("VALOR NÃO ENCONTRADO")),
                Arrays.asList("Arquitetura", Optional.ofNullable(sistemaOp.getArquitetura()).orElse("VALOR NÃO ENCONTRADO")),
                Arrays.asList("Inicializado", Optional.ofNullable(sistemaOp.getInicializado()).map(Object::toString).orElse("VALOR NÃO IDENTIFICADO")),
                Arrays.asList("Permissão", Optional.ofNullable(sistemaOp.isPermissao()).map(Object::toString).orElse("VALOR NÃO ENCONTRADO")),
                Arrays.asList("Tempo de atividade", Optional.ofNullable(sistemaOp.getTempoDeAtividade()).orElse("VALOR NÃO IDENTIFICADO"))
        );
        mapaSO.put("SO", soData);
        mapaSO.put("SOSTRING", tablePrinter.printTable(soData));
        mapaSO.put("SOPDF", createPDFInfos.gerarLayoutPDF(soData));
        return mapaSO;
    }

    public Map<String, Object> exibirTabelaHDD(List<HDD> listaHDD) {
        if (listaHDD == null || listaHDD.isEmpty()) {
            Logger.logWarning("Nenhum HDD encontrado durante o monitoramento.");
            return new HashMap<>();
        }

        StringBuilder table = new StringBuilder();
        StringBuilder pdf = new StringBuilder();
        Map<String, Object> mapaHDD = new HashMap<>();
        Integer i = 0;

        for (HDD hdd : listaHDD) {
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
            mapaHDD.put("HDD " + i, hddData);
            i++;
            table.append(tablePrinter.printTable(hddData));
            pdf.append(createPDFInfos.gerarLayoutPDF(hddData));
        }
        mapaHDD.put("HDDSTRING", table.toString());
        mapaHDD.put("HDDPDF", pdf.toString());
        return mapaHDD;
    }


    public Map<String, Object> exibirTabelaMemoriaRAM(MemoriaRam memoriaRam) {
        if (memoriaRam == null) {
            Logger.logWarning("Memória RAM não encontrada durante o monitoramento.");
            return new HashMap<>();
        }

        List<List<String>> memoriaRamData = Arrays.asList(
                Arrays.asList("", "Memória RAM"),
                Arrays.asList("ID", String.valueOf(memoriaRam.getIdMemoriaRAM())),
                Arrays.asList("Memória Disponível", String.valueOf(memoriaRam.getMemoriaDisponivel())),
                Arrays.asList("Memória em Uso", String.valueOf(memoriaRam.getMemoriaEmUso())),
                Arrays.asList("Memória Total", String.valueOf(memoriaRam.getMemoriaTotal()))
        );

        Map<String, Object> mapaMemoriaRAM = new HashMap<>();
        mapaMemoriaRAM.put("MemoriaRAM", memoriaRamData);
        mapaMemoriaRAM.put("MemoriaRAMSTRING", tablePrinter.printTable(memoriaRamData));
        mapaMemoriaRAM.put("MemoriaRAMPDF", createPDFInfos.gerarLayoutPDF(memoriaRamData));

        return mapaMemoriaRAM;
    }

    public Map<String, Object> exibirTabelaBateria(List<Bateria> baterias) {
        if (baterias == null || baterias.isEmpty()) {
            Logger.logWarning("Nenhuma bateria encontrada durante o monitoramento.");
            return new HashMap<>();
        }

        StringBuilder table = new StringBuilder();
        StringBuilder pdf = new StringBuilder();
        Map<String, Object> mapaBateria = new HashMap<>();
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

            mapaBateria.put("Bateria " + i, bateriaData);
            table.append(tablePrinter.printTable(bateriaData));
            pdf.append(createPDFInfos.gerarLayoutPDF(bateriaData));
        }
        mapaBateria.put("BateriaSTRING", table.toString());
        mapaBateria.put("BateriaPDF", pdf.toString());

        return mapaBateria;
    }

    public Map<String, Object> exibirTabelaAPP(List<APP> apps) {
        if (apps == null || apps.isEmpty()) {
            Logger.logWarning("Nenhum aplicativo encontrado durante o monitoramento.");
            return new HashMap<>();
        }

        StringBuilder table = new StringBuilder();
        StringBuilder pdf = new StringBuilder();
        Map<String, Object> mapaAPP = new HashMap<>();
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
            table.append(tablePrinter.printTable(appData));
            table.append(createPDFInfos.gerarLayoutPDF(appData));
        });
        mapaAPP.put("APPSTRING", table.toString());
        mapaAPP.put("APPPDF", pdf.toString());

        return mapaAPP;
    }

    public Map<String, Object> exibirTabelaConexaoUSB(List<ConexaoUSB> conexoesUSB) {
        if (conexoesUSB == null || conexoesUSB.isEmpty()) {
            Logger.logWarning("Nenhuma conexão USB encontrada durante o monitoramento.");
            return new HashMap<>();
        }

        StringBuilder table = new StringBuilder();
        StringBuilder pdf = new StringBuilder();
        Map<String, Object> mapaConexaoUSB = new HashMap<>();
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
            table.append(tablePrinter.printTable(usbData));
            pdf.append(createPDFInfos.gerarLayoutPDF(usbData));
        });
        mapaConexaoUSB.put("ConexaoUSBSTRING", table.toString());
        mapaConexaoUSB.put("ConexaoUSBPDF", pdf.toString());

        return mapaConexaoUSB;
    }

    public Map<String, Object> exibirTabelaVolume(List<Volume> volumes) {
        if (volumes == null || volumes.isEmpty()) {
            Logger.logWarning("Nenhum volume encontrado durante o monitoramento.");
            return new HashMap<>();
        }

        StringBuilder table = new StringBuilder();
        StringBuilder pdf = new StringBuilder();
        Map<String, Object> mapaVolume = new HashMap<>();
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
            pdf.append(createPDFInfos.gerarLayoutPDF(volumeData));
            mapaVolume.put("Volume " + i, volumeData);
            table.append(tablePrinter.printTable(volumeData));
        }
        mapaVolume.put("VolumeSTRING", table.toString());
        mapaVolume.put("VolumePDF", pdf.toString());

        return mapaVolume;
    }

}
