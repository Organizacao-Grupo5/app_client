package model;

public class PlacaMae {
    private int idPlacaMae;
    private String fabricante;
    private String modelo;

    public PlacaMae() {
    }

    public PlacaMae(String fabricante, String modelo) {
        this.fabricante = fabricante;
        this.modelo = modelo;
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
