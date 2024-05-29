package model;

import model.componentes.Componente;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Maquina {
	private static Integer idMaquina;
	private String numeroSerial;
	private String modelo;
	private String marca;
	private String hostname;
	private String username;
	private List<String> ipv4;
	private Usuario usuario;
	private List<Componente> componentes;

	public Maquina() {
		componentes = new ArrayList<>();
		this.ipv4 = new ArrayList<>();
	}

	public Maquina(Integer idMaquina, Usuario usuario) {
		this.idMaquina = idMaquina;
		this.ipv4 = new ArrayList<>();
		this.usuario = usuario;
		this.componentes = new ArrayList<>();
	}

	public static int getIdMaquina() {
		return idMaquina;
	}

	public void setIdMaquina(int idMaquina) {
		this.idMaquina = idMaquina;
	}

	public List<String> getIpv4() {
		return ipv4;
	}

	public void setIdMaquina(Integer idMaquina) {
		this.idMaquina = idMaquina;
	}

	public String getNumeroSerial() {
		return numeroSerial;
	}

	public void setNumeroSerial(String numeroSerial) {
		this.numeroSerial = numeroSerial;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public void setIpv4(List<String> ipv4) {
		this.ipv4 = ipv4;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Componente> getComponentes() {
		return componentes;
	}

	public void setComponentes(List<Componente> componentes) {
		this.componentes = componentes;
	}

	public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
	

	public String exibirTabelaComponentes() {
		return (new StringBuilder(componentes.stream()
				.map(Componente::tabelaConvert)
				.collect(Collectors.joining()))
		).toString();
	}

	public String layoutPdfComponentes() {
		return (new StringBuilder(componentes.stream()
				.map(Componente::pdfLayout)
				.collect(Collectors.joining()))
		).toString();
	}

}
