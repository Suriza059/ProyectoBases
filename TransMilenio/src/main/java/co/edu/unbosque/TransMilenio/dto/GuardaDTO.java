package co.edu.unbosque.TransMilenio.dto;

import java.util.Objects;

/**
 * Objeto de transferencia de datos (DTO) del guarda. Lleva los datos entre el controller y el service, y
 * tiene los mismos campos que la entidad Guarda.
 */
public class GuardaDTO {

	/**
	 * El identificador unico del registro.
	 */
	private Long id;
	/**
	 * El turno de trabajo del guarda.
	 */
	private String turno;
	/**
	 * El nombre del guarda.
	 */
	private String nombre;
	/**
	 * El apellido del guarda.
	 */
	private String apellido;
	/**
	 * Indica si el guarda esta activo (S o N).
	 */
	private String activo;
	/**
	 * El cargo del guarda (por ejemplo, Supervisor).
	 */
	private String cargo;
	/**
	 * El identificador de la estacion donde trabaja el guarda (llave foranea).
	 */
	private Long idEstacion;
	/**
	 * El identificador del guarda que supervisa a este guarda, puede ser nulo (llave foranea).
	 */
	private Long idSupervisor;

	/**
	 * Crea un guarda sin datos (constructor vacio).
	 */
	public GuardaDTO() {
	}

	/**
	 * Crea un guarda con todos los datos, excepto el id.
	 *
	 * @param turno el turno de trabajo del guarda
	 * @param nombre el nombre del guarda
	 * @param apellido el apellido del guarda
	 * @param activo si el guarda esta activo (S o N)
	 * @param cargo el cargo del guarda (por ejemplo, Supervisor)
	 * @param idEstacion el identificador de la estacion donde trabaja el guarda (llave foranea)
	 * @param idSupervisor el identificador del guarda que supervisa a este guarda, puede ser nulo (llave
	 *     foranea)
	 */
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

	/**
	 * Obtiene el identificador unico del registro.
	 *
	 * @return el identificador unico del registro
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Asigna el identificador unico del registro.
	 *
	 * @param id el identificador unico del registro
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el turno de trabajo del guarda.
	 *
	 * @return el turno de trabajo del guarda
	 */
	public String getTurno() {
		return turno;
	}

	/**
	 * Asigna el turno de trabajo del guarda.
	 *
	 * @param turno el turno de trabajo del guarda
	 */
	public void setTurno(String turno) {
		this.turno = turno;
	}

	/**
	 * Obtiene el nombre del guarda.
	 *
	 * @return el nombre del guarda
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Asigna el nombre del guarda.
	 *
	 * @param nombre el nombre del guarda
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el apellido del guarda.
	 *
	 * @return el apellido del guarda
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * Asigna el apellido del guarda.
	 *
	 * @param apellido el apellido del guarda
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	/**
	 * Indica si el guarda esta activo (S o N).
	 *
	 * @return S si lo esta, N si no
	 */
	public String getActivo() {
		return activo;
	}

	/**
	 * Define si el guarda esta activo (S o N).
	 *
	 * @param activo S o N
	 */
	public void setActivo(String activo) {
		this.activo = activo;
	}

	/**
	 * Obtiene el cargo del guarda (por ejemplo, Supervisor).
	 *
	 * @return el cargo del guarda (por ejemplo, Supervisor)
	 */
	public String getCargo() {
		return cargo;
	}

	/**
	 * Asigna el cargo del guarda (por ejemplo, Supervisor).
	 *
	 * @param cargo el cargo del guarda (por ejemplo, Supervisor)
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	/**
	 * Obtiene el identificador de la estacion donde trabaja el guarda (llave foranea).
	 *
	 * @return el identificador de la estacion donde trabaja el guarda (llave foranea)
	 */
	public Long getIdEstacion() {
		return idEstacion;
	}

	/**
	 * Asigna el identificador de la estacion donde trabaja el guarda (llave foranea).
	 *
	 * @param idEstacion el identificador de la estacion donde trabaja el guarda (llave foranea)
	 */
	public void setIdEstacion(Long idEstacion) {
		this.idEstacion = idEstacion;
	}

	/**
	 * Obtiene el identificador del guarda que supervisa a este guarda, puede ser nulo (llave foranea).
	 *
	 * @return el identificador del guarda que supervisa a este guarda, puede ser nulo (llave foranea)
	 */
	public Long getIdSupervisor() {
		return idSupervisor;
	}

	/**
	 * Asigna el identificador del guarda que supervisa a este guarda, puede ser nulo (llave foranea).
	 *
	 * @param idSupervisor el identificador del guarda que supervisa a este guarda, puede ser nulo (llave
	 *     foranea)
	 */
	public void setIdSupervisor(Long idSupervisor) {
		this.idSupervisor = idSupervisor;
	}

	/**
	 * Devuelve una representacion en texto con todos los campos.
	 *
	 * @return el texto con los valores de los campos
	 */
	@Override
	public String toString() {
		return "GuardaDTO [id=" + id + ", turno=" + turno + ", nombre=" + nombre + ", apellido=" + apellido + ", activo=" + activo + ", cargo=" + cargo + ", idEstacion=" + idEstacion + ", idSupervisor=" + idSupervisor + "]";
	}

	/**
	 * Calcula el codigo hash a partir de todos los campos.
	 *
	 * @return el codigo hash
	 */
	@Override
	public int hashCode() {
		return Objects.hash(activo, apellido, cargo, id, idEstacion, idSupervisor, nombre, turno);
	}

	/**
	 * Compara este objeto con otro. Son iguales si son de la misma clase y todos sus campos coinciden.
	 *
	 * @param obj el objeto con el que se compara
	 * @return true si son iguales, false si no
	 */
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
