package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.Usuario;

/**
 * Data Access Object (DAO) para la entidad Usuario.
 * Gestiona el registro, autenticación y consultas de usuarios en la base de datos
 */
public class UsuarioDAO {
	
	/**
	 * Registra un nuevo usuario en la base de datos
	 * @param u objeto Usuario con alias y password (id se genera automáticamente)
	 * @return true si el registro fue exitoso, false si el alias ya existe o se genera un error
	 * @throws SQLException si ocurre un error grave de base de datos
	 */
	public boolean registrarUsuario(Usuario u) throws SQLException {
		String sql = "INSERT INTO usuario(alias, password) VALUES(?,?)";
		
		try (Connection con = ConexionBD.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)){
			
			pst.setString(1,u.getAlias());
			pst.setString(2,u.getPassword());
			pst.executeUpdate();
			return true;
			
	} catch (SQLException e) {
		return false;
		}
	}
	
	/**
	 * Valida un usuario mediante alias y contraseña
	 * 
	 * @param alias nombre de usuario
	 * @param password contraseña (en texto plano, siguiendo los requisitos)
	 * @return objeto Usuario completo si las credenciales son correctas, null en caso contrario
	 * throws SQLException si falla la consulta a la base de datos
	 */
	public Usuario login(String alias, String password) throws SQLException {
		Usuario u = new Usuario();
		String sql = "SELECT id, alias, password FROM usuario WHERE alias = ? AND password = ?";
		try(Connection con = ConexionBD.getConnection();
		PreparedStatement pst = con.prepareStatement(sql)){
			pst.setString(1, alias);
			pst.setString(2, password);
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				u.setId(rs.getInt("id"));
				u.setAlias(rs.getString("alias"));
				u.setPassword(rs.getString("password"));
				return u;
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				}
			return null; //Devuelve null si no existe o da error
			
	}
	
	/**
	 * Busca un usuario por su alias
	 * @param alias el nombre de usuario a buscar
	 * @return objeto Usuario si existe, null si no se encuentra
	 * @throws SQLException si ocurre error en la consulta
	 */
	public Usuario getUsuarioFromAlias(String alias) throws SQLException {
		Usuario u = new Usuario();
		String sql ="SELECT * FROM usuario WHERE alias = ?";
		try(Connection con = ConexionBD.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)){
			
			pst.setString(1, alias);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				u.setId(rs.getInt("id"));
				u.setAlias(rs.getString("alias"));
				u.setPassword(rs.getString("password"));
			}
			
		}
		return u;
	}
		
	/**
	 * Busca un usuario con su ID numérico.	
	 * @param id id del usuario
	 * @return objeto Usuario si existe, null si no se encuentra
	 * @throws SQLException si falla la consulta
	 */
	public Usuario getUsuarioFromId(int id) throws SQLException {
		Usuario u = new Usuario();
		String sql ="SELECT * FROM usuario WHERE id = ?";
		try(Connection con = ConexionBD.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)){
			
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				u.setId(rs.getInt("id"));
				u.setAlias(rs.getString("alias"));
			}
			
		}
		return u;
	}
}
