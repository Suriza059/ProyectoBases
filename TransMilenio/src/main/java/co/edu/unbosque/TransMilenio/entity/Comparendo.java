package co.edu.unbosque.TransMilenio.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Comparendo {

	private Long id;
	private LocalDate fecha;
	private Long valor;
	private String estado;
	private Long idEventoInfractor;

	public Comparendo() {
	}

	public Comparendo(LocalDate fecha, Long valor, String estado, Long idEventoInfractor) {
		super();
		this.fecha = fecha;
		this.valor = valor;
		this.estado = estado;
		this.idEventoInfractor = idEventoInfractor;
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

	public Long getValor() {
		return valor;
	}

	public void setValor(Long valor) {
		this.valor = valor;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Long getIdEventoInfractor() {
		return idEventoInfractor;
	}

	public void setIdEventoInfractor(Long idEventoInfractor) {
		this.idEventoInfractor = idEventoInfractor;
	}

	@Override
	public String toString() {
		return "Comparendo [id=" + id + ", fecha=" + fecha + ", valor=" + valor + ", estado=" + estado + ", idEventoInfractor=" + idEventoInfractor + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(estado, fecha, id, idEventoInfractor, valor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comparendo other = (Comparendo) obj;
		return Objects.equals(estado, other.estado) && Objects.equals(fecha, other.fecha) && Objects.equals(id, other.id) && Objects.equals(idEventoInfractor, other.idEventoInfractor) && Objects.equals(valor, other.valor);
	}

}
