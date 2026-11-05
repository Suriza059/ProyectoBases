package co.edu.unbosque.TransMilenio.entity;

import java.util.Objects;

/**
 * Entidad que representa a un usuario del sistema, con su rol y sus credenciales de acceso. La contrasena
 * se guarda cifrada (sal y hash), nunca en texto plano.
 */
public class Usuario {

	/**
	 * El identificador unico del registro.
	 */
	private Long id;
	/**
	 * El rol del usuario (Administrador, Analista o Guarda).
	 */
	private String rol;
	/**
	 * El nombre de usuario con el que inicia sesion (no se puede repetir).
	 */
	private String nombreUsuario;
	/**
	 * La contrasena del usuario (se guarda cifrada).
	 */
	private String contrasena;

	/**
	 * Crea un usuario sin datos (constructor vacio).
	 */
	public Usuario() {
	}

	/**
	 * Crea un usuario con todos los datos, excepto el id.
	 *
	 * @param rol el rol del usuario (Administrador, Analista o Guarda)
	 * @param nombreUsuario el nombre de usuario con el que inicia sesion (no se puede repetir)
	 * @param contrasena la contrasena del usuario (se guarda cifrada)
	 */
	public Usuario(String rol, String nombreUsuario, String contrasena) {
		super();
		this.rol = rol;
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
	}

	/**
	 * Obtiene el identificador unico del registro.
	 *
	 * @return el identificador unico del registro
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Asigna el identificador unico del registro.
	 *
	 * @param id el identificador unico del registro
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el rol del usuario (Administrador, Analista o Guarda).
	 *
	 * @return el rol del usuario (Administrador, Analista o Guarda)
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * Asigna el rol del usuario (Administrador, Analista o Guarda).
	 *
	 * @param rol el rol del usuario (Administrador, Analista o Guarda)
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * Obtiene el nombre de usuario con el que inicia sesion (no se puede repetir).
	 *
	 * @return el nombre de usuario con el que inicia sesion (no se puede repetir)
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * Asigna el nombre de usuario con el que inicia sesion (no se puede repetir).
	 *
	 * @param nombreUsuario el nombre de usuario con el que inicia sesion (no se puede repetir)
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/*
	 * Obtiene la contrasena del usuario (se guarda cifrada).
	 *
	 * @return la contrasena del usuario (se guarda cifrada)
	 */
	public String getContrasena() {
		return contrasena;
	}

	/**
	 * Asigna la contrasena del usuario (se guarda cifrada).
	 *
	 * @param contrasena la contrasena del usuario (se guarda cifrada)
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	/**
	 * Devuelve una representacion en texto con todos los campos.
	 *
	 * @return el texto con los valores de los campos
	 */
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", rol=" + rol + ", nombreUsuario=" + nombreUsuario + ", contrasena=" + contrasena + "]";
	}

	/**
	 * Calcula el codigo hash a partir de todos los campos.
	 *
	 * @return el codigo hash
	 */
	@Override
	public int hashCode() {
		return Objects.hash(contrasena, id, nombreUsuario, rol);
	}

	/**
	 * Compara este objeto con otro. Son iguales si son de la misma clase y todos sus campos coinciden.
	 *
	 * @param obj el objeto con el que se compara
	 * @return true si son iguales, false si no
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(contrasena, other.contrasena) && Objects.equals(id, other.id) && Objects.equals(nombreUsuario, other.nombreUsuario) && Objects.equals(rol, other.rol);
	}

}
