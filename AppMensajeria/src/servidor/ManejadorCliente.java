package servidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import modelo.Grupo;
import modelo.Usuario;
import persistencia.UsuarioDAO;

/**
* Clase que maneja la conexión de un cliente al servidor de chat.
* Implementa {@link Runnable} para poder ejecutarse en un hilo separado
* y {@link ChatObserver} para recibir mensajes del servicio de chat.
* 
* Se encarga de:
* 
*     Autenticar al usuario.
*     <Verificar la clave del grupo.
*     Registrar al cliente en el servicio de chat.
*     Enviar el historial de mensajes si se solicita.
*     Recibir y reenviar mensajes del cliente.
*
*/

public class ManejadorCliente implements Runnable, ChatObserver {

    private Socket socket;
    private Grupo grupo;
    private ServicioChat servicioChat;
    private Usuario usuario;
    private PrintWriter out;
    
    /**
     * Crea un nuevo manejador de cliente para un socket, grupo y servicio de chat específicos.
     *
     * @param socket Socket que representa la conexión con el cliente.
     * @param grupo Grupo de chat al que pertenece el cliente.
     * @param servicioChat Servicio de chat que gestiona los mensajes y clientes.
     */ 

    public ManejadorCliente(Socket socket, Grupo grupo, ServicioChat servicioChat) {
        this.socket = socket;
        this.grupo = grupo;
        this.servicioChat = servicioChat;
    }
    
    /**
     * Ejecuta el hilo del manejador de cliente.
     * 
     * Lectura de alias, contraseña y clave de grupo para autenticación.
     * Validación del usuario y clave de grupo.
     * Registro del cliente en el {@link ServicioChat}.
     * Envío del historial si el cliente lo solicita.
     * Recepción continua de mensajes y reenvío a otros clientes.
     *
     */
    @Override
    public void run() {

        try (
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()))
        ) {
            out = new PrintWriter(socket.getOutputStream(), true);

            // ===== Autenticación =====
            String alias = in.readLine();
            String password = in.readLine();
            String claveGrupo = in.readLine();

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuario = usuarioDAO.login(alias, password);

            if (usuario == null) {
                out.println("Usuario no encontrado");
                return;
            }

            if (!grupo.getClave().equals(claveGrupo)) {
                out.println("Error en clave de grupo");
                return;
            }

            out.println("OK");

            // ===== Registro del cliente =====
            servicioChat.registrarCliente(this);
            
            // ===== Historial =====
            String opcion = in.readLine();
            if (opcion != null && opcion.startsWith("/historial")) {
                String[] partes = opcion.split(" ");
                boolean cargarHistorial = partes.length > 1 &&
                        Boolean.parseBoolean(partes[1]);

                if (cargarHistorial) {
                    servicioChat.enviarHistorial(this);
                }
            }

            // ===== Recepción de mensajes =====
            String mensaje;
            while ((mensaje = in.readLine()) != null) {
                servicioChat.enviarMensaje(
                        usuario.getId(),
                        usuario.getAlias(),
                        mensaje
                );
            }

        } catch (SocketException e) {
            // Desconexión normal del cliente
            System.out.println("Cliente desconectado: " + socket.getRemoteSocketAddress());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            servicioChat.eliminarCliente(this);
        }
    }


    /**
     * Método invocado por el {@link ServicioChat} para enviar un mensaje al cliente.
     *
     * @param mensaje Mensaje a enviar al cliente.
     */
    @Override
    public void recibirMensaje(String mensaje) {
        out.println(mensaje);
    }
}

