package co.edu.unbosque.TransMilenio.dto;

import java.util.Objects;

public class AnalistaDTO {

	private Long idUsuario;
	private String areaAnalisis;

	public AnalistaDTO() {
	}

	public AnalistaDTO(Long idUsuario, String areaAnalisis) {
		super();
		this.idUsuario = idUsuario;
		this.areaAnalisis = areaAnalisis;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getAreaAnalisis() {
		return areaAnalisis;
	}

	public void setAreaAnalisis(String areaAnalisis) {
		this.areaAnalisis = areaAnalisis;
	}

	@Override
	public String toString() {
		return "AnalistaDTO [idUsuario=" + idUsuario + ", areaAnalisis=" + areaAnalisis + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(areaAnalisis, idUsuario);
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
		return Objects.equals(areaAnalisis, other.areaAnalisis) && Objects.equals(idUsuario, other.idUsuario);
	}

}
