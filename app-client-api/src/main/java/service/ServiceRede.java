package service;

import java.sql.SQLException;
import java.util.List;

import app.integration.Computer;
import dao.Ipv4DAO;
import dao.MaquinaDAO;
import dao.RedeDAO;
import dao.RedeIpv4DAO;
import model.Ipv4;
import model.Maquina;
import model.Rede;
import model.Usuario;
import util.logs.Logger;

public class ServiceRede {
    
    RedeIpv4DAO redeIpv4DAO = new RedeIpv4DAO();
    Rede rede;

    Ipv4DAO ipv4DAO = new Ipv4DAO();

    public Boolean maquinaContemIp(Maquina maquina) {
		try {
			String ip = MaquinaDAO.getIpv4();
						
			if (ipv4DAO.contem(maquina, ip) < 2) {
				return true;
            } else {
                Logger.logWarning("A máquina / rede a qual você está utilizando não está vinculada ao seu usuário!");
			}
		} catch (Exception e) {
			Logger.logError("Erro ao acessar máquina:", e.getMessage(), e);
            e.printStackTrace();
		}
		return false;
	}


	public static void compararDispositivos(List<String> listaIp) {
        try {
            Computer computer = new Computer();
    
            StringBuilder sb = new StringBuilder();
    
            List<List<String>> listaDevices = computer.listarDispositivos(computer.getIpv4());
                
            
            for (List<String> list : listaDevices) {
                for (int i = 0; i < listaIp.size(); i++) {
                    if (i == 0 && list.get(1).substring(0, 5).equals(listaIp.get(0).substring(0, 5))) {
                        sb.append("IPV4:: %s - Corresponde ao seu ip.\n".formatted(list.get(1)));
                        break;
                    } else if (
                        list.get(0).toLowerCase().equals(listaIp.get(i).toLowerCase())
                    ) {
                        sb.append("IPV4:: %s - Corresponde a um de seus funcionários.\n".formatted(list.get(0)));
                        break;
                    } else if (i == listaIp.size()-1) {
                        sb.append("IPV4:: %s - Corresponde a nenhum de seus funcionários.\n".formatted(list.get(0)));
                    }
                    
                }
            }
            System.out.println(sb.toString());
            
        } catch (Exception e) {
            Logger.logError("Ocorreu um erro ao listar os dispositivos:", e.getMessage(), e);
            e.printStackTrace();
        }
	}

    public Rede criarRede(Usuario usuario, Ipv4 ipv4) {
        try {
            RedeDAO redeDAO = new RedeDAO();
            Rede rede = new Rede();
    
            
            if (!redeDAO.existe(rede)) {
                rede = redeDAO.insert(rede);
            }
            
            redeDAO.atribuirId(rede);

            if (!redeIpv4DAO.existe(ipv4, rede)) {
                redeIpv4DAO.insert(rede, ipv4);
            }

            this.rede = rede;
            
        } catch (SQLException e) {
            Logger.logError("Ocorreu um erro ao criar a rede:", e.getMessage(), e);
            e.printStackTrace();
        }
        return rede;
    }

    public Ipv4 criarIpv4(Usuario usuario, Maquina maquina) {
        Ipv4 ipv4 = new Ipv4(maquina);
        try {
            if (!ipv4DAO.existe(ipv4)) {
                ipv4 = ipv4DAO.insert(ipv4);
            }

            ipv4DAO.atribuirId(ipv4);

        } catch (SQLException e) {
            Logger.logError("Ocorreu um erro ao criar ipv4:", e.getMessage(), e);
            e.printStackTrace();
        }
        return ipv4;
    }

    public void listarDispositivos() {
        redeIpv4DAO.selectIps(this.rede);
    }
}
