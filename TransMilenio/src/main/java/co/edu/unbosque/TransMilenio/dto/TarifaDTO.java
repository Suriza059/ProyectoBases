package co.edu.unbosque.TransMilenio.dto;

import java.time.LocalDate;
import java.util.Objects;

public class TarifaDTO {

	private Long id;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private Long valor;

	public TarifaDTO() {
	}

	public TarifaDTO(LocalDate fechaInicio, LocalDate fechaFin, Long valor) {
		super();
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.valor = valor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Long getValor() {
		return valor;
	}

	public void setValor(Long valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "TarifaDTO [id=" + id + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", valor=" + valor + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(fechaFin, fechaInicio, id, valor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TarifaDTO other = (TarifaDTO) obj;
		return Objects.equals(fechaFin, other.fechaFin) && Objects.equals(fechaInicio, other.fechaInicio) && Objects.equals(id, other.id) && Objects.equals(valor, other.valor);
	}

}
