package co.edu.unbosque.TransMilenio.dto;

import java.util.Objects;

public class UsuarioDTO {

	private Long id;
	private String rol;

	public UsuarioDTO() {
	}

	public UsuarioDTO(String rol) {
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
		return "UsuarioDTO [id=" + id + ", rol=" + rol + "]";
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
		UsuarioDTO other = (UsuarioDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(rol, other.rol);
	}

}
