package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase utilitaria para obtener conexiones a la base de datos MySQL.
 */
public class ConexionBD {
	private static final String URL = "jdbc:mysql://localhost:3306/appmensajeria";
	private static final String USER = "root";
	private static final String PASSWORD = "root";
	
	/**
	 * Obtiene una nueva conexión a la base de datos.
	 * @return Connection activa
	 * @throws SQLException si no se puede conectar
	 */
	public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
