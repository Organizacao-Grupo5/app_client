package model.Componentes;

import java.util.List;

public class Componente  {
    protected Integer idComponente;
    protected String componente;
    protected String modelo;
    protected String fabricante;
    protected Double preferenciaAlerta;

    public Componente() {

    }

    public List<List<String>> tabela() {
        return null;
    }

    public String tabelaConvert() {
        return null;
    }

    public String pdfLayout() {
        return null;
    }

    public Componente(Integer idComponente, String componente, String modelo, String fabricante, Double preferenciaAlerta) {
        this.idComponente = idComponente;
        this.componente = componente;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.preferenciaAlerta = preferenciaAlerta;
    }

    public Componente(String fabricante, String modelo, String componente) {
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.componente = componente;
    }

    public Integer getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(Integer idComponente) {
        this.idComponente = idComponente;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public Double getPreferenciaAlerta() {
        return preferenciaAlerta;
    }

    public void setPreferenciaAlerta(Double preferenciaAlerta) {
        this.preferenciaAlerta = preferenciaAlerta;
    }
}
