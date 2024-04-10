package model;

import java.time.LocalDateTime;

public class Gpu {

    private String nome;
    private long memoria;
    private long total;
    private long livre;
    private LocalDateTime dataHoraCaptura;


    public Gpu(String nome, long memoria, long total, long livre, LocalDateTime dataHoraCaptura) {
        this.nome = nome;
        this.memoria = memoria;
        this.total = total;
        this.livre = livre;
        this.dataHoraCaptura = dataHoraCaptura;
    }

    public Gpu() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getLivre() {
        return livre;
    }

    public void setLivre(long livre) {
        this.livre = livre;
    }

    public String getModelo() {
        return nome;
    }

    public void setModelo(String modelo) {
        this.nome = modelo;
    }

    public long getMemoria() {
        return memoria;
    }

    public void setMemoria(long memoria) {
        this.memoria = memoria;
    }

    public LocalDateTime getDataHoraCaptura() {
        return dataHoraCaptura;
    }

    public void setDataHoraCaptura(LocalDateTime dataHoraCaptura) {
        this.dataHoraCaptura = dataHoraCaptura;
    }

    public String toString() {
        return "GPU: " +
                "\nNome: " + nome +
                "\nMemória: " + formatarBytes(memoria) +
                "\nData da captura: " + dataHoraCaptura;
    }

    private String formatarBytes(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1048576) {
            return String.format("%.2f", bytes / 1024.0) + " KB";
        } else if (bytes < 1073741824) {
            return String.format("%.2f", bytes / 1048576.0) + " MB";
        } else {
            return String.format("%.2f", bytes / 1073741824.0) + " GB";
        }
    }
}
