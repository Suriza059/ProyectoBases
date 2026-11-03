package co.edu.unbosque.TransMilenio.dto;

import java.util.Objects;

/**
 * Objeto de transferencia de datos (DTO) del analista. Lleva los datos entre el controller y el service, y
 * tiene los mismos campos que la entidad Analista.
 */
public class AnalistaDTO {

	/**
	 * El identificador del usuario (llave primaria y foranea).
	 */
	private Long idUsuario;
	/**
	 * El area que analiza el analista.
	 */
	private String areaAnalisis;

	/**
	 * Crea un analista sin datos (constructor vacio).
	 */
	public AnalistaDTO() {
	}

	/**
	 * Crea un analista con todos los datos, excepto el id.
	 *
	 * @param idUsuario el identificador del usuario (llave primaria y foranea)
	 * @param areaAnalisis el area que analiza el analista
	 */
	public AnalistaDTO(Long idUsuario, String areaAnalisis) {
		super();
		this.idUsuario = idUsuario;
		this.areaAnalisis = areaAnalisis;
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
	 * Obtiene el area que analiza el analista.
	 *
	 * @return el area que analiza el analista
	 */
	public String getAreaAnalisis() {
		return areaAnalisis;
	}

	/**
	 * Asigna el area que analiza el analista.
	 *
	 * @param areaAnalisis el area que analiza el analista
	 */
	public void setAreaAnalisis(String areaAnalisis) {
		this.areaAnalisis = areaAnalisis;
	}

	/**
	 * Devuelve una representacion en texto con todos los campos.
	 *
	 * @return el texto con los valores de los campos
	 */
	@Override
	public String toString() {
		return "AnalistaDTO [idUsuario=" + idUsuario + ", areaAnalisis=" + areaAnalisis + "]";
	}

	/**
	 * Calcula el codigo hash a partir de todos los campos.
	 *
	 * @return el codigo hash
	 */
	@Override
	public int hashCode() {
		return Objects.hash(areaAnalisis, idUsuario);
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
		AnalistaDTO other = (AnalistaDTO) obj;
		return Objects.equals(areaAnalisis, other.areaAnalisis) && Objects.equals(idUsuario, other.idUsuario);
	}

}
