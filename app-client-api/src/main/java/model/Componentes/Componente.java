package model.Componentes;

import java.util.List;

public interface Componente  {

    void setId(Integer id);
    void setNome(String nome);

    Integer getId();
    String getNome();

    void adicionarEntradaTabela(String chave, String valor);
    String getEntradaTabela(String chave);

    String getFabricante();
    void setFabricante(String fabricante);
    String getPdfLayout();

}
