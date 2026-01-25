package servidor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
/**
 * Clase observable del patrón Observer para el chat.
 * Mantiene una lista de clientes conectados (observadores)
 * y notifica a todos ellos cuando llega un nuevo mensaje.
 */
public class ChatObservable {
	
	private List<ChatObserver> observers = new CopyOnWriteArrayList<>();
	
	public void addObserver(ChatObserver o) {
		observers.add(o);
	}
	
	public void removeObserver(ChatObserver o) {
		observers.remove(o);
	}
	
	public void notificar(String mensaje) {
		for (ChatObserver o: observers) {
			o.recibirMensaje(mensaje);
		}
	}

}
