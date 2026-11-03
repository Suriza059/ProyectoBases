package co.edu.unbosque.TransMilenio.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Objeto de transferencia de datos (DTO) de la activacion de guarda. Lleva los datos entre el controller y
 * el service, y tiene los mismos campos que la entidad UsuarioGuarda.
 */
public class UsuarioGuardaDTO {

	/**
	 * El identificador del usuario (llave primaria y foranea).
	 */
	private Long idUsuario;
	/**
	 * La fecha en que se activo el guarda para el usuario.
	 */
	private LocalDate fechaActivacion;
	/**
	 * El identificador del guarda activado (llave foranea).
	 */
	private Long idGuarda;

	/**
	 * Crea una activacion de guarda sin datos (constructor vacio).
	 */
	public UsuarioGuardaDTO() {
	}

	/**
	 * Crea una activacion de guarda con todos los datos, excepto el id.
	 *
	 * @param idUsuario el identificador del usuario (llave primaria y foranea)
	 * @param fechaActivacion la fecha en que se activo el guarda para el usuario
	 * @param idGuarda el identificador del guarda activado (llave foranea)
	 */
	public UsuarioGuardaDTO(Long idUsuario, LocalDate fechaActivacion, Long idGuarda) {
		super();
		this.idUsuario = idUsuario;
		this.fechaActivacion = fechaActivacion;
		this.idGuarda = idGuarda;
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
	 * Obtiene la fecha en que se activo el guarda para el usuario.
	 *
	 * @return la fecha en que se activo el guarda para el usuario
	 */
	public LocalDate getFechaActivacion() {
		return fechaActivacion;
	}

	/**
	 * Asigna la fecha en que se activo el guarda para el usuario.
	 *
	 * @param fechaActivacion la fecha en que se activo el guarda para el usuario
	 */
	public void setFechaActivacion(LocalDate fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}

	/**
	 * Obtiene el identificador del guarda activado (llave foranea).
	 *
	 * @return el identificador del guarda activado (llave foranea)
	 */
	public Long getIdGuarda() {
		return idGuarda;
	}

	/**
	 * Asigna el identificador del guarda activado (llave foranea).
	 *
	 * @param idGuarda el identificador del guarda activado (llave foranea)
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
		return "UsuarioGuardaDTO [idUsuario=" + idUsuario + ", fechaActivacion=" + fechaActivacion + ", idGuarda=" + idGuarda + "]";
	}

	/**
	 * Calcula el codigo hash a partir de todos los campos.
	 *
	 * @return el codigo hash
	 */
	@Override
	public int hashCode() {
		return Objects.hash(fechaActivacion, idGuarda, idUsuario);
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
		UsuarioGuardaDTO other = (UsuarioGuardaDTO) obj;
		return Objects.equals(fechaActivacion, other.fechaActivacion) && Objects.equals(idGuarda, other.idGuarda) && Objects.equals(idUsuario, other.idUsuario);
	}

}
