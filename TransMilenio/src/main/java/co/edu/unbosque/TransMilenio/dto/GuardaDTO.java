package co.edu.unbosque.TransMilenio.dto;

import java.util.Objects;

public class GuardaDTO {

	private Long id;
	private String nombre;
	private String apellido;
	private String turno;
	private String cargo;
	private String activo;
	private Long idEstacion;
	private Long idGuardaSuperior;

	public GuardaDTO() {
	}

	public GuardaDTO(String nombre, String apellido, String turno, String cargo, String activo, Long idEstacion, Long idGuardaSuperior) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.turno = turno;
		this.cargo = cargo;
		this.activo = activo;
		this.idEstacion = idEstacion;
		this.idGuardaSuperior = idGuardaSuperior;
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public Long getIdEstacion() {
		return idEstacion;
	}

	public void setIdEstacion(Long idEstacion) {
		this.idEstacion = idEstacion;
	}

	public Long getIdGuardaSuperior() {
		return idGuardaSuperior;
	}

	public void setIdGuardaSuperior(Long idGuardaSuperior) {
		this.idGuardaSuperior = idGuardaSuperior;
	}

	@Override
	public String toString() {
		return "GuardaDTO [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", turno=" + turno + ", cargo=" + cargo + ", activo=" + activo + ", idEstacion=" + idEstacion + ", idGuardaSuperior=" + idGuardaSuperior + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(activo, apellido, cargo, id, idEstacion, idGuardaSuperior, nombre, turno);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GuardaDTO other = (GuardaDTO) obj;
		return Objects.equals(activo, other.activo) && Objects.equals(apellido, other.apellido) && Objects.equals(cargo, other.cargo) && Objects.equals(id, other.id) && Objects.equals(idEstacion, other.idEstacion) && Objects.equals(idGuardaSuperior, other.idGuardaSuperior) && Objects.equals(nombre, other.nombre) && Objects.equals(turno, other.turno);
	}

}
