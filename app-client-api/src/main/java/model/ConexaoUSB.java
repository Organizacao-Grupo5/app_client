package model;

import java.time.LocalDateTime;
import java.util.List;

public class ConexaoUSB {
    private int idConexaoUSB;
    private String nomeUsb;
    private String fornecedor;
    private String idDispositivoUSBExclusivo;
    private String idFornecedor;
    private String numeroSerie;
    private String idProduto;
    private LocalDateTime dataHoraCaptura;

    public ConexaoUSB() {
        this.dataHoraCaptura = LocalDateTime.now();
    }

    public ConexaoUSB(String nomeUsb, String fornecedor, String idDispositivoUSBExclusivo, String idFornecedor, String numeroSerie, String idProduto) {
        this.nomeUsb = nomeUsb;
        this.fornecedor = fornecedor;
        this.idDispositivoUSBExclusivo = idDispositivoUSBExclusivo;
        this.idFornecedor = idFornecedor;
        this.numeroSerie = numeroSerie;
        this.idProduto = idProduto;
        this.dataHoraCaptura = LocalDateTime.now();
    }

    public LocalDateTime getDataHoraCaptura() {
        return dataHoraCaptura;
    }

    public void setDataHoraCaptura(LocalDateTime dataHoraCaptura) {
        this.dataHoraCaptura = dataHoraCaptura;
    }

    public int getIdConexaoUSB() {
        return idConexaoUSB;
    }

    public void setIdConexaoUSB(int idConexaoUSB) {
        this.idConexaoUSB = idConexaoUSB;
    }

    public String getNomeUsb() {
        return nomeUsb;
    }

    public void setNomeUsb(String nomeUsb) {
        this.nomeUsb = nomeUsb;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getIdDispositivoUSBExclusivo() {
        return idDispositivoUSBExclusivo;
    }

    public void setIdDispositivoUSBExclusivo(String idDispositivoUSBExclusivo) {
        this.idDispositivoUSBExclusivo = idDispositivoUSBExclusivo;
    }

    public String getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(String idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }
}
