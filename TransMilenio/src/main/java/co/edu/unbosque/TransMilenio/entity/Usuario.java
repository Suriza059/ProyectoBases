package co.edu.unbosque.TransMilenio.entity;

import java.util.Objects;

public class Usuario {

	private Long id;
	private String rol;
	private Long idRegistroCambios;
	private Long idComparendo;

	public Usuario() {
	}

	public Usuario(String rol, Long idRegistroCambios, Long idComparendo) {
		super();
		this.rol = rol;
		this.idRegistroCambios = idRegistroCambios;
		this.idComparendo = idComparendo;
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

	public Long getIdRegistroCambios() {
		return idRegistroCambios;
	}

	public void setIdRegistroCambios(Long idRegistroCambios) {
		this.idRegistroCambios = idRegistroCambios;
	}

	public Long getIdComparendo() {
		return idComparendo;
	}

	public void setIdComparendo(Long idComparendo) {
		this.idComparendo = idComparendo;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", rol=" + rol + ", idRegistroCambios=" + idRegistroCambios + ", idComparendo=" + idComparendo + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, idComparendo, idRegistroCambios, rol);
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
		return Objects.equals(id, other.id) && Objects.equals(idComparendo, other.idComparendo) && Objects.equals(idRegistroCambios, other.idRegistroCambios) && Objects.equals(rol, other.rol);
	}

}
