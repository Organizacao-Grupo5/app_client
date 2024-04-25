package model;

import java.time.LocalDateTime;

public class MemoriaRam {
    private int idMemoriaRAM;
    private Double memoriaDisponivel;
    private Double memoriaEmUso;
    private Double memoriaTotal;
    private LocalDateTime dataHoraCaptura;

    public MemoriaRam() {
        this.dataHoraCaptura = LocalDateTime.now();
    }

    public MemoriaRam(int idMemoriaRAM, Double memoriaDisponivel, Double memoriaEmUso, Double memoriaTotal) {
        this.idMemoriaRAM = idMemoriaRAM;
        this.memoriaDisponivel = memoriaDisponivel;
        this.memoriaEmUso = memoriaEmUso;
        this.memoriaTotal = memoriaTotal;
        this.dataHoraCaptura = LocalDateTime.now();
    }

    public int getIdMemoriaRAM() {
        return idMemoriaRAM;
    }

    public LocalDateTime getDataHoraCaptura() {
        return dataHoraCaptura;
    }

    public void setDataHoraCaptura(LocalDateTime dataHoraCaptura) {
        this.dataHoraCaptura = dataHoraCaptura;
    }

    public void setIdMemoriaRAM(int idMemoriaRAM) {
        this.idMemoriaRAM = idMemoriaRAM;
    }

    public Double getMemoriaDisponivel() {
        return memoriaDisponivel;
    }

    public void setMemoriaDisponivel(Double memoriaDisponivel) {
        this.memoriaDisponivel = memoriaDisponivel;
    }

    public Double getMemoriaEmUso() {
        return memoriaEmUso;
    }

    public void setMemoriaEmUso(Double memoriaEmUso) {
        this.memoriaEmUso = memoriaEmUso;
    }

    public Double getMemoriaTotal() {
        return memoriaTotal;
    }

    public void setMemoriaTotal(Double memoriaTotal) {
        this.memoriaTotal = memoriaTotal;
    }
}
