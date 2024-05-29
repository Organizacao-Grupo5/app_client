package util.security;

import util.exception.AutenticationException;
import model.Usuario;
import service.ServiceUser;
import util.logs.Logger;

public class Login {
	private Seguranca seguranca;
	private ServiceUser serviceUser;

	public Login() {
		this.seguranca = new Seguranca();
		this.serviceUser = new ServiceUser();
	}

	public Usuario login(String email, String senha) throws AutenticationException {
	Logger.logInfo("Verificando informações de login do usuário.");
		try {
			if (seguranca.autenticarUsuario(email, senha)) {
				Usuario usuario = serviceUser.autenticarUsuario(email, senha).get();
		Logger.logInfo("Usuário foi encontrado com sucesso.");
				return usuario;
			}
		} catch (Exception e) {
		Logger.logError("Erro ao autenticar o usuário: ", e.getMessage(), e);
		}
		return null;
	}

	public void updatePasswordUser(String senha, Integer id ){
		try {
			serviceUser.updatePasswordUser(senha,id);
		} catch (Exception e) {
		Logger.logError("Erro ao autenticar o usuário: ", e.getMessage(), e);
		}

	}
}
