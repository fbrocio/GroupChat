package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import modelo.Mensaje;

/**
 * Data Access Object (DAO) para la entidad Mensaje.
 * Gestiona la persistencia de mensajes en la base de datos MySQL,
 * incluyendo inserción de nuevos mensajes y recuperación de historiales.
 */

public class MensajeDAO {
	
	/**
	 * Inserta un nuevo mensaje en la base de datos
	 * Recupera y asigna el ID autogenerado al objeto Mensaje.
	 * @param m el mensaje a guardar 
	 * @throws SQLException si ocurre un error durante la inserción
	 */
	public void insertar(Mensaje m) throws SQLException {
		String sql = "INSERT INTO mensaje(contenido, fecha, grupo_id, usuario_id)"
				+ "VALUES (?,?,?,?)";
		
		try (Connection con = ConexionBD.getConnection();
				PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			
			pst.setString(1, m.getContenido());
			pst.setTimestamp(2, Timestamp.valueOf(m.getFecha()));
			pst.setInt(3, m.getGrupoId());
			pst.setInt(4, m.getUsuarioId());
			pst.executeUpdate();
			
			//Recuperar el ID autogenerado
			try(ResultSet generatedKeys = pst.getGeneratedKeys()){
				if(generatedKeys.next()) {
					m.setId(generatedKeys.getInt(1));
				}
			}
		}
	}
	
	/**
	 * Recupera el historial completo de mensajes de un grupo
	 * @param grupoId identificador del grupo
	 * @return lista de mensajes
	 * @throws SQLException si falla la consulta
	 */
	public List<Mensaje> verHistorial(int grupoId) throws SQLException {
		String sql = "SELECT contenido, fecha, usuario_id "
				+ "FROM mensaje "
				+ "WHERE grupo_id = ? "
				+ "ORDER BY fecha ASC";
		
		List<Mensaje> historial = new ArrayList<>();
		try (Connection con = ConexionBD.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)){
			
			pst.setInt(1,grupoId);
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				historial.add(new Mensaje(
						rs.getString("contenido"),
						rs.getTimestamp("fecha").toLocalDateTime(),
						rs.getInt("usuario_id")
						));
			}
		}
		return historial;
	}
	
	/**
	 * Recupera solo los mensajes enviados por un usuario concreto en u grupo concreto
	 * Útil para la funcionalidad de filtrar por usuario
	 * @param grupoId id del grupo
	 * @param usuarioId id del usuario
	 * @return lista de mensajes de ese usuario en el grupo (ordenados cronológicamente)
	 * @throws SQLException si falla la consulta
	 */
	public List<Mensaje> verHistorialUsuario(int grupoId, int usuarioId) throws SQLException{
		String sql = "SELECT contenido, fecha "
				+ "FROM mensaje "
				+ "WHERE grupo_id = ? AND usuario_id = ? "
				+ "ORDER BY fecha ASC";
		List<Mensaje> historialUsuario = new ArrayList<>();
		
		try(Connection con = ConexionBD.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)){
			
			pst.setInt(1,grupoId);
			pst.setInt(2,usuarioId);
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				historialUsuario.add(new Mensaje(
						rs.getString("contenido"),
						rs.getTimestamp("fecha").toLocalDateTime()
						));
			}
		}
		return historialUsuario;
	}

}
