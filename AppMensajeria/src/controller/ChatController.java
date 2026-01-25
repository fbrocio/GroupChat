package controller;

import cliente.Cliente;
import cliente.MessageListener;
import gui.GroupChat;
import modelo.Grupo;
import modelo.Usuario;

import java.io.IOException;

public class ChatController {

    private Cliente cliente;
    private GroupChat chatGUI;
    private String aliasUsuario;
   

    public ChatController(GroupChat chatGUI) {
        this.chatGUI = chatGUI;
    }

    /**
     * Conecta al servidor TCP usando la clase Cliente.
     * Crea un MessageListener en un hilo para recibir mensajes.
     */
    public void conectar(String host, int puerto, Usuario u, Grupo g, boolean cargarHistorial) {
    	this.aliasUsuario = u.getAlias();
        try {
        	// Validación de usuario
        	if (u.getAlias() == null || u.getAlias().isEmpty() ||
        	    u.getPassword() == null || u.getPassword().isEmpty()) {
        	    chatGUI.showError("Alias y contraseña son obligatorios");
        	    return;
        	}

        	// Validación de grupo
        	if (g.getClave() == null || g.getClave().isEmpty()) {
        	    chatGUI.showError("Clave del grupo es obligatoria");
        	    return; // sale del método sin intentar conectar
        	}
        	
        	cliente = new Cliente(host, puerto);
            cliente.conectar();

         // Se inicia el hilo para escuchar mensajes
            MessageListener listener = new MessageListener(cliente.getSocket());
            listener.addObserver(chatGUI);
            new Thread(listener).start();
            
            // Mismo orden que en ManejadorCliente 
            cliente.enviarMensaje(u.getAlias());     // 1. El servidor lee 'alias'
            cliente.enviarMensaje(u.getPassword());  // 2. El servidor lee 'password'
            cliente.enviarMensaje(g.getClave());      // 3. El servidor lee 'claveGrupo'
            
            // El servidor lee 'opcion' para el historial 
            String opcionHistorial = "/historial " + (cargarHistorial ? "true" : "false");
            cliente.enviarMensaje(opcionHistorial);

            

        } catch (IOException e) {
            chatGUI.showError("Error: " + e.getMessage());
        }
    }

    /**
     * Envía un mensaje al servidor
     */
    public void sendMessage(String mensaje, String aliasUsuario) {
        if (cliente != null) {
            try {                
            	cliente.enviarMensaje(mensaje);
            } catch (IOException e) {
                e.printStackTrace();
                chatGUI.showError("No se pudo enviar el mensaje: " + e.getMessage());
            }
        } else {
            chatGUI.showError("No estás conectado al servidor.");
        }
    }

    /**
     * Cierra la conexión con el servidor
     */
    public void disconnect() {
        if (cliente != null) {
            try {
                cliente.enviarMensaje("/disconnect"); 
                cliente = null; 
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public String getUsuarioAlias() {
    	return aliasUsuario;
    }
}
