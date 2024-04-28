package service;

import dao.UsuarioDAO;
import model.Usuario;

import java.util.Optional;

public class ServiceUser {
	UsuarioDAO usuarioDao = new UsuarioDAO();

	public ServiceUser() {
		this.usuarioDao = usuarioDao;
	}

	public Optional<Usuario> autenticarUsuario(String email, String senha) {
		return usuarioDao.findByEmailAndSenha(email, senha);
	}
}
