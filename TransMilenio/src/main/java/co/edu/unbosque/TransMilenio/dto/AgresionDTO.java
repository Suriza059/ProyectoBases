package co.edu.unbosque.TransMilenio.dto;

import java.time.LocalDate;
import java.util.Objects;

public class AgresionDTO {

	private Long id;
	private LocalDate fecha;
	private String gravedad;
	private String descripcion;
	private Long idGuarda;
	private Long idEvento;

	public AgresionDTO() {
	}

	public AgresionDTO(LocalDate fecha, String gravedad, String descripcion, Long idGuarda, Long idEvento) {
		super();
		this.fecha = fecha;
		this.gravedad = gravedad;
		this.descripcion = descripcion;
		this.idGuarda = idGuarda;
		this.idEvento = idEvento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getGravedad() {
		return gravedad;
	}

	public void setGravedad(String gravedad) {
		this.gravedad = gravedad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getIdGuarda() {
		return idGuarda;
	}

	public void setIdGuarda(Long idGuarda) {
		this.idGuarda = idGuarda;
	}

	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	@Override
	public String toString() {
		return "AgresionDTO [id=" + id + ", fecha=" + fecha + ", gravedad=" + gravedad + ", descripcion=" + descripcion + ", idGuarda=" + idGuarda + ", idEvento=" + idEvento + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(descripcion, fecha, gravedad, id, idEvento, idGuarda);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgresionDTO other = (AgresionDTO) obj;
		return Objects.equals(descripcion, other.descripcion) && Objects.equals(fecha, other.fecha) && Objects.equals(gravedad, other.gravedad) && Objects.equals(id, other.id) && Objects.equals(idEvento, other.idEvento) && Objects.equals(idGuarda, other.idGuarda);
	}

}
