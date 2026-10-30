package co.edu.unbosque.TransMilenio.dto;

import java.util.Objects;

public class GuardaDTO {

	private Long id;
	private String turno;
	private String nombre;
	private String apellido;
	private String activo;
	private String cargo;
	private Long idEstacion;
	private Long idSupervisor;

	public GuardaDTO() {
	}

	public GuardaDTO(String turno, String nombre, String apellido, String activo, String cargo, Long idEstacion, Long idSupervisor) {
		super();
		this.turno = turno;
		this.nombre = nombre;
		this.apellido = apellido;
		this.activo = activo;
		this.cargo = cargo;
		this.idEstacion = idEstacion;
		this.idSupervisor = idSupervisor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
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

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Long getIdEstacion() {
		return idEstacion;
	}

	public void setIdEstacion(Long idEstacion) {
		this.idEstacion = idEstacion;
	}

	public Long getIdSupervisor() {
		return idSupervisor;
	}

	public void setIdSupervisor(Long idSupervisor) {
		this.idSupervisor = idSupervisor;
	}

	@Override
	public String toString() {
		return "GuardaDTO [id=" + id + ", turno=" + turno + ", nombre=" + nombre + ", apellido=" + apellido + ", activo=" + activo + ", cargo=" + cargo + ", idEstacion=" + idEstacion + ", idSupervisor=" + idSupervisor + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(activo, apellido, cargo, id, idEstacion, idSupervisor, nombre, turno);
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
		return Objects.equals(activo, other.activo) && Objects.equals(apellido, other.apellido) && Objects.equals(cargo, other.cargo) && Objects.equals(id, other.id) && Objects.equals(idEstacion, other.idEstacion) && Objects.equals(idSupervisor, other.idSupervisor) && Objects.equals(nombre, other.nombre) && Objects.equals(turno, other.turno);
	}

}
