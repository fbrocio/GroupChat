package servidor;
/**
 * Clase encargada de gestionar los servidores de los distintos grupos de chat.
 * Inicia un proceso servidor independiente para cada grupo y mantiene
 * el control de los procesos activos.
 */
import persistencia.GrupoDAO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import modelo.Grupo;

public class GestorGrupos {
	
	private GrupoDAO grupoDAO;
	private Map<Integer, Process> servidores;
	
	public GestorGrupos(GrupoDAO grupoDAO) {
		this.grupoDAO = grupoDAO;
		this.servidores = new HashMap<>();
	}
	
	public void iniciarServidor(Grupo grupo) throws IOException {
	    if (servidores.containsKey(grupo.getId())) {
	        return;
	    }

        String classPath = new java.io.File("bin").getAbsolutePath() + 
        		";" + 
        		new java.io.File("lib/mysql-connector-j-9.1.0.jar").getAbsolutePath();
        		
        ProcessBuilder pb = new ProcessBuilder(
                "java",
                "-cp", 
                classPath,
                "servidor.ServidorGrupo",
                grupo.getClave()                  // pasamos la clave del grupo como argumento
        );
	    	pb.inheritIO();
	    	Process proceso = pb.start();
	    	
	    	 // Guardamos la referencia del proceso
	        servidores.put(grupo.getId(), proceso);

	        System.out.println("Servidor del grupo " + grupo.getId() + " iniciado como proceso independiente");
	    }
	
    
    public Process obtenerServidor(int grupoId) {
    	return servidores.get(grupoId);
    }
}