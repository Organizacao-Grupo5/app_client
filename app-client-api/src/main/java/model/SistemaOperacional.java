package model;

public class SistemaOperacional {
    private int idSistemaOperacional;
    private String nome;
    private String versao;
    private double usoCPU;
    private double usoRAM;
    private String usuario;

    public SistemaOperacional(String nome, String versao, double usoCPU, double usoRAM, String usuario) {
        this.nome = nome;
        this.versao = versao;
        this.usoCPU = usoCPU;
        this.usoRAM = usoRAM;
        this.usuario = usuario;
    }

    public SistemaOperacional() {
    }

    public int getIdSistemaOperacional() {
        return idSistemaOperacional;
    }

    public void setIdSistemaOperacional(int idSistemaOperacional) {
        this.idSistemaOperacional = idSistemaOperacional;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public double getUsoCPU() {
        return usoCPU;
    }

    public void setUsoCPU(double usoCPU) {
        this.usoCPU = usoCPU;
    }

    public double getUsoRAM() {
        return usoRAM;
    }

    public void setUsoRAM(double usoRAM) {
        this.usoRAM = usoRAM;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
