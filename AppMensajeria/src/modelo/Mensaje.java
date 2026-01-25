package modelo;

import java.time.LocalDateTime;

/**
 * Representa un mensaje de chat
 * Contiene la información básica del mensaje, como el contenido, la fecha de envío,
 * y los identificadores de usuario y de grupo.
 */
public class Mensaje {
	private int id;
	private String contenido;
	private LocalDateTime fecha;
	private int grupoId;
	private int usuarioId;
	
	//Constructor
	public Mensaje(int id, String contenido, LocalDateTime fecha, int grupoId, int usuarioId) {
		this.id = id;
		this.contenido = contenido;
		this.fecha = fecha;
		this.grupoId = grupoId;
		this.usuarioId = usuarioId;
	}
	
	public Mensaje() {};
	
	public Mensaje(String contenido, LocalDateTime fecha, int usuarioId) {
		this.contenido = contenido;
		this.fecha = fecha;
		this.usuarioId = usuarioId;
	}

	public Mensaje(String contenido, LocalDateTime fecha) {
		this.contenido = contenido;
		this.fecha = fecha;
	}

	//Getters y setters
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getContenido() {
		return contenido;
	}
	
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
	public LocalDateTime getFecha() {
		return fecha;
	}
	
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	
	public int getGrupoId() {
		return grupoId;
	}
	
	public void setGrupoId(int grupoId) {
		this.grupoId = grupoId;
	}
	
	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}
}
