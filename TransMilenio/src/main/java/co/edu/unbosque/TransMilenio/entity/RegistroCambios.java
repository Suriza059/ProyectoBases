package co.edu.unbosque.TransMilenio.entity;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidad de auditoria: guarda el cambio que un usuario hizo sobre un campo de un comparendo (valor
 * anterior y valor nuevo).
 */
public class RegistroCambios {

	/**
	 * El identificador unico del registro.
	 */
	private Long id;
	/**
	 * La fecha y hora en que se hizo el cambio.
	 */
	private LocalDateTime fecha;
	/**
	 * El nombre del campo que se modifico.
	 */
	private String campoModificado;
	/**
	 * El valor que tenia el campo antes del cambio.
	 */
	private String valorAnterior;
	/**
	 * El valor que tiene el campo despues del cambio.
	 */
	private String valorNuevo;
	/**
	 * El identificador del usuario que hizo el cambio (llave foranea).
	 */
	private Long idUsuario;
	/**
	 * El identificador del comparendo modificado (llave foranea).
	 */
	private Long idComparendo;

	/**
	 * Crea un registro de cambios sin datos (constructor vacio).
	 */
	public RegistroCambios() {
	}

	/**
	 * Crea un registro de cambios con todos los datos, excepto el id.
	 *
	 * @param fecha la fecha y hora en que se hizo el cambio
	 * @param campoModificado el nombre del campo que se modifico
	 * @param valorAnterior el valor que tenia el campo antes del cambio
	 * @param valorNuevo el valor que tiene el campo despues del cambio
	 * @param idUsuario el identificador del usuario que hizo el cambio (llave foranea)
	 * @param idComparendo el identificador del comparendo modificado (llave foranea)
	 */
	public RegistroCambios(LocalDateTime fecha, String campoModificado, String valorAnterior, String valorNuevo, Long idUsuario, Long idComparendo) {
		super();
		this.fecha = fecha;
		this.campoModificado = campoModificado;
		this.valorAnterior = valorAnterior;
		this.valorNuevo = valorNuevo;
		this.idUsuario = idUsuario;
		this.idComparendo = idComparendo;
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
	 * Obtiene la fecha y hora en que se hizo el cambio.
	 *
	 * @return la fecha y hora en que se hizo el cambio
	 */
	public LocalDateTime getFecha() {
		return fecha;
	}

	/**
	 * Asigna la fecha y hora en que se hizo el cambio.
	 *
	 * @param fecha la fecha y hora en que se hizo el cambio
	 */
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	/**
	 * Obtiene el nombre del campo que se modifico.
	 *
	 * @return el nombre del campo que se modifico
	 */
	public String getCampoModificado() {
		return campoModificado;
	}

	/**
	 * Asigna el nombre del campo que se modifico.
	 *
	 * @param campoModificado el nombre del campo que se modifico
	 */
	public void setCampoModificado(String campoModificado) {
		this.campoModificado = campoModificado;
	}

	/**
	 * Obtiene el valor que tenia el campo antes del cambio.
	 *
	 * @return el valor que tenia el campo antes del cambio
	 */
	public String getValorAnterior() {
		return valorAnterior;
	}

	/**
	 * Asigna el valor que tenia el campo antes del cambio.
	 *
	 * @param valorAnterior el valor que tenia el campo antes del cambio
	 */
	public void setValorAnterior(String valorAnterior) {
		this.valorAnterior = valorAnterior;
	}

	/**
	 * Obtiene el valor que tiene el campo despues del cambio.
	 *
	 * @return el valor que tiene el campo despues del cambio
	 */
	public String getValorNuevo() {
		return valorNuevo;
	}

	/**
	 * Asigna el valor que tiene el campo despues del cambio.
	 *
	 * @param valorNuevo el valor que tiene el campo despues del cambio
	 */
	public void setValorNuevo(String valorNuevo) {
		this.valorNuevo = valorNuevo;
	}

	/**
	 * Obtiene el identificador del usuario que hizo el cambio (llave foranea).
	 *
	 * @return el identificador del usuario que hizo el cambio (llave foranea)
	 */
	public Long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Asigna el identificador del usuario que hizo el cambio (llave foranea).
	 *
	 * @param idUsuario el identificador del usuario que hizo el cambio (llave foranea)
	 */
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Obtiene el identificador del comparendo modificado (llave foranea).
	 *
	 * @return el identificador del comparendo modificado (llave foranea)
	 */
	public Long getIdComparendo() {
		return idComparendo;
	}

	/**
	 * Asigna el identificador del comparendo modificado (llave foranea).
	 *
	 * @param idComparendo el identificador del comparendo modificado (llave foranea)
	 */
	public void setIdComparendo(Long idComparendo) {
		this.idComparendo = idComparendo;
	}

	/**
	 * Devuelve una representacion en texto con todos los campos.
	 *
	 * @return el texto con los valores de los campos
	 */
	@Override
	public String toString() {
		return "RegistroCambios [id=" + id + ", fecha=" + fecha + ", campoModificado=" + campoModificado + ", valorAnterior=" + valorAnterior + ", valorNuevo=" + valorNuevo + ", idUsuario=" + idUsuario + ", idComparendo=" + idComparendo + "]";
	}

	/**
	 * Calcula el codigo hash a partir de todos los campos.
	 *
	 * @return el codigo hash
	 */
	@Override
	public int hashCode() {
		return Objects.hash(campoModificado, fecha, id, idComparendo, idUsuario, valorAnterior, valorNuevo);
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
		RegistroCambios other = (RegistroCambios) obj;
		return Objects.equals(campoModificado, other.campoModificado) && Objects.equals(fecha, other.fecha) && Objects.equals(id, other.id) && Objects.equals(idComparendo, other.idComparendo) && Objects.equals(idUsuario, other.idUsuario) && Objects.equals(valorAnterior, other.valorAnterior) && Objects.equals(valorNuevo, other.valorNuevo);
	}

}
