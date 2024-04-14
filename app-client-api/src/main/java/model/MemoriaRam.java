package model;

import core.sistema.Conversor;

public class MemoriaRam {
    private int idMemoriaRAM;
    private double capacidadeTotal;
    private int numeroModulo;
    private double porcentagemUtilizada;
    private Maquina maquina;
    private Double velocidadeComponente;
    private Double temperaturaComponente;

    public MemoriaRam() {
    }

    public MemoriaRam(double capacidadeTotal, int numeroModulo, double porcentagemUtilizada, Maquina maquina) {
        this.capacidadeTotal = capacidadeTotal;
        this.numeroModulo = numeroModulo;
        this.porcentagemUtilizada = porcentagemUtilizada;
        this.maquina = maquina;
    }

    public int getIdMemoriaRAM() {
        return idMemoriaRAM;
    }

    public void setIdMemoriaRAM(int idMemoriaRAM) {
        this.idMemoriaRAM = idMemoriaRAM;
    }

    public double getCapacidadeTotal() {
        return capacidadeTotal;
    }

    public void setCapacidadeTotal(long capacidadeTotal) {
        this.capacidadeTotal = Conversor.converterCasasDecimais(Conversor.converterParaGB(capacidadeTotal), 2);
    }

    public int getNumeroModulo() {
        return numeroModulo;
    }

    public void setNumeroModulo(int numeroModulo) {
        this.numeroModulo = numeroModulo;
    }

    public double getPorcentagemUtilizada() {
        return porcentagemUtilizada;
    }

    public void setPorcentagemUtilizada(double porcentagemUtilizada) {
        this.porcentagemUtilizada = Conversor.converterCasasDecimais(porcentagemUtilizada, 2);
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
