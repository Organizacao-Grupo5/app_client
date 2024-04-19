package model;

public class Volume {
    private Integer idVolume;
    private String nome;
    private String volume;
    private Double disponivel;
    private Double total;
    private String tipo;
    private String uuid;
    private String pontoDeMontagem;

    public Volume() {
    }

    public Volume(String nome, String volume, Double disponivel, Double total, String tipo, String uuid, String pontoDeMontagem) {
        this.nome = nome;
        this.volume = volume;
        this.disponivel = disponivel;
        this.total = total;
        this.tipo = tipo;
        this.uuid = uuid;
        this.pontoDeMontagem = pontoDeMontagem;
    }

    public Integer getIdVolume() {
        return idVolume;
    }

    public void setIdVolume(Integer idVolume) {
        this.idVolume = idVolume;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public Double getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Double disponivel) {
        this.disponivel = disponivel;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPontoDeMontagem() {
        return pontoDeMontagem;
    }

    public void setPontoDeMontagem(String pontoDeMontagem) {
        this.pontoDeMontagem = pontoDeMontagem;
    }
}
