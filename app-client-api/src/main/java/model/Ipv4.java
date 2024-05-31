package model;

import app.integration.Computer;

public class Ipv4 {
    private Integer idIpv4;
    private String numeroIp;
    private String nomeLocal;
    private Integer fkMaquina;
    
    public Ipv4(Maquina maquina) {
        Computer computer = new Computer();

        this.numeroIp = computer.getIpv4();
        this.nomeLocal = "default";
        this.fkMaquina = maquina.getIdMaquina();
    }
    
    public Integer getIdIpv4() {
        return idIpv4;
    }
    public void setIdIpv4(Integer idIpv4) {
        this.idIpv4 = idIpv4;
    }
    public String getNumeroIp() {
        return numeroIp;
    }
    public void setNumeroIp(String numeroIp) {
        this.numeroIp = numeroIp;
    }
    public String getNomeLocal() {
        return nomeLocal;
    }
    public void setNomeLocal(String nomeLocal) {
        this.nomeLocal = nomeLocal;
    }
    public Integer getFkMaquina() {
        return fkMaquina;
    }
    public void setFkMaquina(Integer fkMaquina) {
        this.fkMaquina = fkMaquina;
    }
}
