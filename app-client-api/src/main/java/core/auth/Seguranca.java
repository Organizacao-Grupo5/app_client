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
        if (StringUtils.isNullOrEmpty(email) || StringUtils.isNullOrEmpty(senha)){
            throw new AutenticationException("Email e/ou senha não podem estar vazios!");
        } else {
            Optional<Usuario> usuario = serviceUser.autenticarUsuario(email, senha);
            if (!usuario.isPresent()) {
                throw new AutenticationException("Falha ao autenticar o usuário. Verifique suas credenciais.");
            }
            if (verificarStatusBloqueadoUsuario(usuario.get())) {
                throw new AutenticationException("O usuário está bloqueado. Entre em contato com o administrador.");
            }
            return true;
        }
    }

    // Alterar para a tabela de status
    public boolean verificarStatusBloqueadoUsuario(Usuario usuario) {
        return usuario != null && usuario.getStatus().equalsIgnoreCase("bloqueado");
    }

    // Altera para a tabela de acesso
    public boolean verificarPermissaoAdmin(Usuario usuario) {
        return usuario != null && usuario.getStatus().equalsIgnoreCase("admin");
    }

    // Alterar para a tabela de acesso
    public boolean verificarPermissaoAcesso(Usuario usuario) {
        return usuario != null && usuario.getStatus().equalsIgnoreCase("ativo");
    }
}
