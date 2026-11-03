package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.Troncal;

/**
 * Repositorio de la troncal. Guarda los registros en una lista en memoria (los datos se pierden al
 * reiniciar la aplicacion). Aqui es donde se conecta la base de datos: solo hay que cambiar el cuerpo de
 * sus metodos, el resto del back no cambia.
 *
 * <p>Los ids se asignan con un contador (siguienteId), empezando en 1.
 */
public class TroncalRepository {

	/**
	 * Lista en memoria donde se guardan los registros.
	 */
	private ArrayList<Troncal> lista = new ArrayList<>();
	/**
	 * Siguiente id que se asignara a un registro nuevo.
	 */
	private long siguienteId = 1;

	/**
	 * Guarda el registro. Si no tiene id, le asigna uno nuevo y lo agrega; si ya tiene id, reemplaza el
	 * registro existente.
	 *
	 * @param troncal la troncal a guardar
	 * @return el registro guardado
	 */
	public Troncal guardar(Troncal troncal) {
		if (troncal.getId() == null) {
			troncal.setId(siguienteId);
			siguienteId++;
			lista.add(troncal);
			return troncal;
		}
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(troncal.getId())) {
				lista.set(i, troncal);
				return troncal;
			}
		}
		lista.add(troncal);
		return troncal;
	}

	/**
	 * Busca una troncal por su identificador.
	 *
	 * @param id el identificador a buscar
	 * @return el registro encontrado, o null si no existe
	 */
	public Troncal buscarPorId(Long id) {
		for (Troncal troncal : lista) {
			if (troncal.getId().equals(id)) {
				return troncal;
			}
		}
		return null;
	}

	/**
	 * Obtiene todos los registros.
	 *
	 * @return una copia de la lista de registros
	 */
	public List<Troncal> listar() {
		return new ArrayList<>(lista);
	}

	/**
	 * Elimina una troncal por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return true si se elimino, false si no existia
	 */
	public boolean eliminar(Long id) {
		Troncal troncal = buscarPorId(id);
		if (troncal == null)
			return false;
		lista.remove(troncal);
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
