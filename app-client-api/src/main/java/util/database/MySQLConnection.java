package util.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLConnection {
	private static final Logger logger = Logger.getLogger(MySQLConnection.class.getName());

	public static Connection ConBD() throws SQLException {
		try {
			return DriverManager.getConnection("jdbc:mysql://localhost/der_grupo_5", "root", "SUA SENHA DO BANCO");
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Erro ao conectar ao banco de dados", e);
			throw e;
		}
	}
}