package co.edu.unbosque.TransMilenio.entity;

import java.util.Objects;

/**
 * Entidad que representa una troncal del sistema, es decir, una via principal por la que circulan los
 * buses. Una troncal agrupa varias estaciones.
 */
public class Troncal {

	/**
	 * El identificador unico del registro.
	 */
	private Long id;
	/**
	 * El nombre de la troncal.
	 */
	private String nombre;

	/**
	 * Crea una troncal sin datos (constructor vacio).
	 */
	public Troncal() {
	}

	/**
	 * Crea una troncal con todos los datos, excepto el id.
	 *
	 * @param nombre el nombre de la troncal
	 */
	public Troncal(String nombre) {
		super();
		this.nombre = nombre;
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
	 * Obtiene el nombre de la troncal.
	 *
	 * @return el nombre de la troncal
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Asigna el nombre de la troncal.
	 *
	 * @param nombre el nombre de la troncal
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve una representacion en texto con todos los campos.
	 *
	 * @return el texto con los valores de los campos
	 */
	@Override
	public String toString() {
		return "Troncal [id=" + id + ", nombre=" + nombre + "]";
	}

	/**
	 * Calcula el codigo hash a partir de todos los campos.
	 *
	 * @return el codigo hash
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id, nombre);
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
		Troncal other = (Troncal) obj;
		return Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre);
	}

}
