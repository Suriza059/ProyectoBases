package co.edu.unbosque.TransMilenio.entity;

import java.util.Objects;

public class Infractor {

	private Long id;
	private String tipoDocumento;
	private String numeroDocumento;
	private String nombre;
	private String apellido;

	public Infractor() {
	}

	public Infractor(String tipoDocumento, String numeroDocumento, String nombre, String apellido) {
		super();
		this.tipoDocumento = tipoDocumento;
		this.numeroDocumento = numeroDocumento;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@Override
	public String toString() {
		return "Infractor [id=" + id + ", tipoDocumento=" + tipoDocumento + ", numeroDocumento=" + numeroDocumento + ", nombre=" + nombre + ", apellido=" + apellido + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(apellido, id, nombre, numeroDocumento, tipoDocumento);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Infractor other = (Infractor) obj;
		return Objects.equals(apellido, other.apellido) && Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre) && Objects.equals(numeroDocumento, other.numeroDocumento) && Objects.equals(tipoDocumento, other.tipoDocumento);
	}

}
