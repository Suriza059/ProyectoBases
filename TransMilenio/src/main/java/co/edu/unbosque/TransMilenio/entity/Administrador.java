package co.edu.unbosque.TransMilenio.entity;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Entidad con los datos propios de un usuario administrador. Su llave primaria es el id del usuario
 * (relacion uno a uno).
 */
public class Administrador {

	/**
	 * El identificador del usuario (llave primaria y foranea).
	 */
	private Long idUsuario;
	/**
	 * El area que gestiona el administrador.
	 */
	private String areaGestion;
	/**
	 * La fecha de gestion del administrador.
	 */
	private LocalDate fechaGestion;

	/**
	 * Crea un administrador sin datos (constructor vacio).
	 */
	public Administrador() {
	}

	/**
	 * Crea un administrador con todos los datos, excepto el id.
	 *
	 * @param idUsuario el identificador del usuario (llave primaria y foranea)
	 * @param areaGestion el area que gestiona el administrador
	 * @param fechaGestion la fecha de gestion del administrador
	 */
	public Administrador(Long idUsuario, String areaGestion, LocalDate fechaGestion) {
		super();
		this.idUsuario = idUsuario;
		this.areaGestion = areaGestion;
		this.fechaGestion = fechaGestion;
	}

	/**
	 * Obtiene el identificador del usuario (llave primaria y foranea).
	 *
	 * @return el identificador del usuario (llave primaria y foranea)
	 */
	public Long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Asigna el identificador del usuario (llave primaria y foranea).
	 *
	 * @param idUsuario el identificador del usuario (llave primaria y foranea)
	 */
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Obtiene el area que gestiona el administrador.
	 *
	 * @return el area que gestiona el administrador
	 */
	public String getAreaGestion() {
		return areaGestion;
	}

	/**
	 * Asigna el area que gestiona el administrador.
	 *
	 * @param areaGestion el area que gestiona el administrador
	 */
	public void setAreaGestion(String areaGestion) {
		this.areaGestion = areaGestion;
	}

	/**
	 * Obtiene la fecha de gestion del administrador.
	 *
	 * @return la fecha de gestion del administrador
	 */
	public LocalDate getFechaGestion() {
		return fechaGestion;
	}

	/**
	 * Asigna la fecha de gestion del administrador.
	 *
	 * @param fechaGestion la fecha de gestion del administrador
	 */
	public void setFechaGestion(LocalDate fechaGestion) {
		this.fechaGestion = fechaGestion;
	}

	/**
	 * Devuelve una representacion en texto con todos los campos.
	 *
	 * @return el texto con los valores de los campos
	 */
	@Override
	public String toString() {
		return "Administrador [idUsuario=" + idUsuario + ", areaGestion=" + areaGestion + ", fechaGestion=" + fechaGestion + "]";
	}

	/**
	 * Calcula el codigo hash a partir de todos los campos.
	 *
	 * @return el codigo hash
	 */
	@Override
	public int hashCode() {
		return Objects.hash(areaGestion, fechaGestion, idUsuario);
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
		Administrador other = (Administrador) obj;
		return Objects.equals(areaGestion, other.areaGestion) && Objects.equals(fechaGestion, other.fechaGestion) && Objects.equals(idUsuario, other.idUsuario);
	}

}
