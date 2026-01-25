package servidor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import modelo.Grupo;
import persistencia.GrupoDAO;
/**
 * Clase principal del servidor.
 * Se encarga de iniciar los servidores de todos los grupos de chat
 * definidos en la base de datos, creando un servidor independiente
 * para cada grupo.
 */
public class ServidorApp {
	
	public static void main(String[]args) throws IOException {
		
		GrupoDAO grupoDAO = new GrupoDAO();
		GestorGrupos gestor = new GestorGrupos(grupoDAO);
		
		
		try {
			List<Grupo> grupos;
			grupos = grupoDAO.obtenerTodos();
			for (Grupo g : grupos) {
				gestor.iniciarServidor(g);
			} 
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Todos los servidores de grupo iniciados");
	}

}
