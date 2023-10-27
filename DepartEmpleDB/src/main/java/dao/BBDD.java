package dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class BBDD {

	/**
	 * Conector a la base de datos
	 */
	private static Connection conn = null;

	/**
	 * Tipo de base de datos [sqlite|mariadb|...]
	 */
	public static String typeDB = null;

	/**
	 * Constructor
	 * 
	 * Establece una conexión con la base de datos
	 */
	private BBDD() {
		try {
			Properties prop = new Properties();
			prop.load(new FileReader("propiedades.propierties"));

			typeDB = prop.getProperty("db");
			String driver = prop.getProperty("driver");
			String dsn = prop.getProperty("dsn");
			String user = prop.getProperty("user", "");
			String pass = prop.getProperty("pass", "");

			Class.forName(driver);
			conn = DriverManager.getConnection(dsn, user, pass);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Devuelve una conexión a la base de datos
	 * 
	 * @return Conexión a la base de datos
	 */
	public static Connection getConnection() {
		if (conn == null) {
			new BBDD();
		}
		return conn;
	}

	/**
	 * Cierra la conexión
	 */
	public static void close() {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}