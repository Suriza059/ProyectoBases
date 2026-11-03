package co.edu.unbosque.TransMilenio.dto;

import java.util.Objects;

/**
 * Objeto de transferencia de datos (DTO) de la relacion evento-infractor. Lleva los datos entre el
 * controller y el service, y tiene los mismos campos que la entidad EventoInfractor.
 */
public class EventoInfractorDTO {

	/**
	 * El identificador unico del registro.
	 */
	private Long id;
	/**
	 * El identificador del evento (llave foranea).
	 */
	private Long idEvento;
	/**
	 * El identificador del infractor (llave foranea).
	 */
	private Long idInfractor;

	/**
	 * Crea una relacion evento-infractor sin datos (constructor vacio).
	 */
	public EventoInfractorDTO() {
	}

	/**
	 * Crea una relacion evento-infractor con todos los datos, excepto el id.
	 *
	 * @param idEvento el identificador del evento (llave foranea)
	 * @param idInfractor el identificador del infractor (llave foranea)
	 */
	public EventoInfractorDTO(Long idEvento, Long idInfractor) {
		super();
		this.idEvento = idEvento;
		this.idInfractor = idInfractor;
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
	 * Obtiene el identificador del evento (llave foranea).
	 *
	 * @return el identificador del evento (llave foranea)
	 */
	public Long getIdEvento() {
		return idEvento;
	}

	/**
	 * Asigna el identificador del evento (llave foranea).
	 *
	 * @param idEvento el identificador del evento (llave foranea)
	 */
	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	/**
	 * Obtiene el identificador del infractor (llave foranea).
	 *
	 * @return el identificador del infractor (llave foranea)
	 */
	public Long getIdInfractor() {
		return idInfractor;
	}

	/**
	 * Asigna el identificador del infractor (llave foranea).
	 *
	 * @param idInfractor el identificador del infractor (llave foranea)
	 */
	public void setIdInfractor(Long idInfractor) {
		this.idInfractor = idInfractor;
	}

	/**
	 * Devuelve una representacion en texto con todos los campos.
	 *
	 * @return el texto con los valores de los campos
	 */
	@Override
	public String toString() {
		return "EventoInfractorDTO [id=" + id + ", idEvento=" + idEvento + ", idInfractor=" + idInfractor + "]";
	}

	/**
	 * Calcula el codigo hash a partir de todos los campos.
	 *
	 * @return el codigo hash
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id, idEvento, idInfractor);
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
		EventoInfractorDTO other = (EventoInfractorDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(idEvento, other.idEvento) && Objects.equals(idInfractor, other.idInfractor);
	}

}
