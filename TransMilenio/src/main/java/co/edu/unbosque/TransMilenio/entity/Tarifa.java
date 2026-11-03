package co.edu.unbosque.TransMilenio.entity;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Entidad que representa una tarifa con su periodo de vigencia y su valor.
 */
public class Tarifa {

	/**
	 * El identificador unico del registro.
	 */
	private Long id;
	/**
	 * La fecha desde la cual rige la tarifa.
	 */
	private LocalDate fechaInicio;
	/**
	 * La fecha hasta la cual rige la tarifa.
	 */
	private LocalDate fechaFin;
	/**
	 * El valor de la tarifa.
	 */
	private Long valor;

	/**
	 * Crea una tarifa sin datos (constructor vacio).
	 */
	public Tarifa() {
	}

	/**
	 * Crea una tarifa con todos los datos, excepto el id.
	 *
	 * @param fechaInicio la fecha desde la cual rige la tarifa
	 * @param fechaFin la fecha hasta la cual rige la tarifa
	 * @param valor el valor de la tarifa
	 */
	public Tarifa(LocalDate fechaInicio, LocalDate fechaFin, Long valor) {
		super();
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.valor = valor;
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
	 * Obtiene la fecha desde la cual rige la tarifa.
	 *
	 * @return la fecha desde la cual rige la tarifa
	 */
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Asigna la fecha desde la cual rige la tarifa.
	 *
	 * @param fechaInicio la fecha desde la cual rige la tarifa
	 */
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * Obtiene la fecha hasta la cual rige la tarifa.
	 *
	 * @return la fecha hasta la cual rige la tarifa
	 */
	public LocalDate getFechaFin() {
		return fechaFin;
	}

	/**
	 * Asigna la fecha hasta la cual rige la tarifa.
	 *
	 * @param fechaFin la fecha hasta la cual rige la tarifa
	 */
	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * Obtiene el valor de la tarifa.
	 *
	 * @return el valor de la tarifa
	 */
	public Long getValor() {
		return valor;
	}

	/**
	 * Asigna el valor de la tarifa.
	 *
	 * @param valor el valor de la tarifa
	 */
	public void setValor(Long valor) {
		this.valor = valor;
	}

	/**
	 * Devuelve una representacion en texto con todos los campos.
	 *
	 * @return el texto con los valores de los campos
	 */
	@Override
	public String toString() {
		return "Tarifa [id=" + id + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", valor=" + valor + "]";
	}

	/**
	 * Calcula el codigo hash a partir de todos los campos.
	 *
	 * @return el codigo hash
	 */
	@Override
	public int hashCode() {
		return Objects.hash(fechaFin, fechaInicio, id, valor);
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
		Tarifa other = (Tarifa) obj;
		return Objects.equals(fechaFin, other.fechaFin) && Objects.equals(fechaInicio, other.fechaInicio) && Objects.equals(id, other.id) && Objects.equals(valor, other.valor);
	}

}
