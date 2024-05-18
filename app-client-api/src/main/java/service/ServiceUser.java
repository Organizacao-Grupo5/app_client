package service;

import dao.UsuarioDAO;
import model.Usuario;

import java.sql.SQLException;
import java.util.Optional;

public class ServiceUser {
	UsuarioDAO usuarioDao;

	public ServiceUser() {
		this.usuarioDao = new UsuarioDAO();
	}

	public Optional<Usuario> autenticarUsuario(String email, String senha) throws SQLException {
		return usuarioDao.findByEmailAndSenha(email, senha);
	}
}
