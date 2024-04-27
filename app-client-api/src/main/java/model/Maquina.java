package model;

import model.Componentes.Componente;
import model.Componentes.PlacaMae;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Maquina {
    private int idMaquina;
    private String ipv4;
    private Usuario usuario;
    private List<Componente> componentes;

    public Maquina() {
        componentes = new ArrayList<>();
    }

    public Maquina(String ipv4, Usuario usuario) {
        this.ipv4 = ipv4;
        this.usuario = usuario;
        componentes = new ArrayList<>();
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

    public List<Componente> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<Componente> componentes) {
        this.componentes = componentes;
    }

    public String exibirTabelaComponentes(){
        return new StringBuilder(componentes.stream()
                .map(Componente::tabelaConvert)
                .collect(Collectors.joining())).toString();
    }

    public String layoutPdfComponentes(){
        return new StringBuilder(componentes.stream()
                .map(Componente::pdfLayout)
                .collect(Collectors.joining())).toString();
    }
}
