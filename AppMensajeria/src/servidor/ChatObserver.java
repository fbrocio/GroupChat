package servidor;
/**
 * Interfaz del patrón Observer utilizada en el servidor.
 * Las clases que la implementan reciben los mensajes
 * enviados en un grupo de chat.
 */
public interface ChatObserver {
	void recibirMensaje(String mensaje);
}
