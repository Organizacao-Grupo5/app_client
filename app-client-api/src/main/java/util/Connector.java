package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.*;

public class Connector {
    private static final Logger logger = Logger.getLogger(Connector.class.getName());
    public static void ConBD(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost/crudEmJava", "root", "vsops");
        } catch (SQLException | ClassNotFoundException e){
            logger.log(Level.SEVERE, ("Erro no mysql"));
        }
    }
}
