package co.edu.unbosque.TransMilenio.dto;

import java.util.Objects;

public class AnalistaDTO {

	private Long id;
	private String areaAnalisis;
	private Long idUsuario;

	public AnalistaDTO() {
	}

	public AnalistaDTO(String areaAnalisis, Long idUsuario) {
		super();
		this.areaAnalisis = areaAnalisis;
		this.idUsuario = idUsuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAreaAnalisis() {
		return areaAnalisis;
	}

	public void setAreaAnalisis(String areaAnalisis) {
		this.areaAnalisis = areaAnalisis;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Override
	public String toString() {
		return "AnalistaDTO [id=" + id + ", areaAnalisis=" + areaAnalisis + ", idUsuario=" + idUsuario + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(areaAnalisis, id, idUsuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnalistaDTO other = (AnalistaDTO) obj;
		return Objects.equals(areaAnalisis, other.areaAnalisis) && Objects.equals(id, other.id) && Objects.equals(idUsuario, other.idUsuario);
	}

}
