package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import modelo.Grupo;
import persistencia.GrupoDAO;

/**
 *Servidor independiente por cada grupo de chat.
 *Cada instancia de esta clase escucha en un puerto TCP específico y gestiona toda las conexiones de clientes que se unen a ese grupo.
 *
 *Responsabilidades principales:
 * - Iniciar escucha en el puerto asignado al grupo
 * - Aceptar conexiones entrantes de clientes
 * - Delegar cada conexión a un hilo independiente (ManejadorCliente)
 * - Mantener la lógica de broadcast y persistencia a través de ServicioChat
 */
public class ServidorGrupo {
	
	private final Grupo grupo;
	private ServerSocket serverSocket;
	private ServicioChat servicioChat;
	
	
	/**
	 * Constructor del servidor de un grupo concreto
	 * 
	 * @param grupo el objeto Grupo con id, nombre, clave y puerto
	 */
	public ServidorGrupo(Grupo grupo) {
		this.grupo = grupo;
		this.servicioChat = new ServicioChat(grupo.getId());
	}
	
	/**
	 * Inicia el servidor y entra en un bucle infinito aceptando conexiones.
	 * Cada cliente nuevo se atiende en un hilo separado.
	 */
	public void iniciar() {
		try {
			serverSocket = new ServerSocket(grupo.getPuerto());
			System.out.println("Servidor del grupo ID: " + grupo.getNombre() + 
					" escuchando en puerto " + grupo.getPuerto());
			
			while (true) {
				Socket socketCliente = serverSocket.accept();
				System.out.println("Nueva conexión aceptada desde: " + socketCliente.getInetAddress());
				
				ManejadorCliente manejador = new ManejadorCliente(socketCliente, grupo, servicioChat);
				Thread hiloCliente = new Thread(manejador, "Cliente-" + socketCliente.getInetAddress());
				hiloCliente.start();
			}
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	
	/**
	 * Punto de entrada para lanzar un servidor de grupo desde línea de comandos
	 * @param args argumento 0 = clave secreta del grupo
	 */
	  public static void main(String[] args) {
	        if (args.length != 1) {
	            System.out.println("Se necesita la clave del grupo como argumento");
	            return;
	        }

	        String claveGrupo = args[0];
	        GrupoDAO grupoDAO = new GrupoDAO();
	        Grupo grupo;
	        try {
	            grupo = grupoDAO.obtenerGrupoPorClave(claveGrupo);
	            if (grupo == null) {
	                System.out.println("No se encontró el grupo con clave: " + claveGrupo);
	                return;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return;
	        }

	        ServidorGrupo servidor = new ServidorGrupo(grupo);
	        servidor.iniciar();
	    }
	}