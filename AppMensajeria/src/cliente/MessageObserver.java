package cliente;

/**
 * Interfaz del patrón Observer para la recepción de mensajes de chat.
 * Las clases que implementen esta interfaz serán notificadas
 * cuando llegue un nuevo mensaje.
 */

public interface MessageObserver {
	/**
     * Recibe un mensaje enviado en el chat.
     *
     * @param mensaje mensaje recibido
     */
 void recibirMensaje(String mensaje);
}