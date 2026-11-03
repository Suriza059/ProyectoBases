package co.edu.unbosque.TransMilenio.dto;

import java.util.Objects;

/**
 * Objeto de transferencia de datos (DTO) del infractor. Lleva los datos entre el controller y el service, y
 * tiene los mismos campos que la entidad Infractor.
 */
public class InfractorDTO {

	/**
	 * El identificador unico del registro.
	 */
	private Long id;
	/**
	 * El tipo de documento del infractor (por ejemplo, CC).
	 */
	private String tipoDocumento;
	/**
	 * El numero de documento del infractor.
	 */
	private String numeroDocumento;
	/**
	 * El nombre del infractor.
	 */
	private String nombre;
	/**
	 * El apellido del infractor.
	 */
	private String apellido;

	/**
	 * Crea un infractor sin datos (constructor vacio).
	 */
	public InfractorDTO() {
	}

	/**
	 * Crea un infractor con todos los datos, excepto el id.
	 *
	 * @param tipoDocumento el tipo de documento del infractor (por ejemplo, CC)
	 * @param numeroDocumento el numero de documento del infractor
	 * @param nombre el nombre del infractor
	 * @param apellido el apellido del infractor
	 */
	public InfractorDTO(String tipoDocumento, String numeroDocumento, String nombre, String apellido) {
		super();
		this.tipoDocumento = tipoDocumento;
		this.numeroDocumento = numeroDocumento;
		this.nombre = nombre;
		this.apellido = apellido;
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
	 * Obtiene el tipo de documento del infractor (por ejemplo, CC).
	 *
	 * @return el tipo de documento del infractor (por ejemplo, CC)
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * Asigna el tipo de documento del infractor (por ejemplo, CC).
	 *
	 * @param tipoDocumento el tipo de documento del infractor (por ejemplo, CC)
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	/**
	 * Obtiene el numero de documento del infractor.
	 *
	 * @return el numero de documento del infractor
	 */
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	/**
	 * Asigna el numero de documento del infractor.
	 *
	 * @param numeroDocumento el numero de documento del infractor
	 */
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	/**
	 * Obtiene el nombre del infractor.
	 *
	 * @return el nombre del infractor
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Asigna el nombre del infractor.
	 *
	 * @param nombre el nombre del infractor
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el apellido del infractor.
	 *
	 * @return el apellido del infractor
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * Asigna el apellido del infractor.
	 *
	 * @param apellido el apellido del infractor
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	/**
	 * Devuelve una representacion en texto con todos los campos.
	 *
	 * @return el texto con los valores de los campos
	 */
	@Override
	public String toString() {
		return "InfractorDTO [id=" + id + ", tipoDocumento=" + tipoDocumento + ", numeroDocumento=" + numeroDocumento + ", nombre=" + nombre + ", apellido=" + apellido + "]";
	}

	/**
	 * Calcula el codigo hash a partir de todos los campos.
	 *
	 * @return el codigo hash
	 */
	@Override
	public int hashCode() {
		return Objects.hash(apellido, id, nombre, numeroDocumento, tipoDocumento);
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
		InfractorDTO other = (InfractorDTO) obj;
		return Objects.equals(apellido, other.apellido) && Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre) && Objects.equals(numeroDocumento, other.numeroDocumento) && Objects.equals(tipoDocumento, other.tipoDocumento);
	}

}
