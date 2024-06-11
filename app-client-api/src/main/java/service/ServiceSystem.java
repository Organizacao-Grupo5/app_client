package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import dao.AlertaDAO;
import dao.ConfiguracaoDAO;
import dao.componente.CapturaDAO;
import dao.componente.ComponenteDAO;
import dao.componente.RegistroAlertasDAO;
import model.Alerta;
import model.Captura;
import model.Configuracao;
import model.componentes.Componente;
import util.logs.Logger;

public class ServiceSystem {
    AlertaDAO alertaDAO;
    CapturaDAO capturaDAO;
    ConfiguracaoDAO configuracaoDAO;
    RegistroAlertasDAO registroAlertasDAO;
    ComponenteDAO componenteDAO;

    public ServiceSystem() {
        alertaDAO = new AlertaDAO();
        capturaDAO = new CapturaDAO();
        configuracaoDAO = new ConfiguracaoDAO();
        registroAlertasDAO = new RegistroAlertasDAO();
        componenteDAO = new ComponenteDAO();
    }
    
    public void configurar(Integer idComponente) throws SQLException {
        try {
            if (!configuracaoDAO.existeConfiguracao(idComponente)) {
                ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO();
                configuracaoDAO.inserirConfiguracao(idComponente);
            }
        } catch (Exception e) {
            Logger.logError("Ocorreu um erro ao inserir dados à entidade Configuracao:", e.getMessage(), e);
        }
    }

    public void registrar(Integer idCaptura, Componente componente) {
        Double valorCaptura = this.obterValorCaptura(idCaptura);
        Configuracao configuracao = this.obterConfiguracao(componente);
        Integer idAlerta = this.pegarIdAlerta(valorCaptura, configuracao);
        
        registroAlertasDAO.gerarRegistro(idCaptura, idAlerta);
    }

    public Integer pegarIdAlerta(Double valorCaptura, Configuracao configuracao) {
        Integer idAlerta = 0;
        try {
            List<Alerta> listaAlertas = alertaDAO.selecionarTodos();
        
            if (valorCaptura >= configuracao.getMinimoParaSerRuim() && valorCaptura <= 100) {
                idAlerta = listaAlertas.stream().filter(alerta -> alerta.getTipoAlerta().equals("RUIM")).map(Alerta::getIdAlerta).findFirst().get();
            } else if (valorCaptura >= configuracao.getMinimoParaSerMedio()) {
                idAlerta = listaAlertas.stream().filter(alerta -> alerta.getTipoAlerta().equals("MÉDIO")).map(Alerta::getIdAlerta).findFirst().get();
            } else {
                idAlerta = listaAlertas.stream().filter(alerta -> alerta.getTipoAlerta().equals("BOM")).map(Alerta::getIdAlerta).findFirst().get();
            }
        } catch (Exception e) {
            Logger.logError("Ocorreu um erro ao pegar informações da entidade Alerta:", e.getMessage(), e);
        }
        return idAlerta;
    }

    public Double obterValorCaptura(Integer idCaptura) {
        Double valorCaptura = 0.0;
        try {
            Optional<Captura> optionalCaptura = capturaDAO.selecionarById(idCaptura);
            valorCaptura = optionalCaptura.get().getDadoCapturaPercent();
        } catch (Exception e) {
            Logger.logError("Ocorreu um erro ao obter informações da entidade Captura:", e.getMessage(), e);
        }
        return valorCaptura;
    }

    public Configuracao obterConfiguracao(Componente componente) {
        Configuracao configuracao = null;
        try {
            Optional<Configuracao> optionalConfiguracao = configuracaoDAO.selecionarByComponente(componente);
            configuracao = optionalConfiguracao.get();    
        } catch (Exception e) {
            Logger.logError("Ocorreu um erro ao obter informações da entidade Configuração:", e.getMessage(), e);
        }
        return configuracao;
    }
}
