package model;

import java.time.LocalDateTime;

public class CPU {
    private int idCPU;
    private String modelo;
    private String numeroSerie;
    private String fabricante;
    private String arquitetura;
    private String cache;
    private Maquina maquina;
    private Double velocidadeComponente;
    private Double temperaturaComponente;

    public CPU() {
    }

    public CPU(String modelo, String numeroSerie, String fabricante, String arquitetura, String cache, Maquina maquina) {
        this.modelo = modelo;
        this.numeroSerie = numeroSerie;
        this.fabricante = fabricante;
        this.arquitetura = arquitetura;
        this.cache = cache;
        this.maquina = maquina;
    }

    public int getIdCPU() {
        return idCPU;
    }

    public void setIdCPU(int idCPU) {
        this.idCPU = idCPU;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getArquitetura() {
        return arquitetura;
    }

    public void setArquitetura(String arquitetura) {
        this.arquitetura = arquitetura;
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
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
