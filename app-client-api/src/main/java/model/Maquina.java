package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Maquina {
    private String nomeMaquina;
    private String sistOperacional;

    private String fabricante;
    private String placaMae;
    private String versaoBios;
    private List<Map<String, List<Object>>> componentes;

    public Maquina(String nomeMaquina, String sistOperacional, String fabricante, String placaMae, String versaoBios) {
        this.nomeMaquina = nomeMaquina;
        this.sistOperacional = sistOperacional;
        this.fabricante = fabricante;
        this.placaMae = placaMae;
        this.versaoBios = versaoBios;
        this.componentes = new ArrayList<>();
    }

    public Maquina() {
        this.componentes = new ArrayList<>();
    }

    public String getNomeMaquina() {
        return nomeMaquina;
    }

    public void setNomeMaquina(String nomeMaquina) {
        this.nomeMaquina = nomeMaquina;
    }

    public String getSistOperacional() {
        return sistOperacional;
    }

    public void setSistOperacional(String sistOperacional) {
        this.sistOperacional = sistOperacional;
    }

    public List<Map<String, List<Object>>> getComponentes() {
        return componentes;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getPlacaMae() {
        return placaMae;
    }

    public void setPlacaMae(String placaMae) {
        this.placaMae = placaMae;
    }

    public String getVersaoBios() {
        return versaoBios;
    }

    public void setVersaoBios(String versaoBios) {
        this.versaoBios = versaoBios;
    }

    public void adicionarRegistro(String nomeComponente, Object componente) {
        boolean chaveExistente = componentes.stream()
                .anyMatch(mapa -> mapa.containsKey(nomeComponente));

        if (!chaveExistente) {
            Map<String, List<Object>> novoRegistro = new HashMap<>();
            novoRegistro.put(nomeComponente, new ArrayList<>(List.of(componente)));
            componentes.add(novoRegistro);
        } else {
            componentes.stream()
                    .filter(mapa -> mapa.containsKey(nomeComponente))
                    .findFirst()
                    .ifPresent(mapa -> mapa.get(nomeComponente).add(componente));
        }
    }

    public void listarComponentes() {
        System.out.println("Listagem de Componentes:");
        for (Map<String, List<Object>> registro : componentes) {
            for (Map.Entry<String, List<Object>> entry : registro.entrySet()) {
                String nomeComponente = entry.getKey();
                List<Object> componentes = entry.getValue();
                System.out.println("- " + nomeComponente + ": " + componentes);
            }
        }
    }
}
