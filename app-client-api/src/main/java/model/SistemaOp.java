package model;

import java.time.Instant;

public class SistemaOp {
    private int idSistemaOperacional;

    private String sistemaOperacional;
    private String fabricante;
    private String arquitetura;
    private Instant inicializado;
    private boolean permissao;
    private Double tempoDeAtividade;

    public SistemaOp() {
    }

    public SistemaOp(int idSistemaOperacional, String sistemaOperacional, String fabricante, String arquitetura, Instant inicializado, boolean permissao, Double tempoDeAtividade) {
        this.idSistemaOperacional = idSistemaOperacional;
        this.sistemaOperacional = sistemaOperacional;
        this.fabricante = fabricante;
        this.arquitetura = arquitetura;
        this.inicializado = inicializado;
        this.permissao = permissao;
        this.tempoDeAtividade = tempoDeAtividade;
    }

    public int getIdSistemaOperacional() {
        return idSistemaOperacional;
    }

    public void setIdSistemaOperacional(int idSistemaOperacional) {
        this.idSistemaOperacional = idSistemaOperacional;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getArquitetura() {
        return arquitetura;
    }

    public void setArquitetura(String arquitetura) {
        this.arquitetura = arquitetura;
    }

    public Instant getInicializado() {
        return inicializado;
    }

    public void setInicializado(Instant inicializado) {
        this.inicializado = inicializado;
    }

    public boolean isPermissao() {
        return permissao;
    }

    public void setPermissao(boolean permissao) {
        this.permissao = permissao;
    }

    public Double getTempoDeAtividade() {
        return tempoDeAtividade;
    }

    public void setTempoDeAtividade(Double tempoDeAtividade) {
        this.tempoDeAtividade = tempoDeAtividade;
    }
}
