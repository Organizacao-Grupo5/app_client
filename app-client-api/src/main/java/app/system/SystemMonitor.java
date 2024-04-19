package app.system;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.dispositivos.DispositivoUsb;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.sistema.Sistema;
import com.github.britooo.looca.api.group.temperatura.Temperatura;
import model.*;
import oshi.SystemInfo;
import oshi.hardware.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class SystemMonitor {

    private Looca looca;
    private Conversor conversor;
    private SystemInfo systemInfo;
    private HardwareAbstractionLayer hardware;

    public SystemMonitor() {
        looca = new Looca();
        systemInfo = new SystemInfo();
        hardware = systemInfo.getHardware();
    }

    public CPU monitorarCPU() {
        Processador processador = looca.getProcessador();
        Temperatura temperatura = looca.getTemperatura();

        return new CPU(
                processador.getNumeroCpusFisicas(),
                processador.getNumeroCpusLogicas(),
                processador.getMicroarquitetura(),
                processador.getIdentificador(),
                processador.getId(),
                processador.getFabricante(),
                conversor.formatarBytes(processador.getFrequencia()),
                processador.getNumeroPacotesFisicos(),
                processador.getUso(),
                processador.getNome(),
                temperatura.getTemperatura()
        );
    }

    public List<GPU> monitorarGPU() {
        return hardware.getGraphicsCards().stream().map(gpu -> new GPU(
                gpu.getName(),
                gpu.getVendor(),
                gpu.getVersionInfo(),
                gpu.getDeviceId(),
                conversor.formatarBytes(gpu.getVRam())
        )).collect(Collectors.toList());
    }

    public MemoriaRam monitorarRAM() {
        Memoria memoria = looca.getMemoria();
        MemoriaRam memoriaRam = new MemoriaRam();

        memoriaRam.setMemoriaDisponivel(conversor.formatarBytes(memoria.getDisponivel()));
        memoriaRam.setMemoriaTotal(conversor.formatarBytes(memoria.getTotal()));
        memoriaRam.setMemoriaEmUso(conversor.formatarBytes(memoria.getEmUso()));

        return memoriaRam;
    }

    public List<HDD> monitorarHDD() {
        return looca.getGrupoDeDiscos().getDiscos().stream().map(disco -> new HDD(
                disco.getNome(),
                disco.getSerial(),
                disco.getModelo(),
                disco.getEscritas(),
                disco.getLeituras(),
                conversor.formatarBytes(disco.getBytesDeEscritas()),
                conversor.formatarBytes(disco.getBytesDeLeitura()),
                conversor.converterCasasDecimais(conversor.formatarBytes(disco.getTamanho()), 2),
                conversor.converterCasasDecimais(conversor.formatarBytes(disco.getTamanhoAtualDaFila()), 2),
                conversor.converterSegundosParaHoras(disco.getTempoDeTransferencia())
        )).collect(Collectors.toList());


    }

    public List<ConexaoUSB> monitorarUSB() {
        return looca.getDispositivosUsbGrupo().getDispositivosUsbConectados().stream().map(usb -> new ConexaoUSB(
                usb.getNome(),
                usb.getForncecedor(),
                usb.getIdDispositivoUsbExclusivo(),
                usb.getIdFornecedor(),
                usb.getNumeroDeSerie(),
                usb.getIdProduto())).collect(Collectors.toList());
    }

    public List<Bateria> monitorarBateria() {
        return hardware.getPowerSources().stream()
                .map(power -> new Bateria(
                        power.getAmperage(),
                        power.getDeviceName(),
                        power.getSerialNumber(),
                        power.getChemistry(),
                        power.getName(),
                        power.getVoltage(),
                        "mAh",
                        conversor.formatarBytes(power.getCurrentCapacity()),
                        power.getCycleCount(),
                        conversor.formatarBytes(power.getDesignCapacity()),
                        power.getTimeRemainingInstant(),
                        power.getTimeRemainingEstimated(),
                        power.getPowerUsageRate(),
                        power.getTemperature(),
                        conversor.formatarBytes(power.getMaxCapacity()),
                        conversor.convertePorcentagem(power.getMaxCapacity(), power.getRemainingCapacityPercent() * power.getMaxCapacity() / 100),
                        power.getManufactureDate().toString(),
                        power.getManufacturer()
                ))
                .collect(Collectors.toList());
    }



    public List<APP> monitorarDisplay() {
        return looca.getGrupoDeJanelas().getJanelasVisiveis().stream().map(janela -> new APP(
                janela.getTitulo(),
                janela.getComando(),
                conversor.formatarBytes(janela.getJanelaId()),
                conversor.formatarBytes(janela.getPid()),
                janela.getLocalizacaoETamanho()
        )).collect(Collectors.toList());
    }

    public List<Volume> monitorarVolumeLogico() {
        return looca.getGrupoDeDiscos().getVolumes().stream().map(volume -> new Volume(
                volume.getNome(),
                volume.getVolume(),
                conversor.formatarBytes(volume.getDisponivel()),
                conversor.formatarBytes(volume.getTotal()),
                volume.getTipo(),
                volume.getUUID(),
                volume.getPontoDeMontagem()
        )).collect(Collectors.toList());

    }

    public SistemaOp monitorarSistemaOperacional() {
        Sistema sistema = looca.getSistema();

        return new SistemaOp(
                sistema.getSistemaOperacional(),
                sistema.getFabricante(),
                sistema.getArquitetura().toString(),
                sistema.getInicializado(),
                sistema.getPermissao(),
                conversor.converterSegundosParaHoras(sistema.getTempoDeAtividade())
        );
    }
}