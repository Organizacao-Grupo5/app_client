package core.auth;

import com.mysql.cj.util.StringUtils;
import exception.AutenticationException;
import model.Usuario;
import service.ServiceUser;

import java.util.Optional;

public class Seguranca {

    private ServiceUser serviceUser;

    public Seguranca() {
        this.serviceUser = new ServiceUser();
    }

    public boolean autenticarUsuario(String email, String senha) throws AutenticationException {
        if (StringUtils.isNullOrEmpty(email) || StringUtils.isNullOrEmpty(senha)) {
            throw new AutenticationException("Email e/ou senha não podem estar vazios!");
        } else {
            Optional<Usuario> usuario = serviceUser.autenticarUsuario(email, senha);
            if (!usuario.isPresent()) {
                throw new AutenticationException("Falha ao autenticar o usuário. Verifique suas credenciais.");
            }
            return true;
        }
    }
}
