package util.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLConnection {
	private static final Logger logger = Logger.getLogger(MySQLConnection.class.getName());

	private static boolean hml = false;

	private static final String CONNECTION_STRING = "jdbc:mysql://54.167.113.168:3306/der_grupo_5";
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "urubu100";

	public static Connection ConBD() throws SQLException {
		try {
			if (hml){
				return DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
			} else {
				return DriverManager.getConnection("jdbc:mysql://localhost/der_grupo_5", "client", "Client123$");
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Erro ao conectar ao banco de dados", e);
			throw e;
		}
	}
}