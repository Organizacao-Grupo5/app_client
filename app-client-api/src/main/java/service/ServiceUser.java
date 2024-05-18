package service;

import dao.UsuarioDAO;
import model.Usuario;

import java.util.Optional;

public class ServiceUser {
	UsuarioDAO usuarioDao;

	public ServiceUser() {
		this.usuarioDao = new UsuarioDAO();
	}

	public Optional<Usuario> autenticarUsuario(String email, String senha) {
		return usuarioDao.findByEmailAndSenha(email, senha);
	}
}
