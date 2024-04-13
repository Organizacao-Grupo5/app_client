package model;

import java.time.LocalDateTime;

public class HDD {
    private int idHDD;
    private double capacidadeTotal;
    private int numeroParticoes;
    private String statusSaude;
    private Maquina maquina;

    public HDD() {
    }

    public HDD(double capacidadeTotal, int numeroParticoes, String statusSaude, Maquina maquina) {
        this.capacidadeTotal = capacidadeTotal;
        this.numeroParticoes = numeroParticoes;
        this.statusSaude = statusSaude;
        this.maquina = maquina;
    }

    public int getIdHDD() {
        return idHDD;
    }

    public void setIdHDD(int idHDD) {
        this.idHDD = idHDD;
    }

    public double getCapacidadeTotal() {
        return capacidadeTotal;
    }

    public void setCapacidadeTotal(double capacidadeTotal) {
        this.capacidadeTotal = capacidadeTotal;
    }

    public int getNumeroParticoes() {
        return numeroParticoes;
    }

    public void setNumeroParticoes(int numeroParticoes) {
        this.numeroParticoes = numeroParticoes;
    }

    public String getStatusSaude() {
        return statusSaude;
    }

    public void setStatusSaude(String statusSaude) {
        this.statusSaude = statusSaude;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }
}
