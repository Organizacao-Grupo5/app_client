package model;

public class ConexaoUSB {
    private int idConexaoUSB;
    private int totalPortas;
    private String tipoConector;
    private String deteccaoDispositivo;
    private String energiaPorta;
    private String hubsConectados;
    private String dispositivoConectado;
    private Maquina maquina;

    public ConexaoUSB() {
    }

    public ConexaoUSB(int totalPortas, String tipoConector, String deteccaoDispositivo, String energiaPorta, String hubsConectados, String dispositivoConectado, Maquina maquina) {
        this.totalPortas = totalPortas;
        this.tipoConector = tipoConector;
        this.deteccaoDispositivo = deteccaoDispositivo;
        this.energiaPorta = energiaPorta;
        this.hubsConectados = hubsConectados;
        this.dispositivoConectado = dispositivoConectado;
        this.maquina = maquina;
    }

    public int getIdConexaoUSB() {
        return idConexaoUSB;
    }

    public void setIdConexaoUSB(int idConexaoUSB) {
        this.idConexaoUSB = idConexaoUSB;
    }

    public int getTotalPortas() {
        return totalPortas;
    }

    public void setTotalPortas(int totalPortas) {
        this.totalPortas = totalPortas;
    }

    public String getTipoConector() {
        return tipoConector;
    }

    public void setTipoConector(String tipoConector) {
        this.tipoConector = tipoConector;
    }

    public String getDeteccaoDispositivo() {
        return deteccaoDispositivo;
    }

    public void setDeteccaoDispositivo(String deteccaoDispositivo) {
        this.deteccaoDispositivo = deteccaoDispositivo;
    }

    public String getEnergiaPorta() {
        return energiaPorta;
    }

    public void setEnergiaPorta(String energiaPorta) {
        this.energiaPorta = energiaPorta;
    }

    public String getHubsConectados() {
        return hubsConectados;
    }

    public void setHubsConectados(String hubsConectados) {
        this.hubsConectados = hubsConectados;
    }

    public String getDispositivoConectado() {
        return dispositivoConectado;
    }

    public void setDispositivoConectado(String dispositivoConectado) {
        this.dispositivoConectado = dispositivoConectado;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }
}
