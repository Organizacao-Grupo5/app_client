package model;

import app.integration.Computer;

public class Rede {
    private Integer idRede;
    private String nomeRede;
    private String interfaceRede;
    private Integer sinalRede;
    private Double transmissaoRede;
    private String bssidRede;

    public Rede() {
        Computer computer = new Computer();

        this.nomeRede = computer.getNomeRede();
        this.interfaceRede = computer.getInterfaceRede();
        this.sinalRede = computer.getSinalRede();
        this.transmissaoRede = computer.getTransmissaoRede();
        this.bssidRede = computer.getBssidRede();
    }

    public Integer getIdRede() {
        return idRede;
    }

    public void setIdRede(Integer idRede) {
        this.idRede = idRede;
    }

    public String getNomeRede() {
        return nomeRede;
    }

    public void setNomeRede(String nomeRede) {
        this.nomeRede = nomeRede;
    }

    public String getInterfaceRede() {
        return interfaceRede;
    }

    public void setInterfaceRede(String interfaceRede) {
        this.interfaceRede = interfaceRede;
    }

    public Integer getSinalRede() {
        return sinalRede;
    }

    public void setSinalRede(Integer sinalRede) {
        this.sinalRede = sinalRede;
    }

    public Double getTransmissaoRede() {
        return transmissaoRede;
    }

    public void setTransmissaoRede(Double transmissaoRede) {
        this.transmissaoRede = transmissaoRede;
    }

    public String getBssidRede() {
        return bssidRede;
    }

    public void setBssidRede(String bssidRede) {
        this.bssidRede = bssidRede;
    }

}
