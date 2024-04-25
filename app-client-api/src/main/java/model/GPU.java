package model;

import util.TablePrinter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GPU {
    private Integer idGpu;
    private String nome;
    private String fabricante;
    private String versao;
    private String idDevice;
    private Double vRam;
    private LocalDateTime dataHoraCaptura;
    private Double temperatura;

    public GPU() {
        dataHoraCaptura = LocalDateTime.now();
    }

    public GPU(String nome, String fabricante, String versao, String idDevice, Double vRam) {
        this.nome = nome;
        this.fabricante = fabricante;
        this.versao = versao;
        this.idDevice = idDevice;
        this.vRam = vRam;
        this.dataHoraCaptura = LocalDateTime.now();
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public LocalDateTime getDataHoraCaptura() {
        return dataHoraCaptura;
    }

    public void setDataHoraCaptura(LocalDateTime dataHoraCaptura) {
        this.dataHoraCaptura = dataHoraCaptura;
    }

    public Integer getIdGpu() {
        return idGpu;
    }

    public void setIdGpu(Integer idGpu) {
        this.idGpu = idGpu;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(String idDevice) {
        this.idDevice = idDevice;
    }

    public Double getvRam() {
        return vRam;
    }

    public void setvRam(Double vRam) {
        this.vRam = vRam;
    }
    
    public String tabela(){
        List<List<String>> gpuData = Arrays.asList(
                Arrays.asList("", "GPU "),
                Arrays.asList("Data Hora captura", String.valueOf(dataHoraCaptura)),
                Arrays.asList("ID", String.valueOf(idGpu)),
                Arrays.asList("Nome", nome),
                Arrays.asList("Fabricante", fabricante),
                Arrays.asList("Versão", versao),
                Arrays.asList("ID Device", idDevice),
                Arrays.asList("VRAM", String.valueOf(vRam)),
                Arrays.asList("Temperatura", Optional.ofNullable(temperatura).orElse(0.0).toString() + "°C")
        );
        return TablePrinter.printTable(gpuData);
    }
}
