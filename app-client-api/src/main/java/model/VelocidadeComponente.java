package model;

import java.time.LocalDateTime;

public class VelocidadeComponente {
    private int idVelocidadeComponentes;
    private String componente;
    private double numero;
    private String tipo;
    private LocalDateTime timestamp;

    public VelocidadeComponente() {
    }

    public VelocidadeComponente(String componente, double numero, String tipo, LocalDateTime timestamp) {
        this.componente = componente;
        this.numero = numero;
        this.tipo = tipo;
        this.timestamp = timestamp;
    }

    public int getIdVelocidadeComponentes() {
        return idVelocidadeComponentes;
    }

    public void setIdVelocidadeComponentes(int idVelocidadeComponentes) {
        this.idVelocidadeComponentes = idVelocidadeComponentes;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public double getNumero() {
        return numero;
    }

    public void setNumero(double numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
