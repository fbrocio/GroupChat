package modelo;

/**
 * Representa a un usuario de la aplicación
 * Cada usuario tiene un id (autogenerado), un alias unico y una contraseña
 * 
 */
public class Usuario {
	private int id;
	private String alias;
	private String password;
	
	//Constructor
	public Usuario(int id, String alias, String password) {
		this.id = id;
		this.alias = alias;
		this.password = password;
		}
	
	public Usuario(String alias, String password) {
		this.alias = alias;
		this.password = password;
	}
	
	public Usuario() {};
	
	//Getters y setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
