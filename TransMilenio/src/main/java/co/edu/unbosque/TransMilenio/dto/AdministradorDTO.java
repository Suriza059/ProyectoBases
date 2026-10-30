package co.edu.unbosque.TransMilenio.dto;

import java.time.LocalDate;
import java.util.Objects;

public class AdministradorDTO {

	private Long id;
	private String areaGestion;
	private LocalDate fechaGestion;
	private Long idUsuario;

	public AdministradorDTO() {
	}

	public AdministradorDTO(String areaGestion, LocalDate fechaGestion, Long idUsuario) {
		super();
		this.areaGestion = areaGestion;
		this.fechaGestion = fechaGestion;
		this.idUsuario = idUsuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Override
	public String toString() {
		return "AdministradorDTO [id=" + id + ", areaGestion=" + areaGestion + ", fechaGestion=" + fechaGestion + ", idUsuario=" + idUsuario + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(areaGestion, fechaGestion, id, idUsuario);
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
		return Objects.equals(areaGestion, other.areaGestion) && Objects.equals(fechaGestion, other.fechaGestion) && Objects.equals(id, other.id) && Objects.equals(idUsuario, other.idUsuario);
	}

}
