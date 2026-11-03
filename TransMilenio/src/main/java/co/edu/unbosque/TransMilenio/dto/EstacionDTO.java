package co.edu.unbosque.TransMilenio.dto;

import java.util.Objects;

/**
 * Objeto de transferencia de datos (DTO) de la estacion. Lleva los datos entre el controller y el service,
 * y tiene los mismos campos que la entidad Estacion.
 */
public class EstacionDTO {

	/**
	 * El identificador unico del registro.
	 */
	private Long id;
	/**
	 * El nombre de la estacion.
	 */
	private String nombre;
	/**
	 * Indica si la estacion esta activa (S o N).
	 */
	private String activa;
	/**
	 * El identificador de la troncal a la que pertenece la estacion (llave foranea).
	 */
	private Long idTroncal;

	/**
	 * Crea una estacion sin datos (constructor vacio).
	 */
	public EstacionDTO() {
	}

	/**
	 * Crea una estacion con todos los datos, excepto el id.
	 *
	 * @param nombre el nombre de la estacion
	 * @param activa si la estacion esta activa (S o N)
	 * @param idTroncal el identificador de la troncal a la que pertenece la estacion (llave foranea)
	 */
	public EstacionDTO(String nombre, String activa, Long idTroncal) {
		super();
		this.nombre = nombre;
		this.activa = activa;
		this.idTroncal = idTroncal;
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
	 * Obtiene el nombre de la estacion.
	 *
	 * @return el nombre de la estacion
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Asigna el nombre de la estacion.
	 *
	 * @param nombre el nombre de la estacion
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Indica si la estacion esta activa (S o N).
	 *
	 * @return S si lo esta, N si no
	 */
	public String getActiva() {
		return activa;
	}

	/**
	 * Define si la estacion esta activa (S o N).
	 *
	 * @param activa S o N
	 */
	public void setActiva(String activa) {
		this.activa = activa;
	}

	/**
	 * Obtiene el identificador de la troncal a la que pertenece la estacion (llave foranea).
	 *
	 * @return el identificador de la troncal a la que pertenece la estacion (llave foranea)
	 */
	public Long getIdTroncal() {
		return idTroncal;
	}

	/**
	 * Asigna el identificador de la troncal a la que pertenece la estacion (llave foranea).
	 *
	 * @param idTroncal el identificador de la troncal a la que pertenece la estacion (llave foranea)
	 */
	public void setIdTroncal(Long idTroncal) {
		this.idTroncal = idTroncal;
	}

	/**
	 * Devuelve una representacion en texto con todos los campos.
	 *
	 * @return el texto con los valores de los campos
	 */
	@Override
	public String toString() {
		return "EstacionDTO [id=" + id + ", nombre=" + nombre + ", activa=" + activa + ", idTroncal=" + idTroncal + "]";
	}

	/**
	 * Calcula el codigo hash a partir de todos los campos.
	 *
	 * @return el codigo hash
	 */
	@Override
	public int hashCode() {
		return Objects.hash(activa, id, idTroncal, nombre);
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
		EstacionDTO other = (EstacionDTO) obj;
		return Objects.equals(activa, other.activa) && Objects.equals(id, other.id) && Objects.equals(idTroncal, other.idTroncal) && Objects.equals(nombre, other.nombre);
	}

}
