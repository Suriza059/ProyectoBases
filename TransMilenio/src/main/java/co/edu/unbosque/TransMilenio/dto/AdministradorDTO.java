package co.edu.unbosque.TransMilenio.dto;

import java.time.LocalDate;
import java.util.Objects;

public class AdministradorDTO {

	private Long idUsuario;
	private String areaGestion;
	private LocalDate fechaGestion;

	public AdministradorDTO() {
	}

	public AdministradorDTO(Long idUsuario, String areaGestion, LocalDate fechaGestion) {
		super();
		this.idUsuario = idUsuario;
		this.areaGestion = areaGestion;
		this.fechaGestion = fechaGestion;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getAreaGestion() {
		return areaGestion;
	}

	public void setAreaGestion(String areaGestion) {
		this.areaGestion = areaGestion;
	}

	public LocalDate getFechaGestion() {
		return fechaGestion;
	}

	public void setFechaGestion(LocalDate fechaGestion) {
		this.fechaGestion = fechaGestion;
	}

	@Override
	public String toString() {
		return "AdministradorDTO [idUsuario=" + idUsuario + ", areaGestion=" + areaGestion + ", fechaGestion=" + fechaGestion + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(areaGestion, fechaGestion, idUsuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdministradorDTO other = (AdministradorDTO) obj;
		return Objects.equals(areaGestion, other.areaGestion) && Objects.equals(fechaGestion, other.fechaGestion) && Objects.equals(idUsuario, other.idUsuario);
	}

}
