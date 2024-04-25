package model;

import java.time.LocalDateTime;

public class PlacaMae {
    private int idPlacaMae;
    private String fabricante;
    private String modelo;
    private LocalDateTime dataHoraCaptura;

    public PlacaMae() {
        this.dataHoraCaptura = LocalDateTime.now();
    }

    public PlacaMae(String fabricante, String modelo) {
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.dataHoraCaptura = LocalDateTime.now();
    }

    public LocalDateTime getDataHoraCaptura() {
        return dataHoraCaptura;
    }

    public void setDataHoraCaptura(LocalDateTime dataHoraCaptura) {
        this.dataHoraCaptura = dataHoraCaptura;
    }

    public int getIdPlacaMae() {
        return idPlacaMae;
    }

    public void setIdPlacaMae(int idPlacaMae) {
        this.idPlacaMae = idPlacaMae;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
