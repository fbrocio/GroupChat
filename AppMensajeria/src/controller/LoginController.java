package controller;

import java.sql.SQLException;

import modelo.Usuario;
import persistencia.UsuarioDAO;

/**
 * Responsabilidades:
 * - Gestiona la conexión TCP con el servidor del grupo
 * - Envía credenciales y opción de historial al conectar
 * - Envía mensajes cuando el usuario los escribe
 * - Permite desconexión limpia
 * - Proporciona el alias del usuario para formatear mensajes en la GUI
 */
public class LoginController {

    private UsuarioDAO usuarioDAO;

    public LoginController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    /**
     * Intenta loguear un usuario con alias y password.
     * @return Usuario si las credenciales son correctas, null si no.
     */
    public Usuario login(String alias, String password) {
        try {
			return usuarioDAO.login(alias, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }

    /**
     * Registra un nuevo usuario.
     * IMPORTANTE: tu DAO requiere un id; si tu BD tiene auto-increment, este id no debería ponerse aquí.
     * @param id id del usuario
     * @param alias alias del usuario
     * @param password contraseña
     * @return true si se registró correctamente, false si hubo error
     */
    public boolean register(String alias, String password) {
        Usuario u = new Usuario(alias, password);
        try {
			return usuarioDAO.registrarUsuario(u);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
    }

    /**
     * Obtiene usuario por alias
     */
    public Usuario getUsuarioByAlias(String alias) {
        try {
            return usuarioDAO.getUsuarioFromAlias(alias);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtiene usuario por id
     */
    public Usuario getUsuarioById(int id) {
        try {
            return usuarioDAO.getUsuarioFromId(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
