package co.edu.unbosque.TransMilenio.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Pago {

	private Long id;
	private String numeroRecibido;
	private LocalDate fecha;
	private Long valor;

	public Pago() {
	}

	public Pago(String numeroRecibido, LocalDate fecha, Long valor) {
		super();
		this.numeroRecibido = numeroRecibido;
		this.fecha = fecha;
		this.valor = valor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroRecibido() {
		return numeroRecibido;
	}

	public void setNumeroRecibido(String numeroRecibido) {
		this.numeroRecibido = numeroRecibido;
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

	@Override
	public String toString() {
		return "Pago [id=" + id + ", numeroRecibido=" + numeroRecibido + ", fecha=" + fecha + ", valor=" + valor + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(fecha, id, numeroRecibido, valor);
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
		return Objects.equals(fecha, other.fecha) && Objects.equals(id, other.id) && Objects.equals(numeroRecibido, other.numeroRecibido) && Objects.equals(valor, other.valor);
	}

}
