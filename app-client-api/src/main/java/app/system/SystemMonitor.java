package app.system;

import app.integration.HardwareIntegration;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.dispositivos.DispositivoUsb;
import com.github.britooo.looca.api.group.janelas.Janela;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.sistema.Sistema;
import exception.InvalidDataException;
import model.*;
import util.logs.Logger;
import oshi.SystemInfo;
import oshi.hardware.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SystemMonitor {
    private final Looca looca = new Looca();
    private final Conversor conversor = new Conversor();
    private boolean dadosJanelaValidados = false;
    private boolean dadosHDDValidados = false;
    private boolean dadosBateriaValidados = false;
    private boolean dadosGPUValidados = false;
    private boolean dadosUSBValidados = false;

    private final HardwareAbstractionLayer hardware = new SystemInfo().getHardware();

    HardwareIntegration hardwareIntegration = new HardwareIntegration();

    public CPU monitorarCPU() {
        Logger.logInfo("Capturando dados da sua CPU.");
        CPU cpu = new CPU();

        try {
            Processador processor = looca.getProcessador();

            cpu.setNumeroDeCpusLogicas(processor.getNumeroCpusLogicas());
            cpu.setNumeroDeCpusFisicas(processor.getNumeroCpusFisicas());
            cpu.setMicroarquitetura(processor.getMicroarquitetura());
            cpu.setIdentificador(processor.getIdentificador());
            cpu.setIdCpuLooca(processor.getId());
            cpu.setFabricante(processor.getFabricante());
            cpu.setFrequencia(Double.valueOf(processor.getFrequencia()));
            cpu.setNumeroPacotesFisicos(processor.getNumeroPacotesFisicos());
            cpu.setUso(processor.getUso());
            cpu.setNome(processor.getNome());

            cpu.setTemperatura(Optional.ofNullable(hardwareIntegration.monitorarTemperatura()).orElse(0.00));

            Logger.logInfo("Dados da CPU capturados com sucesso.");
        } catch (Exception e) {
            Logger.logError("Erro ao capturar dados da CPU: ", e.getMessage(), e);
        }

        return cpu;
    }

    public List<APP> monitorarDisplay() {
        if (!dadosJanelaValidados) {
            Logger.logInfo("Capturando dados dos seus Apps.");
            dadosJanelaValidados = true;
        }

        List<APP> apps = new ArrayList<>();

        try {
            looca.getGrupoDeJanelas().getJanelasVisiveis().forEach(janela -> {
                try {
                    validarDados(janela);
                    APP app = new APP(janela.getTitulo(), janela.getComando(),
                            conversor.formatarBytes(janela.getJanelaId()), conversor.formatarBytes(janela.getPid()),
                            janela.getLocalizacaoETamanho());
                    apps.add(app);
                    Logger.logInfo("Dados da janela gravados.");
                } catch (Exception e) {
                    Logger.logError("Erro ao processar janela: ", e.getMessage(), e);
                }
            });
            Logger.logInfo("Todas as janelas verificadas.");
        } catch (Exception e) {
            Logger.logError("Erro ao acessar informações do sistema: ", e.getMessage(), e);
        }

        return apps;
    }

    private void validarDados(Janela janela) {
        if (!dadosJanelaValidados && (janela.getTitulo() == null || janela.getTitulo().isEmpty()
                || janela.getComando() == null || janela.getComando().isEmpty() || janela.getJanelaId() <= 0
                || janela.getPid() <= 0 || janela.getLocalizacaoETamanho() == null)) {
            Logger.logWarning("Dados incompletos da janela.");
        }
    }

    public List<HDD> monitorarHDD() {
        if (!dadosHDDValidados) {
            Logger.logInfo("Capturando dados do seu HDD");
            dadosHDDValidados = true;
        }

        List<HDD> hdds = new ArrayList<>();

        try {
            looca.getGrupoDeDiscos().getDiscos().forEach(disco -> {
                try {
                    validarDadosDisco(disco);
                    hdds.add(mapearDiscoParaHDD(disco));
                } catch (InvalidDataException e) {
                    Logger.logWarning("Erro ao processar dados do HDD: " + e.getMessage());
                }
            });
            Logger.logInfo("Dados do disco gravados.");
        } catch (Exception e) {
            Logger.logError("Erro ao acessar informações do sistema: ", e.getMessage(), e);
        }
        return hdds;
    }

    private void validarDadosDisco(Disco disco) {
        if (!dadosHDDValidados && (disco.getNome() == null || disco.getNome().isEmpty() || disco.getSerial() == null
                || disco.getSerial().isEmpty() || disco.getModelo() == null || disco.getModelo().isEmpty()
                || disco.getTamanho() <= 0)) {
            Logger.logWarning("Dados incompletos do disco.");
        }
    }

    private HDD mapearDiscoParaHDD(Disco disco) throws InvalidDataException {
        return new HDD(disco.getNome(), disco.getSerial(), disco.getModelo(),
                conversor.formatarBytes(disco.getEscritas()), conversor.formatarBytes(disco.getLeituras()),
                conversor.formatarBytes(disco.getBytesDeEscritas()), conversor.formatarBytes(disco.getBytesDeLeitura()),
                conversor.converterCasasDecimais(conversor.formatarBytes(disco.getTamanho()), 2),
                conversor.converterCasasDecimais(conversor.formatarBytes(looca.getGrupoDeDiscos().getVolumes().getFirst().getTotal()) - conversor.formatarBytes(looca.getGrupoDeDiscos().getVolumes().getFirst().getDisponivel()), 2),
                conversor.converterSegundosParaHoras(disco.getTempoDeTransferencia()));
    }

    public List<Bateria> monitorarBateria() {
        if (!dadosBateriaValidados) {
            Logger.logInfo("Capturando dados da sua bateria.");
            dadosBateriaValidados = true;
        }

        List<Bateria> baterias = new ArrayList<>();

        try {
            hardware.getPowerSources().forEach(power -> {
                try {
                    validarDadosBateria(power);
                    baterias.add(mapearPowerParaBateria(power));
                } catch (InvalidDataException e) {
                    Logger.logWarning("Erro ao processar dados da bateria: " + e.getMessage());
                }
            });
            Logger.logInfo("Dados da bateria gravados.");
        } catch (Exception e) {
            Logger.logError("Erro ao acessar informações do sistema: ", e.getMessage(), e);
        }
        return baterias;
    }

    private void validarDadosBateria(PowerSource power) {
        if (!dadosBateriaValidados && (power.getDeviceName() == null || power.getDeviceName().isEmpty()
                || power.getSerialNumber() == null || power.getSerialNumber().isEmpty() || power.getChemistry() == null
                || power.getChemistry().isEmpty() || power.getName() == null || power.getName().isEmpty())) {
            Logger.logWarning("Dados incompletos da bateria.");
        }
    }

    private Bateria mapearPowerParaBateria(PowerSource power) throws InvalidDataException {
        String manufactureDateStr = power.getManufactureDate() != null ? power.getManufactureDate().toString() : "N/A";
        return new Bateria(power.getAmperage(), power.getDeviceName(), power.getSerialNumber(), power.getChemistry(),
                power.getName(), power.getVoltage(), "mAh", conversor.formatarBytes(power.getCurrentCapacity()),
                power.getCycleCount(), conversor.formatarBytes(power.getDesignCapacity()),
                power.getTimeRemainingInstant(), power.getTimeRemainingEstimated(), power.getPowerUsageRate(),
                power.getTemperature(), conversor.formatarBytes(power.getMaxCapacity()),
                conversor.convertePorcentagem(power.getMaxCapacity(),
                        power.getRemainingCapacityPercent() * power.getMaxCapacity() / 100),
                manufactureDateStr, power.getManufacturer());
    }


    public List<GPU> monitorarGPU() {
        if (!dadosGPUValidados) {
            Logger.logInfo("Capturando dados da sua GPU");
            dadosGPUValidados = true;
        }

        List<GPU> gpus = new ArrayList<>();
        List<GraphicsCard> graphicsCards = hardware.getGraphicsCards();

        try {
            if (graphicsCards == null || graphicsCards.isEmpty()) {
                Logger.logWarning("Dados da GPU não puderam ser capturados: nenhuma placa gráfica encontrada.");
            } else {
                graphicsCards.forEach(gpu -> {
                    try {
                        validarDadosGPU(gpu);
                        gpus.add(mapearGPU(gpu));
                    } catch (InvalidDataException e) {
                        Logger.logWarning("Erro ao processar dados da GPU: " + e.getMessage());
                    }
                });
                Logger.logInfo("Dados da GPU gravados.");
            }
        } catch (Exception e) {
            Logger.logError("Erro ao acessar informações do sistema: ", e.getMessage(), e);
        }
        return gpus;
    }

    private void validarDadosGPU(GraphicsCard gpu) {
        if (!dadosGPUValidados && (gpu.getName() == null || gpu.getName().isEmpty() || gpu.getVendor() == null
                || gpu.getVendor().isEmpty() || gpu.getVersionInfo() == null || gpu.getVersionInfo().isEmpty())) {
            Logger.logWarning("Dados incompletos da GPU.");
        }
    }

    private GPU mapearGPU(GraphicsCard gpu) throws InvalidDataException {
        return new GPU(gpu.getName(), gpu.getVendor(), gpu.getVersionInfo(), gpu.getDeviceId(),
                conversor.formatarBytes(gpu.getVRam()));
    }

    public MemoriaRam monitorarRAM() {
        Logger.logInfo("Capturando dados da sua memória Ram.");
        Memoria memoria = looca.getMemoria();

        if (memoria == null) {
            Logger.logWarning("Dados da memória RAM não puderam ser capturados: memória é nula.");
        }

        MemoriaRam memoriaRam = new MemoriaRam();

        memoriaRam.setMemoriaDisponivel(conversor.formatarBytes(memoria.getDisponivel()));
        memoriaRam.setMemoriaTotal(conversor.formatarBytes(memoria.getTotal()));
        memoriaRam.setMemoriaEmUso(conversor.formatarBytes(memoria.getEmUso()));

        return memoriaRam;
    }

    public SistemaOp monitorarSistemaOperacional() {
        Logger.logInfo("Capturando dados do sistema operacional.");
        Sistema sistema = looca.getSistema();

        if (sistema == null) {
            Logger.logWarning("Dados do sistema operacional não puderam ser capturados: sistema é nulo.");
        }

        return new SistemaOp(Optional.ofNullable(sistema.getSistemaOperacional()).orElse("VALOR NÃO ENCONTRADO"),
                Optional.ofNullable(sistema.getFabricante()).orElse("VALOR NÃO ENCONTRADO"),
                Optional.ofNullable(sistema.getArquitetura()).map(arquitetura -> arquitetura.toString())
                        .orElse("VALOR NÃO ENCONTRADO"),
                Optional.ofNullable(sistema.getInicializado()).map(Object::toString).orElse("VALOR NÃO ENCONTRADO"),
                Optional.ofNullable(sistema.getPermissao()).map(p -> p ? "SIM" : "NÃO").orElse("VALOR NÃO ENCONTRADO"),
                Optional.ofNullable(sistema.getTempoDeAtividade()).map(conversor::converterSegundosParaHoras)
                        .map(Object::toString).orElse("VALOR NÃO ENCONTRADO"));
    }

    public List<ConexaoUSB> monitorarUSB() {
        if (!dadosUSBValidados) {
            Logger.logInfo("Capturando dados das suas conexões USB");
            dadosUSBValidados = true;
        }

        List<ConexaoUSB> conexoesUSB = looca.getDispositivosUsbGrupo().getDispositivosUsbConectados().stream()
                .map(usb -> mapearConexaoUSB(usb))
                .collect(Collectors.toList());

        validarDadosUSB(conexoesUSB);

        return conexoesUSB;
    }

    private ConexaoUSB mapearConexaoUSB(DispositivoUsb usb) {
        return new ConexaoUSB(usb.getNome(), usb.getForncecedor(), usb.getIdDispositivoUsbExclusivo(),
                usb.getIdFornecedor(), usb.getNumeroDeSerie(), usb.getIdProduto());
    }

    private void validarDadosUSB(List<ConexaoUSB> conexoesUSB) {
        if (!dadosUSBValidados) {
            if (conexoesUSB == null || conexoesUSB.isEmpty()) {
                Logger.logWarning("Nenhuma conexão USB detectada.");
            }
        }
    }

    public List<Volume> monitorarVolumeLogico() {
        return looca.getGrupoDeDiscos().getVolumes().stream()
                .map(volume -> new Volume(volume.getNome(), volume.getVolume(),
                        conversor.formatarBytes(volume.getDisponivel()), conversor.formatarBytes(volume.getTotal()),
                        volume.getTipo(), volume.getUUID(), volume.getPontoDeMontagem()))
                .collect(Collectors.toList());
    }

}
