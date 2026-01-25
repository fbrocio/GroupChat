package servidor;
/**
 * Servicio encargado de la gestión del chat de un grupo.
 * Controla los clientes conectados, el envío de mensajes,
 * la notificación a los observadores y la persistencia
 * de los mensajes en la base de datos.
 */
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import modelo.Mensaje;
import persistencia.MensajeDAO;
import persistencia.UsuarioDAO;

public class ServicioChat {
	
	private int grupoId;
	private List<ChatObserver> clientes;
	private MensajeDAO mensajeDAO;
	private ChatObservable observable;
	private UsuarioDAO usuarioDAO;
	
	public ServicioChat(int grupoId) {
		this.grupoId = grupoId;
		this.clientes = new CopyOnWriteArrayList<>();
		this.mensajeDAO = new MensajeDAO();
		this.usuarioDAO = new UsuarioDAO();
		this.observable = new ChatObservable();
	}
	
	public void registrarCliente(ChatObserver cliente) {
		clientes.add(cliente);
		observable.addObserver(cliente);
	}
	
	public void eliminarCliente(ChatObserver cliente) {
		clientes.remove(cliente);
		observable.removeObserver(cliente);
	}
	
	public void enviarMensaje(int usuarioId, String alias, String texto) {
		Mensaje m = new Mensaje();
		m.setGrupoId(grupoId);
		m.setContenido(texto);
		m.setUsuarioId(usuarioId);
		m.setFecha(LocalDateTime.now());
		try {
			mensajeDAO.insertar(m);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String timestamp = java.time.LocalDateTime.now().toString();
		String mensajeFormateado = alias + "|" + timestamp + "|" + texto;
		observable.notificar(mensajeFormateado);
	}
	
	public void enviarHistorial(ChatObserver cliente) {
		List<Mensaje> historial;
		try{
			historial = mensajeDAO.verHistorial(grupoId);
			for (Mensaje m : historial) {
				String texto = usuarioDAO.getUsuarioFromId(m.getUsuarioId()).getAlias() + 
						"|" + m.getFecha().toString()+ "|" + m.getContenido();
				cliente.recibirMensaje(texto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
