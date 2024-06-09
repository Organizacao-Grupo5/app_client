package util.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLConnection {
	private static final Logger logger = Logger.getLogger(MySQLConnection.class.getName());


	private static final String user = "client";
	private static final String password = "Client123$";
	private static final String mySqlUrl = "jdbc:mysql://mysql:3306/der_grupo_5";
	public static final String sqlServerUrl = "jdbc:sqlserver://52.73.100.194:1433;databaseName=der_grupo_5;user=sa;password=senha123;encrypt=true;trustServerCertificate=true;";

	public static Connection ConnectionMySql() throws SQLException {
		try {
			return DriverManager.getConnection(mySqlUrl, user, password);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Erro ao conectar ao banco de dados", e);
			throw e;
		}
	}

	public static Connection ConnectionSqlServer() throws SQLException {
		try {
			return DriverManager.getConnection(sqlServerUrl);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Erro ao conectar ao banco de dados", e);
			throw e;
		}
	}
}