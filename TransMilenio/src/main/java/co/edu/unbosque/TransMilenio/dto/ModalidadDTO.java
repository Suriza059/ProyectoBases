package co.edu.unbosque.TransMilenio.dto;

import java.util.Objects;

public class ModalidadDTO {

	private Long id;
	private String nombre;
	private Long idEvento;

	public ModalidadDTO() {
	}

	public ModalidadDTO(String nombre, Long idEvento) {
		super();
		this.nombre = nombre;
		this.idEvento = idEvento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	@Override
	public String toString() {
		return "ModalidadDTO [id=" + id + ", nombre=" + nombre + ", idEvento=" + idEvento + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, idEvento, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModalidadDTO other = (ModalidadDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(idEvento, other.idEvento) && Objects.equals(nombre, other.nombre);
	}

}
