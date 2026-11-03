package co.edu.unbosque.TransMilenio.entity;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Entidad que representa un comparendo impuesto a un infractor por un evento.
 */
public class Comparendo {

	/**
	 * El identificador unico del registro.
	 */
	private Long id;
	/**
	 * La fecha del comparendo.
	 */
	private LocalDate fecha;
	/**
	 * El valor del comparendo.
	 */
	private Long valor;
	/**
	 * El estado del comparendo (por ejemplo, Pendiente o Pagado).
	 */
	private String estado;
	/**
	 * El identificador de la relacion evento-infractor a la que pertenece el comparendo (llave foranea).
	 */
	private Long idEventoInfractor;

	/**
	 * Crea un comparendo sin datos (constructor vacio).
	 */
	public Comparendo() {
	}

	/**
	 * Crea un comparendo con todos los datos, excepto el id.
	 *
	 * @param fecha la fecha del comparendo
	 * @param valor el valor del comparendo
	 * @param estado el estado del comparendo (por ejemplo, Pendiente o Pagado)
	 * @param idEventoInfractor el identificador de la relacion evento-infractor a la que pertenece el
	 *     comparendo (llave foranea)
	 */
	public Comparendo(LocalDate fecha, Long valor, String estado, Long idEventoInfractor) {
		super();
		this.fecha = fecha;
		this.valor = valor;
		this.estado = estado;
		this.idEventoInfractor = idEventoInfractor;
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
	 * Obtiene la fecha del comparendo.
	 *
	 * @return la fecha del comparendo
	 */
	public LocalDate getFecha() {
		return fecha;
	}

	/**
	 * Asigna la fecha del comparendo.
	 *
	 * @param fecha la fecha del comparendo
	 */
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	/**
	 * Obtiene el valor del comparendo.
	 *
	 * @return el valor del comparendo
	 */
	public Long getValor() {
		return valor;
	}

	/**
	 * Asigna el valor del comparendo.
	 *
	 * @param valor el valor del comparendo
	 */
	public void setValor(Long valor) {
		this.valor = valor;
	}

	/**
	 * Obtiene el estado del comparendo (por ejemplo, Pendiente o Pagado).
	 *
	 * @return el estado del comparendo (por ejemplo, Pendiente o Pagado)
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Asigna el estado del comparendo (por ejemplo, Pendiente o Pagado).
	 *
	 * @param estado el estado del comparendo (por ejemplo, Pendiente o Pagado)
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Obtiene el identificador de la relacion evento-infractor a la que pertenece el comparendo (llave
	 * foranea).
	 *
	 * @return el identificador de la relacion evento-infractor a la que pertenece el comparendo (llave
	 *     foranea)
	 */
	public Long getIdEventoInfractor() {
		return idEventoInfractor;
	}

	/**
	 * Asigna el identificador de la relacion evento-infractor a la que pertenece el comparendo (llave
	 * foranea).
	 *
	 * @param idEventoInfractor el identificador de la relacion evento-infractor a la que pertenece el
	 *     comparendo (llave foranea)
	 */
	public void setIdEventoInfractor(Long idEventoInfractor) {
		this.idEventoInfractor = idEventoInfractor;
	}

	/**
	 * Devuelve una representacion en texto con todos los campos.
	 *
	 * @return el texto con los valores de los campos
	 */
	@Override
	public String toString() {
		return "Comparendo [id=" + id + ", fecha=" + fecha + ", valor=" + valor + ", estado=" + estado + ", idEventoInfractor=" + idEventoInfractor + "]";
	}

	/**
	 * Calcula el codigo hash a partir de todos los campos.
	 *
	 * @return el codigo hash
	 */
	@Override
	public int hashCode() {
		return Objects.hash(estado, fecha, id, idEventoInfractor, valor);
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
		Comparendo other = (Comparendo) obj;
		return Objects.equals(estado, other.estado) && Objects.equals(fecha, other.fecha) && Objects.equals(id, other.id) && Objects.equals(idEventoInfractor, other.idEventoInfractor) && Objects.equals(valor, other.valor);
	}

}
