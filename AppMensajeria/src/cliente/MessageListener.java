package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Clase que escucha continuamente los mensajes entrantes desde un socket 
 * y notifica a todos los observadores registrados cuando llega un nuevo mensaje.
 * 
 * Esta clase está diseñada para ejecutarse en un hilo separado ({@link Runnable}).
 * 
 */
public class MessageListener implements Runnable {

    private Socket socket;
    private BufferedReader in;
    private List<MessageObserver> observers;

    public MessageListener(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.observers = new ArrayList<>();
    }

    public void addObserver(MessageObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers(String msg) {
        for (MessageObserver obs : observers) {
            obs.recibirMensaje(msg);
        }
    }
    
    /**
     * Método principal del hilo. Lee continuamente líneas de texto desde el socket
     * y notifica a los observadores cada vez que llega un mensaje completo.
     */
    @Override
    public void run() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                notifyObservers(line); // Notificar a la GUI
            }
        } catch (IOException e) {
            System.err.println("Error al leer del socket: " + e.getMessage());
        } finally {
            try {
                in.close();
            } catch (IOException ignored) {}
        }
    }
}
