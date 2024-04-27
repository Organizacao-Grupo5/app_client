package service;

import dao.MaquinaDAO;
import exception.ExceptionMonitoring;
import model.Maquina;
import model.Usuario;
import util.logs.Logger;

import java.net.InetAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ServicePC {
    MaquinaDAO maquinaDAO = new MaquinaDAO();

    public Maquina verificarMaquina(Usuario usuario){
        try {
            String ipv4 = InetAddress.getLocalHost().getHostAddress();
            Optional<Maquina> maquina = maquinaDAO.monitorarMaquina(usuario);

            if (maquina.isEmpty()){
                Logger.logWarning("Não encontramos nenhuma máquina vinculada ao seu usuário, entre em contato com seu gestor!");
            } else if (!maquina.get().getIpv4().equals(ipv4)) {
                Logger.logWarning("A máquina / rede a qual você está utilizando não está vinculada ao seu usuário!");
            } else{
                return maquina.get();
            }

            return null;
        } catch (Exception e){
            Logger.logError("Erro ao acessar máquina:", e.getMessage(), e);
        }
        return null;
    }
}
