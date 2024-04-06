package objects;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Cpu {
    private String modelo;
    private double frequencia;
    private int numeroNucleos;
    private double uso;

    private Map<LocalDateTime, Double> registros;

    private LocalDateTime dataHoraCaptura;

    public Cpu(String modelo, double frequencia, int numeroNucleos, double uso, LocalDateTime dataHoraCaptura) {
        this.modelo = modelo;
        this.frequencia = frequencia;
        this.numeroNucleos = numeroNucleos;
        this.uso = uso;
        this.dataHoraCaptura = dataHoraCaptura;
    }

    public Cpu() {
    }

    public Map<LocalDateTime, Double> getRegistros() {
        return registros;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(double frequencia) {
        this.frequencia = frequencia;
    }

    public int getNumeroNucleos() {
        return numeroNucleos;
    }

    public void setNumeroNucleos(int numeroNucleos) {
        this.numeroNucleos = numeroNucleos;
    }

    public double getUso() {
        return uso;
    }

    public void setUso(double uso) {
        this.uso = uso;
    }

    public LocalDateTime getDataHoraCaptura() {
        return dataHoraCaptura;
    }

    public void setDataHoraCaptura(LocalDateTime dataHoraCaptura) {
        this.dataHoraCaptura = dataHoraCaptura;
    }

    public String toString() {
        return "CPU: " +
                "\nModelo: " + modelo +
                "\nFrequência: " + frequencia + " GHz" +
                "\nNúmero de núcleos: " + numeroNucleos +
                "\nUso: " + String.format("%.2f", uso) + "%" +
                "\nData da captura: " + dataHoraCaptura;
    }
}
