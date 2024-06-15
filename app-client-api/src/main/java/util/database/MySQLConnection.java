package util.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import util.logs.Logger;

public class MySQLConnection {

	private static final String user = "client";
	private static final String password = "Client123$";
	private static final String mySqlUrl = "jdbc:mysql://localhost/der_grupo_5";
	// public static final String sqlServerUrl = "jdbc:sqlserver://52.73.100.194:1433;databaseName=der_grupo_5;user=sa;password=senha123;encrypt=true;trustServerCertificate=true;";
	public static final String sqlServerUrl = "jdbc:sqlserver://44.206.49.167:1433;databaseName=der_grupo_5;user=sa;password=Senha123;encrypt=true;trustServerCertificate=true;";

	public static Connection ConnectionMySql() throws SQLException {
		try {
			return DriverManager.getConnection(mySqlUrl, user, password);
		} catch (SQLException e) {
			Logger.logError("Erro ao conectar ao MySQL:: ", e.getMessage(), e);
			throw e;
		}
	}

	public static Connection ConnectionSqlServer() throws SQLException {
		try {
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return DriverManager.getConnection(sqlServerUrl);
		} catch (SQLException e) {
			Logger.logError("Erro ao conectar ao SQL Server:: ", e.getMessage(), e);
			throw e;
		}
	}
}