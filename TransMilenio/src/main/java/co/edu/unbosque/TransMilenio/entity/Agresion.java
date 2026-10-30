package co.edu.unbosque.TransMilenio.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Agresion {

	private Long id;
	private LocalDate fecha;
	private String gravedad;
	private String descripcion;
	private Long idEvento;
	private Long idGuarda;

	public Agresion() {
	}

	public Agresion(LocalDate fecha, String gravedad, String descripcion, Long idEvento, Long idGuarda) {
		super();
		this.fecha = fecha;
		this.gravedad = gravedad;
		this.descripcion = descripcion;
		this.idEvento = idEvento;
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

	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	public Long getIdGuarda() {
		return idGuarda;
	}

	public void setIdGuarda(Long idGuarda) {
		this.idGuarda = idGuarda;
	}

	@Override
	public String toString() {
		return "Agresion [id=" + id + ", fecha=" + fecha + ", gravedad=" + gravedad + ", descripcion=" + descripcion + ", idEvento=" + idEvento + ", idGuarda=" + idGuarda + "]";
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
		Agresion other = (Agresion) obj;
		return Objects.equals(descripcion, other.descripcion) && Objects.equals(fecha, other.fecha) && Objects.equals(gravedad, other.gravedad) && Objects.equals(id, other.id) && Objects.equals(idEvento, other.idEvento) && Objects.equals(idGuarda, other.idGuarda);
	}

}
