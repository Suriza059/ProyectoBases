package co.edu.unbosque.TransMilenio.entity;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Entidad que representa un evento ocurrido en una estacion. Guarda la tarifa vigente ese dia (dato
 * historico), la modalidad y el guarda asociados.
 */
public class Evento {

	/**
	 * El identificador unico del registro.
	 */
	private Long id;
	/**
	 * La fecha en que ocurrio el evento.
	 */
	private LocalDate fecha;
	/**
	 * La cantidad de personas involucradas en el evento.
	 */
	private Integer numeroPersonas;
	/**
	 * El identificador de la estacion donde ocurrio el evento (llave foranea).
	 */
	private Long idEstacion;
	/**
	 * El identificador de la tarifa vigente cuando ocurrio el evento, dato historico (llave foranea).
	 */
	private Long idTarifa;
	/**
	 * El identificador de la modalidad del evento (llave foranea).
	 */
	private Long idModalidad;
	/**
	 * El identificador del guarda asociado al evento (llave foranea).
	 */
	private Long idGuarda;

	/**
	 * Crea un evento sin datos (constructor vacio).
	 */
	public Evento() {
	}

	/**
	 * Crea un evento con todos los datos, excepto el id.
	 *
	 * @param fecha la fecha en que ocurrio el evento
	 * @param numeroPersonas la cantidad de personas involucradas en el evento
	 * @param idEstacion el identificador de la estacion donde ocurrio el evento (llave foranea)
	 * @param idTarifa el identificador de la tarifa vigente cuando ocurrio el evento, dato historico (llave
	 *     foranea)
	 * @param idModalidad el identificador de la modalidad del evento (llave foranea)
	 * @param idGuarda el identificador del guarda asociado al evento (llave foranea)
	 */
	public Evento(LocalDate fecha, Integer numeroPersonas, Long idEstacion, Long idTarifa, Long idModalidad, Long idGuarda) {
		super();
		this.fecha = fecha;
		this.numeroPersonas = numeroPersonas;
		this.idEstacion = idEstacion;
		this.idTarifa = idTarifa;
		this.idModalidad = idModalidad;
		this.idGuarda = idGuarda;
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
	 * Obtiene la fecha en que ocurrio el evento.
	 *
	 * @return la fecha en que ocurrio el evento
	 */
	public LocalDate getFecha() {
		return fecha;
	}

	/**
	 * Asigna la fecha en que ocurrio el evento.
	 *
	 * @param fecha la fecha en que ocurrio el evento
	 */
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	/**
	 * Obtiene la cantidad de personas involucradas en el evento.
	 *
	 * @return la cantidad de personas involucradas en el evento
	 */
	public Integer getNumeroPersonas() {
		return numeroPersonas;
	}

	/**
	 * Asigna la cantidad de personas involucradas en el evento.
	 *
	 * @param numeroPersonas la cantidad de personas involucradas en el evento
	 */
	public void setNumeroPersonas(Integer numeroPersonas) {
		this.numeroPersonas = numeroPersonas;
	}

	/**
	 * Obtiene el identificador de la estacion donde ocurrio el evento (llave foranea).
	 *
	 * @return el identificador de la estacion donde ocurrio el evento (llave foranea)
	 */
	public Long getIdEstacion() {
		return idEstacion;
	}

	/**
	 * Asigna el identificador de la estacion donde ocurrio el evento (llave foranea).
	 *
	 * @param idEstacion el identificador de la estacion donde ocurrio el evento (llave foranea)
	 */
	public void setIdEstacion(Long idEstacion) {
		this.idEstacion = idEstacion;
	}

	/**
	 * Obtiene el identificador de la tarifa vigente cuando ocurrio el evento, dato historico (llave
	 * foranea).
	 *
	 * @return el identificador de la tarifa vigente cuando ocurrio el evento, dato historico (llave
	 *     foranea)
	 */
	public Long getIdTarifa() {
		return idTarifa;
	}

	/**
	 * Asigna el identificador de la tarifa vigente cuando ocurrio el evento, dato historico (llave
	 * foranea).
	 *
	 * @param idTarifa el identificador de la tarifa vigente cuando ocurrio el evento, dato historico (llave
	 *     foranea)
	 */
	public void setIdTarifa(Long idTarifa) {
		this.idTarifa = idTarifa;
	}

	/**
	 * Obtiene el identificador de la modalidad del evento (llave foranea).
	 *
	 * @return el identificador de la modalidad del evento (llave foranea)
	 */
	public Long getIdModalidad() {
		return idModalidad;
	}

	/**
	 * Asigna el identificador de la modalidad del evento (llave foranea).
	 *
	 * @param idModalidad el identificador de la modalidad del evento (llave foranea)
	 */
	public void setIdModalidad(Long idModalidad) {
		this.idModalidad = idModalidad;
	}

	/**
	 * Obtiene el identificador del guarda asociado al evento (llave foranea).
	 *
	 * @return el identificador del guarda asociado al evento (llave foranea)
	 */
	public Long getIdGuarda() {
		return idGuarda;
	}

	/**
	 * Asigna el identificador del guarda asociado al evento (llave foranea).
	 *
	 * @param idGuarda el identificador del guarda asociado al evento (llave foranea)
	 */
	public void setIdGuarda(Long idGuarda) {
		this.idGuarda = idGuarda;
	}

	/**
	 * Devuelve una representacion en texto con todos los campos.
	 *
	 * @return el texto con los valores de los campos
	 */
	@Override
	public String toString() {
		return "Evento [id=" + id + ", fecha=" + fecha + ", numeroPersonas=" + numeroPersonas + ", idEstacion=" + idEstacion + ", idTarifa=" + idTarifa + ", idModalidad=" + idModalidad + ", idGuarda=" + idGuarda + "]";
	}

	/**
	 * Calcula el codigo hash a partir de todos los campos.
	 *
	 * @return el codigo hash
	 */
	@Override
	public int hashCode() {
		return Objects.hash(fecha, id, idEstacion, idGuarda, idModalidad, idTarifa, numeroPersonas);
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
		Evento other = (Evento) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(id, other.id) && Objects.equals(idEstacion, other.idEstacion) && Objects.equals(idGuarda, other.idGuarda) && Objects.equals(idModalidad, other.idModalidad) && Objects.equals(idTarifa, other.idTarifa) && Objects.equals(numeroPersonas, other.numeroPersonas);
	}

}
