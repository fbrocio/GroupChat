package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Grupo;

/**
 * Data Access Object (DAO) para la entidad Grupo.
 * Contiene las operaciones de lectura principalmente, ya que los grupos se crean de forma estática
 * mediante script SQL inicial (no hay creación desde la aplicación).
 */
public class GrupoDAO {
	
	/**
	 * Busca un grupo por su clave secreta única.
	 * El cliente debe validad esta clave antes de conectarse al puerto correspondiente.
	 * @param clave la clave secreta del grupo
	 * @return el objeto Grupo completo si existe, o {@code null} si no se cuentra
	 */
	public Grupo obtenerGrupoPorClave(String clave) throws SQLException {
		String sql = "SELECT * FROM grupo WHERE clave = ?";
		Grupo grupo = null;
		try(Connection con = ConexionBD.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setString(1, clave);
			try(ResultSet rs = pst.executeQuery()){
			if (rs.next()) {
				return new Grupo(
						rs.getInt("id"),
						rs.getString("nombre"),
						rs.getString("clave"),
						rs.getInt("puerto"));
				
				}
			}
		
		} return null;
	}
	
	/**
	 * Obtiene la lista completa de todos los grupos registrados en la base de datos.
	 * Útil para mostrar al usuario una lista de grupos disponible
	 * @return lista de todos los grupos 
	 * @throws SQLException si ocurre un error en la conexión o consulta
	 */
	
	public List<Grupo> obtenerTodos() throws SQLException {
		String sql = "SELECT * FROM grupo";
		List<Grupo> grupos = new ArrayList<>();
		try(Connection con = ConexionBD.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql)){
			while (rs.next()) {
				grupos.add(new Grupo
						(rs.getInt("id"),
						rs.getString("nombre"),
						rs.getString("clave"),
						rs.getInt("puerto")));
			}
		} return grupos;
				
	}

}
