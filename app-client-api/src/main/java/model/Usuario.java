package model;

public class Usuario {
	private int idUsuario;
	private String nome;
	private String cpfCnpj;
	private Integer fkEmpresa;
	private String cargo;
	private String email;
	private String senha;
	private Integer fkPlano;

	public Usuario() {
	}

	public Usuario(int idUsuario, String nome, String cpfCnpj, Integer fkEmpresa, String cargo, String email,
			String senha, Integer fkPlano) {
		this.idUsuario = idUsuario;
		this.nome = nome;
		this.cpfCnpj = cpfCnpj;
		this.fkEmpresa = fkEmpresa;
		this.cargo = cargo;
		this.email = email;
		this.senha = senha;
		this.fkPlano = fkPlano;
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

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
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

	public Integer getFkEmpresa() {
		return fkEmpresa;
	}

	public void setFkEmpresa(Integer fkEmpresa) {
		this.fkEmpresa = fkEmpresa;
	}
}
