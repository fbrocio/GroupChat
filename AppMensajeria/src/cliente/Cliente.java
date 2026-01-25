package cliente;

import java.io.*;
import java.net.Socket;

/**
 * Clase básica de conexión TCP
 * Maneja el socket abierto, envía y recibe mensajes como texto plano.
 */
public class Cliente {
	private String host;
	private int puerto;
	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;
	
	/**
	 * Crea el cliente
	 * @param host
	 * @param puerto
	 */
	public Cliente(String host, int puerto) {
		this.host = host;
		this.puerto = puerto;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	/**
	 * Abre el socket y configura flujos de entrada / salida.
	 * @throws IOException si falla la conexión
	 */
	public void conectar() throws IOException{
		socket = new Socket(host, puerto);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	}
		
	/**
	 * Envia un mensaje al servidor
	 * @param mensaje
	 * @throws IOException
	 */
	public void enviarMensaje(String mensaje) throws IOException {
	    if (mensaje == null) {
	        mensaje = ""; 
	    }
	    out.write(mensaje);
	    out.newLine();
	    out.flush();
	}
		
	/**
	 * Lee la siguiente línea del servidor.
	 * @return
	 * @throws IOException
	 */
	public String recibirMensaje() throws IOException {
		return in.readLine();
	}
		

}
