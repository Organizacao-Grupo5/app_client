package model;

import java.time.Instant;
import java.time.LocalDateTime;

public class SistemaOp {
    private int idSistemaOperacional;

    private String sistemaOperacional;
    private String fabricante;
    private String arquitetura;
    private String inicializado;
    private String permissao;
    private String tempoDeAtividade;
    private LocalDateTime dataHoraCaptura;

    public SistemaOp() {
    }

    public SistemaOp(String sistemaOperacional, String fabricante, String arquitetura, String inicializado, String permissao, String tempoDeAtividade) {
        this.sistemaOperacional = sistemaOperacional;
        this.fabricante = fabricante;
        this.arquitetura = arquitetura;
        this.inicializado = inicializado;
        this.permissao = permissao ;
        this.tempoDeAtividade = tempoDeAtividade;
        this.dataHoraCaptura = LocalDateTime.now();
    }

    public String getPermissao() {
        return permissao;
    }

    public LocalDateTime getDataHoraCaptura() {
        return dataHoraCaptura;
    }

    public void setDataHoraCaptura(LocalDateTime dataHoraCaptura) {
        this.dataHoraCaptura = dataHoraCaptura;
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

    public String getInicializado() {
        return inicializado;
    }

    public void setInicializado(String inicializado) {
        this.inicializado = inicializado;
    }

    public String isPermissao() {
        return permissao;
    }

    public void setPermissao(String permissao) {
        this.permissao = permissao;
    }

    public String getTempoDeAtividade() {
        return tempoDeAtividade;
    }

    public void setTempoDeAtividade(String tempoDeAtividade) {
        this.tempoDeAtividade = tempoDeAtividade;
    }
}
