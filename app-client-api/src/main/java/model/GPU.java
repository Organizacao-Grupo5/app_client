package model;

import core.sistema.Conversor;

public class GPU {
    private Integer idGpu;
    private String nome;
    private String fabricante;
    private String versao;
    private String idDevice;
    private Double vRam;

    public GPU() {
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
}
