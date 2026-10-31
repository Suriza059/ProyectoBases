package co.edu.unbosque.TransMilenio.dto;

import java.time.LocalDate;
import java.util.Objects;

public class EventoDTO {

	private Long id;
	private LocalDate fecha;
	private Integer numeroPersonas;
	private Long idEstacion;
	private Long idTarifa;
	private Long idModalidad;
	private Long idGuarda;

	public EventoDTO() {
	}

	public EventoDTO(LocalDate fecha, Integer numeroPersonas, Long idEstacion, Long idTarifa, Long idModalidad, Long idGuarda) {
		super();
		this.fecha = fecha;
		this.numeroPersonas = numeroPersonas;
		this.idEstacion = idEstacion;
		this.idTarifa = idTarifa;
		this.idModalidad = idModalidad;
		this.idGuarda = idGuarda;
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

	public Integer getNumeroPersonas() {
		return numeroPersonas;
	}

	public void setNumeroPersonas(Integer numeroPersonas) {
		this.numeroPersonas = numeroPersonas;
	}

	public Long getIdEstacion() {
		return idEstacion;
	}

	public void setIdEstacion(Long idEstacion) {
		this.idEstacion = idEstacion;
	}

	public Long getIdTarifa() {
		return idTarifa;
	}

	public void setIdTarifa(Long idTarifa) {
		this.idTarifa = idTarifa;
	}

	public Long getIdModalidad() {
		return idModalidad;
	}

	public void setIdModalidad(Long idModalidad) {
		this.idModalidad = idModalidad;
	}

	public Long getIdGuarda() {
		return idGuarda;
	}

	public void setIdGuarda(Long idGuarda) {
		this.idGuarda = idGuarda;
	}

	@Override
	public String toString() {
		return "EventoDTO [id=" + id + ", fecha=" + fecha + ", numeroPersonas=" + numeroPersonas + ", idEstacion=" + idEstacion + ", idTarifa=" + idTarifa + ", idModalidad=" + idModalidad + ", idGuarda=" + idGuarda + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(fecha, id, idEstacion, idGuarda, idModalidad, idTarifa, numeroPersonas);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventoDTO other = (EventoDTO) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(id, other.id) && Objects.equals(idEstacion, other.idEstacion) && Objects.equals(idGuarda, other.idGuarda) && Objects.equals(idModalidad, other.idModalidad) && Objects.equals(idTarifa, other.idTarifa) && Objects.equals(numeroPersonas, other.numeroPersonas);
	}

}
