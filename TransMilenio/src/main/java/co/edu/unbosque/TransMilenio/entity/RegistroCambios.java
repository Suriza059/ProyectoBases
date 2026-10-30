package co.edu.unbosque.TransMilenio.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class RegistroCambios {

	private Long id;
	private LocalDateTime fecha;
	private String valorAnterior;
	private String valorNuevo;
	private String campoModificado;
	private Long idComparendo;

	public RegistroCambios() {
	}

	public RegistroCambios(LocalDateTime fecha, String valorAnterior, String valorNuevo, String campoModificado, Long idComparendo) {
		super();
		this.fecha = fecha;
		this.valorAnterior = valorAnterior;
		this.valorNuevo = valorNuevo;
		this.campoModificado = campoModificado;
		this.idComparendo = idComparendo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getValorAnterior() {
		return valorAnterior;
	}

	public void setValorAnterior(String valorAnterior) {
		this.valorAnterior = valorAnterior;
	}

	public String getValorNuevo() {
		return valorNuevo;
	}

	public void setValorNuevo(String valorNuevo) {
		this.valorNuevo = valorNuevo;
	}

	public String getCampoModificado() {
		return campoModificado;
	}

	public void setCampoModificado(String campoModificado) {
		this.campoModificado = campoModificado;
	}

	public Long getIdComparendo() {
		return idComparendo;
	}

	public void setIdComparendo(Long idComparendo) {
		this.idComparendo = idComparendo;
	}

	@Override
	public String toString() {
		return "RegistroCambios [id=" + id + ", fecha=" + fecha + ", valorAnterior=" + valorAnterior + ", valorNuevo=" + valorNuevo + ", campoModificado=" + campoModificado + ", idComparendo=" + idComparendo + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(campoModificado, fecha, id, idComparendo, valorAnterior, valorNuevo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistroCambios other = (RegistroCambios) obj;
		return Objects.equals(campoModificado, other.campoModificado) && Objects.equals(fecha, other.fecha) && Objects.equals(id, other.id) && Objects.equals(idComparendo, other.idComparendo) && Objects.equals(valorAnterior, other.valorAnterior) && Objects.equals(valorNuevo, other.valorNuevo);
	}

}
