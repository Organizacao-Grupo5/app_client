package model;

import core.sistema.Conversor;

import java.time.LocalDateTime;

public class GPU {
    private int idGPU;
    private String modelo;
    private double memoria;
    private double utilizacao;
    private String versaoDriver;
    private Maquina maquina;
    private Double velocidadeComponente;
    private Double temperaturaComponente;

    public GPU(String modelo, double memoria, double utilizacao, String versaoDriver, Maquina maquina) {
        this.modelo = modelo;
        this.memoria = memoria;
        this.utilizacao = utilizacao;
        this.versaoDriver = versaoDriver;
        this.maquina = maquina;
    }

    public GPU() {
    }

    public int getIdGPU() {
        return idGPU;
    }

    public void setIdGPU(int idGPU) {
        this.idGPU = idGPU;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getMemoria() {
        return memoria;
    }

    public void setMemoria(long memoria) {
        this.memoria = Conversor.converterParaKB(memoria);
    }

    public double getUtilizacao() {
        return utilizacao;
    }

    public void setUtilizacao(long utilizacao) {
        this.utilizacao = Conversor.converterParaGB(utilizacao);
    }

    public String getVersaoDriver() {
        return versaoDriver;
    }

    public void setVersaoDriver(String versaoDriver) {
        this.versaoDriver = versaoDriver;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public Double getVelocidadeComponente() {
        return velocidadeComponente;
    }

    public void setVelocidadeComponente(Double velocidadeComponente) {
        this.velocidadeComponente = velocidadeComponente;
    }

    public Double getTemperaturaComponente() {
        return temperaturaComponente;
    }

    public void setTemperaturaComponente(Double temperaturaComponente) {
        this.temperaturaComponente = temperaturaComponente;
    }
}
