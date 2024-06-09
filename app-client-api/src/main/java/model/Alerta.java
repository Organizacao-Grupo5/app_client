package model;

public class Alerta {
    private Integer idAlerta;
    private String mensagem;
    private String tipoAlerta;
 
    public Alerta() {}
    
    public Alerta(Integer idAlerta, String mensagem, String tipoAlerta) {
        this.idAlerta = idAlerta;
        this.mensagem = mensagem;
        this.tipoAlerta = tipoAlerta;
    }

    public Integer getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(Integer idAlerta) {
        this.idAlerta = idAlerta;
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

}
