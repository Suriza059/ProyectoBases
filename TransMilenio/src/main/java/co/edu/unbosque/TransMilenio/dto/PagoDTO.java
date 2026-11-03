package co.edu.unbosque.TransMilenio.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Objeto de transferencia de datos (DTO) del pago. Lleva los datos entre el controller y el service, y
 * tiene los mismos campos que la entidad Pago.
 */
public class PagoDTO {

	/**
	 * El identificador unico del registro.
	 */
	private Long id;
	/**
	 * El valor pagado.
	 */
	private Long valor;
	/**
	 * El numero del recibo de pago.
	 */
	private String numeroRecibo;
	/**
	 * La fecha del pago.
	 */
	private LocalDate fecha;
	/**
	 * El identificador del comparendo que se paga (llave foranea).
	 */
	private Long idComparendo;

	/**
	 * Crea un pago sin datos (constructor vacio).
	 */
	public PagoDTO() {
	}

	/**
	 * Crea un pago con todos los datos, excepto el id.
	 *
	 * @param valor el valor pagado
	 * @param numeroRecibo el numero del recibo de pago
	 * @param fecha la fecha del pago
	 * @param idComparendo el identificador del comparendo que se paga (llave foranea)
	 */
	public PagoDTO(Long valor, String numeroRecibo, LocalDate fecha, Long idComparendo) {
		super();
		this.valor = valor;
		this.numeroRecibo = numeroRecibo;
		this.fecha = fecha;
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
	 * Obtiene el valor pagado.
	 *
	 * @return el valor pagado
	 */
	public Long getValor() {
		return valor;
	}

	/**
	 * Asigna el valor pagado.
	 *
	 * @param valor el valor pagado
	 */
	public void setValor(Long valor) {
		this.valor = valor;
	}

	/**
	 * Obtiene el numero del recibo de pago.
	 *
	 * @return el numero del recibo de pago
	 */
	public String getNumeroRecibo() {
		return numeroRecibo;
	}

	/**
	 * Asigna el numero del recibo de pago.
	 *
	 * @param numeroRecibo el numero del recibo de pago
	 */
	public void setNumeroRecibo(String numeroRecibo) {
		this.numeroRecibo = numeroRecibo;
	}

	/**
	 * Obtiene la fecha del pago.
	 *
	 * @return la fecha del pago
	 */
	public LocalDate getFecha() {
		return fecha;
	}

	/**
	 * Asigna la fecha del pago.
	 *
	 * @param fecha la fecha del pago
	 */
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	/**
	 * Obtiene el identificador del comparendo que se paga (llave foranea).
	 *
	 * @return el identificador del comparendo que se paga (llave foranea)
	 */
	public Long getIdComparendo() {
		return idComparendo;
	}

	/**
	 * Asigna el identificador del comparendo que se paga (llave foranea).
	 *
	 * @param idComparendo el identificador del comparendo que se paga (llave foranea)
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
		return "PagoDTO [id=" + id + ", valor=" + valor + ", numeroRecibo=" + numeroRecibo + ", fecha=" + fecha + ", idComparendo=" + idComparendo + "]";
	}

	/**
	 * Calcula el codigo hash a partir de todos los campos.
	 *
	 * @return el codigo hash
	 */
	@Override
	public int hashCode() {
		return Objects.hash(fecha, id, idComparendo, numeroRecibo, valor);
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
		PagoDTO other = (PagoDTO) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(id, other.id) && Objects.equals(idComparendo, other.idComparendo) && Objects.equals(numeroRecibo, other.numeroRecibo) && Objects.equals(valor, other.valor);
	}

}
