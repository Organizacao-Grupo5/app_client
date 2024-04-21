package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connector {
    private static final Logger logger = Logger.getLogger(Connector.class.getName());

    public static Connection ConBD() throws SQLException {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/visualOps", "root", "54157592808");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao conectar ao banco de dados", e);
            throw e;
        }
    }
}
