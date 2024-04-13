package model;

import java.sql.Date;
import java.time.LocalDate;

public class APP {
    private int idApp;
    private Maquina maquina;
    private String nomeApp;
    private LocalDate dtInstalacao;
    private LocalDate ultimaDtInstalacao;
    private String tamanhoAplicativo;

    public APP() {
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public APP(Maquina maquina, String nomeApp, LocalDate dtInstalacao, LocalDate ultimaDtInstalacao, String tamanhoAplicativo) {
        this.maquina = maquina;
        this.nomeApp = nomeApp;
        this.dtInstalacao = dtInstalacao;
        this.ultimaDtInstalacao = ultimaDtInstalacao;
        this.tamanhoAplicativo = tamanhoAplicativo;
    }

    public int getIdApp() {
        return idApp;
    }

    public void setIdApp(int idApp) {
        this.idApp = idApp;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public String getNomeApp() {
        return nomeApp;
    }

    public void setNomeApp(String nomeApp) {
        this.nomeApp = nomeApp;
    }

    public Date getDtInstalacao() {
        return dtInstalacao;
    }

    public void setDtInstalacao(LocalDate dtInstalacao) {
        this.dtInstalacao = dtInstalacao;
    }

    public Date getUltimaDtInstalacao() {
        return ultimaDtInstalacao;
    }

    public void setUltimaDtInstalacao(LocalDate ultimaDtInstalacao) {
        this.ultimaDtInstalacao = ultimaDtInstalacao;
    }

    public String getTamanhoAplicativo() {
        return tamanhoAplicativo;
    }

    public void setTamanhoAplicativo(String tamanhoAplicativo) {
        this.tamanhoAplicativo = tamanhoAplicativo;
    }
}
