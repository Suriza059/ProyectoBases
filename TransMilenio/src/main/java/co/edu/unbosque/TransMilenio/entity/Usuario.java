package co.edu.unbosque.TransMilenio.entity;

import java.util.Objects;

public class Usuario {

	private Long id;
	private String rol;

	public Usuario() {
	}

	public Usuario(String rol) {
		super();
		this.rol = rol;
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

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", rol=" + rol + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, rol);
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
		return Objects.equals(id, other.id) && Objects.equals(rol, other.rol);
	}

}
