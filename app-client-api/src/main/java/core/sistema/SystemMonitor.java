package core.sistema;

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
        CPU cpu = new CPU();

        processador.getNumeroCpusFisicas();
        processador.getNumeroCpusLogicas();
        processador.getMicroarquitetura();
        processador.getIdentificador();
        processador.getId();
        processador.getFabricante();
        processador.getFrequencia();
        processador.getNumeroPacotesFisicos();
        processador.getUso();
        processador.getNome();
        temperatura.getTemperatura();

        return cpu;
    }

    public List<GPU> monitorarGPU() {
        List<GPU> listaGpu = new ArrayList<>();
        List<GraphicsCard> graphicsCards = hardware.getGraphicsCards();
        for (int i = 0; i < graphicsCards.size(); i++) {

            GraphicsCard graphicCard = graphicsCards.get(i);
            GPU gpu = new GPU();

            gpu.setNome(graphicCard.getName());
            gpu.setvRam(conversor.formatarBytes(graphicCard.getVRam()));
            gpu.setIdDevice(graphicCard.getDeviceId());
            gpu.setVersao(graphicCard.getVersionInfo());
            gpu.setFabricante(graphicCard.getVendor());
            listaGpu.add(gpu);
        }
        return listaGpu;
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
        List<Disco> diskStores = looca.getGrupoDeDiscos().getDiscos();
        List<HDD> listaHDD = new ArrayList<>();

        for (Disco disco : diskStores) {
            HDD hdd = new HDD();

            hdd.setNome(disco.getNome());
            hdd.setSerial(disco.getSerial());
            hdd.setModelo(disco.getModelo());
            hdd.setEscritas(disco.getEscritas());
            hdd.setLeituras(disco.getLeituras());
            hdd.setBytesDeEscrita(conversor.formatarBytes(disco.getBytesDeEscritas()));
            hdd.setBytesDeLeitura(conversor.formatarBytes(disco.getBytesDeLeitura()));
            hdd.setTamanho(conversor.converterCasasDecimais(conversor.formatarBytes(disco.getTamanho()), 2));
            hdd.setTamanhoAtualDaFita(conversor.converterCasasDecimais(conversor.formatarBytes(disco.getTamanhoAtualDaFila()), 2));
            hdd.setTempoDeTransferencia(conversor.converterSegundosParaHoras(disco.getTempoDeTransferencia()));

            listaHDD.add(hdd);
        }
        return listaHDD;
    }

    public List<ConexaoUSB> monitorarUSB() {
        List<DispositivoUsb> listaUSB = looca.getDispositivosUsbGrupo().getDispositivosUsbConectados();
        List<ConexaoUSB> listaUSBs = new ArrayList<>();

        for (DispositivoUsb dispositivoUsb : listaUSB){
            ConexaoUSB usb = new ConexaoUSB();

            usb.setNomeUsb(dispositivoUsb.getNome());
            usb.setFornecedor(dispositivoUsb.getForncecedor());
            usb.setIdDispositivoUSBExclusivo(dispositivoUsb.getIdDispositivoUsbExclusivo());
            usb.setIdFornecedor(dispositivoUsb.getIdFornecedor());
            usb.setNumeroSerie(dispositivoUsb.getNumeroDeSerie());
            usb.setIdProduto(dispositivoUsb.getIdProduto());

            listaUSBs.add(usb);
        }
        return listaUSBs;
    }

    public void monitorarBateria() {

    }

    public void monitorarDisplay() {

    }

    public void monitorarVolumeLogico() {

    }

    public SistemaOp monitorarSistemaOperacional(){
        Sistema sistema = looca.getSistema();
        SistemaOp sistemaOp = new SistemaOp();

        sistema.getSistemaOperacional();
        sistema.getFabricante();
        sistema.getArquitetura();
        sistema.getInicializado();
        sistema.getPermissao();
        conversor.converterSegundosParaHoras(sistema.getTempoDeAtividade());

        return sistemaOp;
    }
}