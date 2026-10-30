package co.edu.unbosque.TransMilenio.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Pago {

	private Long id;
	private Long valor;
	private String numeroRecibo;
	private LocalDate fecha;
	private Long idComparendo;

	public Pago() {
	}

	public Pago(Long valor, String numeroRecibo, LocalDate fecha, Long idComparendo) {
		super();
		this.valor = valor;
		this.numeroRecibo = numeroRecibo;
		this.fecha = fecha;
		this.idComparendo = idComparendo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getValor() {
		return valor;
	}

	public void setValor(Long valor) {
		this.valor = valor;
	}

	public String getNumeroRecibo() {
		return numeroRecibo;
	}

	public void setNumeroRecibo(String numeroRecibo) {
		this.numeroRecibo = numeroRecibo;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Long getIdComparendo() {
		return idComparendo;
	}

	public void setIdComparendo(Long idComparendo) {
		this.idComparendo = idComparendo;
	}

	@Override
	public String toString() {
		return "Pago [id=" + id + ", valor=" + valor + ", numeroRecibo=" + numeroRecibo + ", fecha=" + fecha + ", idComparendo=" + idComparendo + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(fecha, id, idComparendo, numeroRecibo, valor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pago other = (Pago) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(id, other.id) && Objects.equals(idComparendo, other.idComparendo) && Objects.equals(numeroRecibo, other.numeroRecibo) && Objects.equals(valor, other.valor);
	}

}
