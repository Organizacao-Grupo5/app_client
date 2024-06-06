package model;

import java.sql.Timestamp;

public class Captura {
    private Integer idCaptura;
    private Double dadoCaptura;
    private String unidadeMedida;
    private Timestamp dataCaptura;
    private Integer fkComponente;
    
    public Captura() {}

    public Captura(Integer idCaptura, Double dadoCaptura, String unidadeMedida, Timestamp dataCaptura, Integer fkComponente) {
        this.idCaptura = idCaptura;
        this.dadoCaptura = dadoCaptura;
        this.unidadeMedida = unidadeMedida;
        this.dataCaptura = dataCaptura;
        this.fkComponente = fkComponente;
    }

    public Integer getIdCaptura() {
        return idCaptura;
    }

    public void setIdCaptura(Integer idCaptura) {
        this.idCaptura = idCaptura;
    }

    public Double getDadoCaptura() {
        return dadoCaptura;
    }

    public void setDadoCaptura(Double dadoCaptura) {
        this.dadoCaptura = dadoCaptura;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Timestamp getDataCaptura() {
        return dataCaptura;
    }

    public void setDataCaptura(Timestamp dataCaptura) {
        this.dataCaptura = dataCaptura;
    }

    public Integer getFkComponente() {
        return fkComponente;
    }

    public void setFkComponente(Integer fkComponente) {
        this.fkComponente = fkComponente;
    }

    
    
}
