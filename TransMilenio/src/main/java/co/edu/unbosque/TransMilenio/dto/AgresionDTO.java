package co.edu.unbosque.TransMilenio.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Objeto de transferencia de datos (DTO) de la agresion. Lleva los datos entre el controller y el service,
 * y tiene los mismos campos que la entidad Agresion.
 */
public class AgresionDTO {

	/**
	 * El identificador unico del registro.
	 */
	private Long id;
	/**
	 * La fecha de la agresion.
	 */
	private LocalDate fecha;
	/**
	 * La gravedad de la agresion.
	 */
	private String gravedad;
	/**
	 * La descripcion de la agresion.
	 */
	private String descripcion;
	/**
	 * El identificador del evento en el que ocurrio la agresion (llave foranea).
	 */
	private Long idEvento;
	/**
	 * El identificador del guarda involucrado en la agresion (llave foranea).
	 */
	private Long idGuarda;

	/**
	 * Crea una agresion sin datos (constructor vacio).
	 */
	public AgresionDTO() {
	}

	/**
	 * Crea una agresion con todos los datos, excepto el id.
	 *
	 * @param fecha la fecha de la agresion
	 * @param gravedad la gravedad de la agresion
	 * @param descripcion la descripcion de la agresion
	 * @param idEvento el identificador del evento en el que ocurrio la agresion (llave foranea)
	 * @param idGuarda el identificador del guarda involucrado en la agresion (llave foranea)
	 */
	public AgresionDTO(LocalDate fecha, String gravedad, String descripcion, Long idEvento, Long idGuarda) {
		super();
		this.fecha = fecha;
		this.gravedad = gravedad;
		this.descripcion = descripcion;
		this.idEvento = idEvento;
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
	 * Obtiene la fecha de la agresion.
	 *
	 * @return la fecha de la agresion
	 */
	public LocalDate getFecha() {
		return fecha;
	}

	/**
	 * Asigna la fecha de la agresion.
	 *
	 * @param fecha la fecha de la agresion
	 */
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	/**
	 * Obtiene la gravedad de la agresion.
	 *
	 * @return la gravedad de la agresion
	 */
	public String getGravedad() {
		return gravedad;
	}

	/**
	 * Asigna la gravedad de la agresion.
	 *
	 * @param gravedad la gravedad de la agresion
	 */
	public void setGravedad(String gravedad) {
		this.gravedad = gravedad;
	}

	/**
	 * Obtiene la descripcion de la agresion.
	 *
	 * @return la descripcion de la agresion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Asigna la descripcion de la agresion.
	 *
	 * @param descripcion la descripcion de la agresion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el identificador del evento en el que ocurrio la agresion (llave foranea).
	 *
	 * @return el identificador del evento en el que ocurrio la agresion (llave foranea)
	 */
	public Long getIdEvento() {
		return idEvento;
	}

	/**
	 * Asigna el identificador del evento en el que ocurrio la agresion (llave foranea).
	 *
	 * @param idEvento el identificador del evento en el que ocurrio la agresion (llave foranea)
	 */
	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	/**
	 * Obtiene el identificador del guarda involucrado en la agresion (llave foranea).
	 *
	 * @return el identificador del guarda involucrado en la agresion (llave foranea)
	 */
	public Long getIdGuarda() {
		return idGuarda;
	}

	/**
	 * Asigna el identificador del guarda involucrado en la agresion (llave foranea).
	 *
	 * @param idGuarda el identificador del guarda involucrado en la agresion (llave foranea)
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
		return "AgresionDTO [id=" + id + ", fecha=" + fecha + ", gravedad=" + gravedad + ", descripcion=" + descripcion + ", idEvento=" + idEvento + ", idGuarda=" + idGuarda + "]";
	}

	/**
	 * Calcula el codigo hash a partir de todos los campos.
	 *
	 * @return el codigo hash
	 */
	@Override
	public int hashCode() {
		return Objects.hash(descripcion, fecha, gravedad, id, idEvento, idGuarda);
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
		AgresionDTO other = (AgresionDTO) obj;
		return Objects.equals(descripcion, other.descripcion) && Objects.equals(fecha, other.fecha) && Objects.equals(gravedad, other.gravedad) && Objects.equals(id, other.id) && Objects.equals(idEvento, other.idEvento) && Objects.equals(idGuarda, other.idGuarda);
	}

}
