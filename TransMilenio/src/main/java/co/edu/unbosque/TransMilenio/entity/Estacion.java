package co.edu.unbosque.TransMilenio.entity;

import java.util.Objects;

public class Estacion {

	private Long id;
	private String nombre;
	private String activa;
	private Long idTroncal;

	public Estacion() {
	}

	public Estacion(String nombre, String activa, Long idTroncal) {
		super();
		this.nombre = nombre;
		this.activa = activa;
		this.idTroncal = idTroncal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getActiva() {
		return activa;
	}

	public void setActiva(String activa) {
		this.activa = activa;
	}

	public Long getIdTroncal() {
		return idTroncal;
	}

	public void setIdTroncal(Long idTroncal) {
		this.idTroncal = idTroncal;
	}

	@Override
	public String toString() {
		return "Estacion [id=" + id + ", nombre=" + nombre + ", activa=" + activa + ", idTroncal=" + idTroncal + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(activa, id, idTroncal, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estacion other = (Estacion) obj;
		return Objects.equals(activa, other.activa) && Objects.equals(id, other.id) && Objects.equals(idTroncal, other.idTroncal) && Objects.equals(nombre, other.nombre);
	}

}
