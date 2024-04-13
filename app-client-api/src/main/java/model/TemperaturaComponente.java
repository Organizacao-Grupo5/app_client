package model;

import java.time.LocalDateTime;

public class TemperaturaComponente {
    private int idTemperaturaComponentes;
    private String componente;
    private int temperatura;
    private LocalDateTime timestamp;

    public TemperaturaComponente() {
    }

    public TemperaturaComponente(String componente, int temperatura, LocalDateTime timestamp) {
        this.componente = componente;
        this.temperatura = temperatura;
        this.timestamp = timestamp;
    }

    public int getIdTemperaturaComponentes() {
        return idTemperaturaComponentes;
    }

    public void setIdTemperaturaComponentes(int idTemperaturaComponentes) {
        this.idTemperaturaComponentes = idTemperaturaComponentes;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
