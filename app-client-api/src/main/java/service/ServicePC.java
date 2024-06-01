package service;

import dao.MaquinaDAO;
import model.Maquina;
import model.Usuario;
import util.logs.Logger;

import java.util.Optional;


public class ServicePC {
	MaquinaDAO maquinaDAO = new MaquinaDAO();

	public Maquina verificarMaquina(Usuario usuario) {
		try {
			Optional<Maquina> maquina = maquinaDAO.monitorarMaquina(usuario);

			if (maquina.isEmpty()) {
				Logger.logWarning("Não encontramos nenhuma máquina vinculada ao seu usuário, entre em contato com seu gestor!");
			} else {
				return maquina.get();
			}

			return null;
		} catch (Exception e) {
			Logger.logError("Erro ao acessar máquina:", e.getMessage(), e);
		}
		return null;
	}
}
