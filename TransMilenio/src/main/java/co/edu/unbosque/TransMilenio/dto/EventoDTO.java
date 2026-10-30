package co.edu.unbosque.TransMilenio.dto;

import java.time.LocalDate;
import java.util.Objects;

public class EventoDTO {

	private Long id;
	private LocalDate fecha;
	private Integer numeroPersonas;
	private Long perdida;
	private Long idEstacion;
	private Long idGuarda;
	private Long idTarifa;

	public EventoDTO() {
	}

	public EventoDTO(LocalDate fecha, Integer numeroPersonas, Long perdida, Long idEstacion, Long idGuarda, Long idTarifa) {
		super();
		this.fecha = fecha;
		this.numeroPersonas = numeroPersonas;
		this.perdida = perdida;
		this.idEstacion = idEstacion;
		this.idGuarda = idGuarda;
		this.idTarifa = idTarifa;
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

	public Long getPerdida() {
		return perdida;
	}

	public void setPerdida(Long perdida) {
		this.perdida = perdida;
	}

	public Long getIdEstacion() {
		return idEstacion;
	}

	public void setIdEstacion(Long idEstacion) {
		this.idEstacion = idEstacion;
	}

	public Long getIdGuarda() {
		return idGuarda;
	}

	public void setIdGuarda(Long idGuarda) {
		this.idGuarda = idGuarda;
	}

	public Long getIdTarifa() {
		return idTarifa;
	}

	public void setIdTarifa(Long idTarifa) {
		this.idTarifa = idTarifa;
	}

	@Override
	public String toString() {
		return "EventoDTO [id=" + id + ", fecha=" + fecha + ", numeroPersonas=" + numeroPersonas + ", perdida=" + perdida + ", idEstacion=" + idEstacion + ", idGuarda=" + idGuarda + ", idTarifa=" + idTarifa + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(fecha, id, idEstacion, idGuarda, idTarifa, numeroPersonas, perdida);
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
		return Objects.equals(fecha, other.fecha) && Objects.equals(id, other.id) && Objects.equals(idEstacion, other.idEstacion) && Objects.equals(idGuarda, other.idGuarda) && Objects.equals(idTarifa, other.idTarifa) && Objects.equals(numeroPersonas, other.numeroPersonas) && Objects.equals(perdida, other.perdida);
	}

}
