package model;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.Date;

public class APP {
    private int idApp;
    private String nome;

    private String comando;
    private Double janelaID;
    private Double pid;
    private Rectangle localizacaoEtamanho;
    private LocalDateTime dataHoraCaptura;

    public APP() {
        this.dataHoraCaptura = LocalDateTime.now();
    }

    public APP(String nome, String comando, Double janelaID, Double pid, Rectangle localizacaoEtamanho) {
        this.nome = nome;
        this.comando = comando;
        this.janelaID = janelaID;
        this.pid = pid;
        this.localizacaoEtamanho = localizacaoEtamanho;
        this.dataHoraCaptura = LocalDateTime.now();
    }

    public LocalDateTime getDataHoraCaptura() {
        return dataHoraCaptura;
    }

    public void setDataHoraCaptura(LocalDateTime dataHoraCaptura) {
        this.dataHoraCaptura = dataHoraCaptura;
    }

    public int getIdApp() {
        return idApp;
    }

    public void setIdApp(int idApp) {
        this.idApp = idApp;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public Double getJanelaID() {
        return janelaID;
    }

    public void setJanelaID(Double janelaID) {
        this.janelaID = janelaID;
    }

    public Double getPid() {
        return pid;
    }

    public void setPid(Double pid) {
        this.pid = pid;
    }

    public Rectangle getLocalizacaoEtamanho() {
        return localizacaoEtamanho;
    }

    public void setLocalizacaoEtamanho(Rectangle localizacaoEtamanho) {
        this.localizacaoEtamanho = localizacaoEtamanho;
    }
}