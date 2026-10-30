package co.edu.unbosque.TransMilenio.entity;

import java.util.Objects;

public class Modalidad {

	private Long id;
	private String nombre;

	public Modalidad() {
	}

	public Modalidad(String nombre) {
		super();
		this.nombre = nombre;
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

	@Override
	public String toString() {
		return "Modalidad [id=" + id + ", nombre=" + nombre + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Modalidad other = (Modalidad) obj;
		return Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre);
	}

}
