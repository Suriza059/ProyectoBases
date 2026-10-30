package co.edu.unbosque.TransMilenio.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Comparendo {

	private Long id;
	private LocalDate fecha;
	private Long valor;
	private String estado;
	private Long idEvento;
	private Long idInfractor;
	private Long idPago;

	public Comparendo() {
	}

	public Comparendo(LocalDate fecha, Long valor, String estado, Long idEvento, Long idInfractor, Long idPago) {
		super();
		this.fecha = fecha;
		this.valor = valor;
		this.estado = estado;
		this.idEvento = idEvento;
		this.idInfractor = idInfractor;
		this.idPago = idPago;
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

	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	public Long getIdInfractor() {
		return idInfractor;
	}

	public void setIdInfractor(Long idInfractor) {
		this.idInfractor = idInfractor;
	}

	public Long getIdPago() {
		return idPago;
	}

	public void setIdPago(Long idPago) {
		this.idPago = idPago;
	}

	@Override
	public String toString() {
		return "Comparendo [id=" + id + ", fecha=" + fecha + ", valor=" + valor + ", estado=" + estado + ", idEvento=" + idEvento + ", idInfractor=" + idInfractor + ", idPago=" + idPago + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(estado, fecha, id, idEvento, idInfractor, idPago, valor);
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
		return Objects.equals(estado, other.estado) && Objects.equals(fecha, other.fecha) && Objects.equals(id, other.id) && Objects.equals(idEvento, other.idEvento) && Objects.equals(idInfractor, other.idInfractor) && Objects.equals(idPago, other.idPago) && Objects.equals(valor, other.valor);
	}

}
