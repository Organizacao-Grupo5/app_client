package model;

import java.time.LocalDateTime;

public class MemoriaRam {
    private int idMemoriaRAM;
    private double capacidadeTotal;
    private int numeroModulo;
    private double porcentagemUtilizada;
    private Maquina maquina;
    private VelocidadeComponente velocidadeComponente;
    private TemperaturaComponente temperaturaComponente;

    public MemoriaRam() {
    }

    public MemoriaRam(double capacidadeTotal, int numeroModulo, double porcentagemUtilizada, Maquina maquina, VelocidadeComponente velocidadeComponente, TemperaturaComponente temperaturaComponente) {
        this.capacidadeTotal = capacidadeTotal;
        this.numeroModulo = numeroModulo;
        this.porcentagemUtilizada = porcentagemUtilizada;
        this.maquina = maquina;
        this.velocidadeComponente = velocidadeComponente;
        this.temperaturaComponente = temperaturaComponente;
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

    public void setCapacidadeTotal(double capacidadeTotal) {
        this.capacidadeTotal = capacidadeTotal;
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
        this.porcentagemUtilizada = porcentagemUtilizada;
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
