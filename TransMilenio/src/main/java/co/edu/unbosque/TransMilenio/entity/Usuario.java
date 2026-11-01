package co.edu.unbosque.TransMilenio.entity;

import java.util.Objects;

public class Usuario {

	private Long id;
	private String rol;
	private String nombreUsuario;
	private String contrasena;

	public Usuario() {
	}

	public Usuario(String rol, String nombreUsuario, String contrasena) {
		super();
		this.rol = rol;
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", rol=" + rol + ", nombreUsuario=" + nombreUsuario + ", contrasena=" + contrasena + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(contrasena, id, nombreUsuario, rol);
	}

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
