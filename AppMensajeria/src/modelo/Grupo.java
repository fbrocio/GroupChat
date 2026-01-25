package modelo;

/**
 * Representa un grupo de chat con su configuración básica.
 * Cada grupo tiene un servidor independiente en un puerto TCP específico.
 */
public class Grupo {
	private int id;
	private String nombre;
	private String clave;
	private int puerto;
	
	//Métodos constructores
	public Grupo(int id, String nombre, String clave, int puerto) {
		this.id = id;
		this.nombre = nombre;
		this.clave = clave;
		this.puerto = puerto;
	}
	
	public Grupo (String clave) {
		this.clave = clave;
	}
	
	public Grupo() {
	}

	// Getters y setters
	public int getId(){
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getClave() {
		return clave;
	}
	
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	public int getPuerto() {
		return puerto;
	}
	
	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}
}
