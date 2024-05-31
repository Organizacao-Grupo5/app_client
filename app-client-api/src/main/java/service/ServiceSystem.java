package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import dao.AlertaDAO;
import dao.ConfiguracaoDAO;
import dao.componente.CapturaDAO;
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

    public ServiceSystem() {
        alertaDAO = new AlertaDAO();
        capturaDAO = new CapturaDAO();
        configuracaoDAO = new ConfiguracaoDAO();
    }
    
    public void configurar(Componente componente) throws SQLException {
        try {
            ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO();
            configuracaoDAO.inserirConfiguracao(componente);
        } catch (Exception e) {
            Logger.logError("Ocorreu um erro ao inserir dados à entidade Configuracao:", e.getMessage(), e);
            e.printStackTrace();
        }
    }

    public void registrar(Integer idCaptura, Componente componente) {
        Integer idAlerta = this.pegarIdAlerta(idCaptura, componente);

        registroAlertasDAO.gerarRegistro(idCaptura, componente, idAlerta);
    }

    public Integer pegarIdAlerta(Integer idCaptura, Componente componente) {
        Integer idAlerta = 0;
        try {
            Optional<Captura> optionalCaptura = capturaDAO.selecionarById(idCaptura);
            Double valorCaptura = optionalCaptura.get().getDadoCaptura();
            
            Optional<Configuracao>  optionalConfiguracao = configuracaoDAO.selecionarByComponente(componente);
            Float minParaMedio = optionalConfiguracao.get().getMinimoParaSerMedio();
            Float minParaRuim = optionalConfiguracao.get().getMinimoParaSerRuim();
    
            List<Alerta> listaAlertas = alertaDAO.selecionarTodos();
        
            if (valorCaptura <= 100 && valorCaptura >= minParaRuim) {
                idAlerta = listaAlertas.stream().filter(alerta -> alerta.getTipoAlerta().equals("RUIM")).map(Alerta::getIdAlerta).findFirst().get();
            } else if (valorCaptura >= minParaMedio) {
                idAlerta = listaAlertas.stream().filter(alerta -> alerta.getTipoAlerta().equals("MÉDIO")).map(Alerta::getIdAlerta).findFirst().get();
            } else {
                idAlerta = listaAlertas.stream().filter(alerta -> alerta.getTipoAlerta().equals("BOM")).map(Alerta::getIdAlerta).findFirst().get();
            }
        } catch (Exception e) {
            Logger.logError("Ocorreu um erro ao tentar pegar informações da entidade Captura, Configuração ou Alerta:", e.getMessage(), e);
            e.printStackTrace();
        }
        return idAlerta;
    }
}
