package util.security;

import com.mysql.cj.util.StringUtils;
import util.exception.AutenticationException;
import model.Usuario;
import service.ServiceUser;
import util.logs.Logger;

import java.util.Optional;

public class Seguranca {

	private ServiceUser serviceUser;

	public Seguranca() {
		this.serviceUser = new ServiceUser();
	}

	public boolean autenticarUsuario(String email, String senha) throws AutenticationException {
		if (StringUtils.isNullOrEmpty(email) || StringUtils.isNullOrEmpty(senha)) {
			Logger.logWarning("Credencias de acesso inválidas.");
			throw new AutenticationException("Email e/ou senha não podem estar vazios!");
		} else {
			Optional<Usuario> usuario = serviceUser.autenticarUsuario(email, senha);
			if (!usuario.isPresent()) {
				Logger.logWarning("Usuário não encontrado.");
				throw new AutenticationException("Falha ao autenticar o usuário. Verifique suas credenciais.");
			}
			return true;
		}
	}
}
