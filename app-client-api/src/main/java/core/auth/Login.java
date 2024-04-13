package core.auth;

import exception.AutenticationException;
import model.Usuario;
import service.ServiceUser;

public class Login {
    private Seguranca seguranca;
    private ServiceUser serviceUser;

    public Login() {
        this.seguranca = new Seguranca();
        this.serviceUser = new ServiceUser();
    }
    public Usuario login(String email, String senha) throws AutenticationException {
        try {
            if (seguranca.autenticarUsuario(email, senha)) {
                Usuario usuario = serviceUser.autenticarUsuario(email, senha).get();
                return usuario;
            }
        } catch (AutenticationException e) {
            System.out.println("Erro ao fazer login: " + e.getMessage());
        }
        return null;
    }
}
