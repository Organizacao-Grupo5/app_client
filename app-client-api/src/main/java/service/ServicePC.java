package service;

import dao.MaquinaDAO;
import model.Maquina;
import model.Usuario;
import util.logs.Logger;

import java.net.InetAddress;
import java.util.Optional;

import com.sun.jna.platform.win32.Netapi32Util.UserInfo;

public class ServicePC {
	MaquinaDAO maquinaDAO = new MaquinaDAO();
	UserInfo userInfo = new UserInfo();

	public Maquina verificarMaquina(Usuario usuario) {
		try {
			String ipv4 = InetAddress.getLocalHost().getHostAddress();
			Optional<Maquina> maquina = maquinaDAO.monitorarMaquina(usuario);

			if (maquina.isEmpty()) {
				Logger.logWarning(
						"Não encontramos nenhuma máquina vinculada ao seu usuário, entre em contato com seu gestor!");
			} else if (!maquina.get().getIpv4().contains(ipv4)) {
				Logger.logWarning("A máquina / rede a qual você está utilizando não está vinculada ao seu usuário!");
			} else {
				Maquina maquinaExemplo = maquina.get();
				return maquina.get();
			}

			return null;
		} catch (Exception e) {
			Logger.logError("Erro ao acessar máquina:", e.getMessage(), e);
		}
		return null;
	}
}
