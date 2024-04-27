package model;

public class Usuario {
    private int idUsuario;
    private String nome;
    private String cpfCnpj;
    private Integer fkPlano;
    private Integer fkCargo;
    private String email;
    private String senha;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nome, String cpfCnpj, Integer fkPlano, Integer fkCargo, String email, String senha) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.fkPlano = fkPlano;
        this.fkCargo = fkCargo;
        this.email = email;
        this.senha = senha;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public Integer getFkPlano() {
        return fkPlano;
    }

    public void setFkPlano(Integer fkPlano) {
        this.fkPlano = fkPlano;
    }

    public Integer getFkCargo() {
        return fkCargo;
    }

    public void setFkCargo(Integer fkCargo) {
        this.fkCargo = fkCargo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
