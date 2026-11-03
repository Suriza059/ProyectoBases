package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.Agresion;

/**
 * Repositorio de la agresion. Guarda los registros en una lista en memoria (los datos se pierden al
 * reiniciar la aplicacion). Aqui es donde se conecta la base de datos: solo hay que cambiar el cuerpo de
 * sus metodos, el resto del back no cambia.
 *
 * <p>Los ids se asignan con un contador (siguienteId), empezando en 1.
 */
public class AgresionRepository {

	/**
	 * Lista en memoria donde se guardan los registros.
	 */
	private ArrayList<Agresion> lista = new ArrayList<>();
	/**
	 * Siguiente id que se asignara a un registro nuevo.
	 */
	private long siguienteId = 1;

	/**
	 * Guarda el registro. Si no tiene id, le asigna uno nuevo y lo agrega; si ya tiene id, reemplaza el
	 * registro existente.
	 *
	 * @param agresion la agresion a guardar
	 * @return el registro guardado
	 */
	public Agresion guardar(Agresion agresion) {
		if (agresion.getId() == null) {
			agresion.setId(siguienteId);
			siguienteId++;
			lista.add(agresion);
			return agresion;
		}
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(agresion.getId())) {
				lista.set(i, agresion);
				return agresion;
			}
		}
		lista.add(agresion);
		return agresion;
	}

	/**
	 * Busca una agresion por su identificador.
	 *
	 * @param id el identificador a buscar
	 * @return el registro encontrado, o null si no existe
	 */
	public Agresion buscarPorId(Long id) {
		for (Agresion agresion : lista) {
			if (agresion.getId().equals(id)) {
				return agresion;
			}
		}
		return null;
	}

	/**
	 * Obtiene todos los registros.
	 *
	 * @return una copia de la lista de registros
	 */
	public List<Agresion> listar() {
		return new ArrayList<>(lista);
	}

	/**
	 * Elimina una agresion por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return true si se elimino, false si no existia
	 */
	public boolean eliminar(Long id) {
		Agresion agresion = buscarPorId(id);
		if (agresion == null)
			return false;
		lista.remove(agresion);
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
