package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.Modalidad;

/**
 * Repositorio de la modalidad. Guarda los registros en una lista en memoria (los datos se pierden al
 * reiniciar la aplicacion). Aqui es donde se conecta la base de datos: solo hay que cambiar el cuerpo de
 * sus metodos, el resto del back no cambia.
 *
 * <p>Los ids se asignan con un contador (siguienteId), empezando en 1.
 */
public class ModalidadRepository {

	/**
	 * Lista en memoria donde se guardan los registros.
	 */
	private ArrayList<Modalidad> lista = new ArrayList<>();
	/**
	 * Siguiente id que se asignara a un registro nuevo.
	 */
	private long siguienteId = 1;

	/**
	 * Guarda el registro. Si no tiene id, le asigna uno nuevo y lo agrega; si ya tiene id, reemplaza el
	 * registro existente.
	 *
	 * @param modalidad la modalidad a guardar
	 * @return el registro guardado
	 */
	public Modalidad guardar(Modalidad modalidad) {
		if (modalidad.getId() == null) {
			modalidad.setId(siguienteId);
			siguienteId++;
			lista.add(modalidad);
			return modalidad;
		}
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(modalidad.getId())) {
				lista.set(i, modalidad);
				return modalidad;
			}
		}
		lista.add(modalidad);
		return modalidad;
	}

	/**
	 * Busca una modalidad por su identificador.
	 *
	 * @param id el identificador a buscar
	 * @return el registro encontrado, o null si no existe
	 */
	public Modalidad buscarPorId(Long id) {
		for (Modalidad modalidad : lista) {
			if (modalidad.getId().equals(id)) {
				return modalidad;
			}
		}
		return null;
	}

	/**
	 * Obtiene todos los registros.
	 *
	 * @return una copia de la lista de registros
	 */
	public List<Modalidad> listar() {
		return new ArrayList<>(lista);
	}

	/**
	 * Elimina una modalidad por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return true si se elimino, false si no existia
	 */
	public boolean eliminar(Long id) {
		Modalidad modalidad = buscarPorId(id);
		if (modalidad == null)
			return false;
		lista.remove(modalidad);
		return true;
	}

	/**
	 * Indica si existe un registro con ese identificador.
	 *
	 * @param id el identificador a comprobar
	 * @return true si existe, false si no
	 */
	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	/**
	 * Cuenta los registros guardados.
	 *
	 * @return la cantidad de registros
	 */
	public long contar() {
		return lista.size();
	}
}
