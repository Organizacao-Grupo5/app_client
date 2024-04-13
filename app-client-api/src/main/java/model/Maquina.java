package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Maquina {
    private int idMaquina;
    private String ipv4;
    private Usuario usuario;
    private PlacaMae placaMae;

    public Maquina() {
    }

    public Maquina(String ipv4, Usuario usuario, PlacaMae placaMae) {
        this.ipv4 = ipv4;
        this.usuario = usuario;
        this.placaMae = placaMae;
    }

    public int getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(int idMaquina) {
        this.idMaquina = idMaquina;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public PlacaMae getPlacaMae() {
        return placaMae;
    }

    public void setPlacaMae(PlacaMae placaMae) {
        this.placaMae = placaMae;
    }
}
