package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.Tarifa;

/**
 * Repositorio de la tarifa. Guarda los registros en una lista en memoria (los datos se pierden al reiniciar
 * la aplicacion). Aqui es donde se conecta la base de datos: solo hay que cambiar el cuerpo de sus metodos,
 * el resto del back no cambia.
 *
 * <p>Los ids se asignan con un contador (siguienteId), empezando en 1.
 */
public class TarifaRepository {

	/**
	 * Lista en memoria donde se guardan los registros.
	 */
	private ArrayList<Tarifa> lista = new ArrayList<>();
	/**
	 * Siguiente id que se asignara a un registro nuevo.
	 */
	private long siguienteId = 1;

	/**
	 * Guarda el registro. Si no tiene id, le asigna uno nuevo y lo agrega; si ya tiene id, reemplaza el
	 * registro existente.
	 *
	 * @param tarifa la tarifa a guardar
	 * @return el registro guardado
	 */
	public Tarifa guardar(Tarifa tarifa) {
		if (tarifa.getId() == null) {
			tarifa.setId(siguienteId);
			siguienteId++;
			lista.add(tarifa);
			return tarifa;
		}
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(tarifa.getId())) {
				lista.set(i, tarifa);
				return tarifa;
			}
		}
		lista.add(tarifa);
		return tarifa;
	}

	/**
	 * Busca una tarifa por su identificador.
	 *
	 * @param id el identificador a buscar
	 * @return el registro encontrado, o null si no existe
	 */
	public Tarifa buscarPorId(Long id) {
		for (Tarifa tarifa : lista) {
			if (tarifa.getId().equals(id)) {
				return tarifa;
			}
		}
		return null;
	}

	/**
	 * Obtiene todos los registros.
	 *
	 * @return una copia de la lista de registros
	 */
	public List<Tarifa> listar() {
		return new ArrayList<>(lista);
	}

	/**
	 * Elimina una tarifa por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return true si se elimino, false si no existia
	 */
	public boolean eliminar(Long id) {
		Tarifa tarifa = buscarPorId(id);
		if (tarifa == null)
			return false;
		lista.remove(tarifa);
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
