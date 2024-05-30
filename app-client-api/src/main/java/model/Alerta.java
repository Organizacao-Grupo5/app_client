package model;

public class Alerta {
    private Integer idAlerta;
    private Double percentualMaximo; 
    private String mensagem;
    private String tipoAlerta;
    private String definicaoPercentual;
 
    public Alerta() {}
    
    public Alerta(Integer idAlerta, Double percentualMaximo, String mensagem, String tipoAlerta, String definicaoPercentual) {
        this.idAlerta = idAlerta;
        this.percentualMaximo = percentualMaximo;
        this.mensagem = mensagem;
        this.tipoAlerta = tipoAlerta;
        this.definicaoPercentual = definicaoPercentual;
    }

    public Integer getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(Integer idAlerta) {
        this.idAlerta = idAlerta;
    }

    public Double getPercentualMaximo() {
        return percentualMaximo;
    }

    public void setPercentualMaximo(Double percentualMaximo) {
        this.percentualMaximo = percentualMaximo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getTipoAlerta() {
        return tipoAlerta;
    }

    public void setTipoAlerta(String tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }

    public String getDefinicaoPercentual() {
        return definicaoPercentual;
    }

    public void setDefinicaoPercentual(String definicaoPercentual) {
        this.definicaoPercentual = definicaoPercentual;
    }

    
}
