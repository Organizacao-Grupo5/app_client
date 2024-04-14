package model;

public class Usuario {
    private int idUsuario;
    private String nome;
    private String cpfCnpj;
    private String status;
    private String email;
    private String senha;

    private int idPlano;

    public Usuario() {
    }

    public Usuario(String nome, String cpfCnpj, String status, String email, String senha, int idPlano) {
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.status = status;
        this.email = email;
        this.senha = senha;
        this.idPlano = idPlano;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public int getIdPlano() {
        return idPlano;
    }

    public void setIdPlano(int idPlano) {
        this.idPlano = idPlano;
    }
}
