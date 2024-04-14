package model;

import core.sistema.Conversor;

import java.util.Set;

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

    public void setIdHDD(Integer idHDD) {
        this.idHDD = idHDD;
    }

    public double getCapacidadeTotal() {
        return capacidadeTotal;
    }

    public void setCapacidadeTotal(long capacidadeTotal) {
        this.capacidadeTotal = Conversor.converterCasasDecimais(formatarBytes(capacidadeTotal), 2);
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

    private Double formatarBytes(long bytes) {
        if (bytes < 1024) {
            return Double.parseDouble(String.valueOf(bytes));
        } else if (bytes < 1048576) {
            return bytes / 1024.0;
        } else if (bytes < 1073741824) {
            return bytes / 1048576.0;
        } else {
            return bytes / 1073741824.0;
        }
    }
}
