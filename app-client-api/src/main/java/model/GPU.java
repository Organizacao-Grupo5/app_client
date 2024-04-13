package model;

import java.time.LocalDateTime;

public class GPU {
    private int idGPU;
    private String modelo;
    private double memoria;
    private double utilizacao;
    private String versaoDriver;
    private Maquina maquina;
    private VelocidadeComponente velocidadeComponente;
    private TemperaturaComponente temperaturaComponente;

    public GPU(String modelo, double memoria, double utilizacao, String versaoDriver, Maquina maquina, VelocidadeComponente velocidadeComponente, TemperaturaComponente temperaturaComponente) {
        this.modelo = modelo;
        this.memoria = memoria;
        this.utilizacao = utilizacao;
        this.versaoDriver = versaoDriver;
        this.maquina = maquina;
        this.velocidadeComponente = velocidadeComponente;
        this.temperaturaComponente = temperaturaComponente;
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

    public void setMemoria(double memoria) {
        this.memoria = memoria;
    }

    public double getUtilizacao() {
        return utilizacao;
    }

    public void setUtilizacao(double utilizacao) {
        this.utilizacao = utilizacao;
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

    public VelocidadeComponente getVelocidadeComponente() {
        return velocidadeComponente;
    }

    public void setVelocidadeComponente(VelocidadeComponente velocidadeComponente) {
        this.velocidadeComponente = velocidadeComponente;
    }

    public TemperaturaComponente getTemperaturaComponente() {
        return temperaturaComponente;
    }

    public void setTemperaturaComponente(TemperaturaComponente temperaturaComponente) {
        this.temperaturaComponente = temperaturaComponente;
    }
}
