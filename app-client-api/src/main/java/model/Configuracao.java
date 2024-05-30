package model;

import java.sql.Timestamp;

public class Configuracao {
    private Integer idConfig;
    private Float minimoParaSerMedio;
	private Float minimoParaSerRuim;
    private Timestamp dataModificacao;
    private Integer fkComponente;
    private Integer fkMaquina;
    private Integer fkUsuario;

    public Configuracao() {}

    public Configuracao(Integer idConfig, Float minimoParaSerMedio, Float minimoParaSerRuim, Timestamp dataModificacao, Integer fkComponente, Integer fkMaquina, Integer fkUsuario) {
        this.idConfig = idConfig;
        this.minimoParaSerMedio = minimoParaSerMedio;
        this.minimoParaSerRuim = minimoParaSerRuim;
        this.dataModificacao = dataModificacao;
        this.fkComponente = fkComponente;
        this.fkMaquina = fkMaquina;
        this.fkUsuario = fkUsuario;
    }

    public Integer getIdConfig() {
        return idConfig;
    }

    public void setIdConfig(Integer idConfig) {
        this.idConfig = idConfig;
    }

    public Float getMinimoParaSerMedio() {
        return minimoParaSerMedio;
    }

    public void setMinimoParaSerMedio(Float minimoParaSerMedio) {
        this.minimoParaSerMedio = minimoParaSerMedio;
    }

    public Float getMinimoParaSerRuim() {
        return minimoParaSerRuim;
    }

    public void setMinimoParaSerRuim(Float minimoParaSerRuim) {
        this.minimoParaSerRuim = minimoParaSerRuim;
    }

    public Integer getFkComponente() {
        return fkComponente;
    }

    public void setFkComponente(Integer fkComponente) {
        this.fkComponente = fkComponente;
    }

    public Timestamp getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(Timestamp dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    public Integer getFkMaquina() {
        return fkMaquina;
    }

    public void setFkMaquina(Integer fkMaquina) {
        this.fkMaquina = fkMaquina;
    }

    public Integer getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(Integer fkUsuario) {
        this.fkUsuario = fkUsuario;
    }
    
}
