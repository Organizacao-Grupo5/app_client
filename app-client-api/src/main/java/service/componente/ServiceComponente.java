package service.componente;

import dao.componente.ComponenteDAO;
import model.Maquina;
import util.logs.Logger;

public class ServiceComponente {
    private ComponenteDAO componenteDAO = new ComponenteDAO();

    public void obterComponentes(Maquina maquina){
        try{
            if(componenteDAO.getComponente(maquina)){
                Logger.logInfo("Sua máquina contém componentes registrados");
            } else{
                Logger.logInfo("Sua máquina não contém componentes registrados iremos registrar seus componentes");
            }
        } catch (Exception e){
            Logger.logError("Ocorreu um erro ao obter seus componentes:", e.getMessage(), e);
        }
    }
}
