package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.Estacion;

/**
 * Repositorio de la estacion. Guarda los registros en una lista en memoria (los datos se pierden al
 * reiniciar la aplicacion). Aqui es donde se conecta la base de datos: solo hay que cambiar el cuerpo de
 * sus metodos, el resto del back no cambia.
 *
 * <p>Los ids se asignan con un contador (siguienteId), empezando en 1.
 */
public class EstacionRepository {

	/**
	 * Lista en memoria donde se guardan los registros.
	 */
	private ArrayList<Estacion> lista = new ArrayList<>();
	/**
	 * Siguiente id que se asignara a un registro nuevo.
	 */
	private long siguienteId = 1;

	/**
	 * Guarda el registro. Si no tiene id, le asigna uno nuevo y lo agrega; si ya tiene id, reemplaza el
	 * registro existente.
	 *
	 * @param estacion la estacion a guardar
	 * @return el registro guardado
	 */
	public Estacion guardar(Estacion estacion) {
		if (estacion.getId() == null) {
			estacion.setId(siguienteId);
			siguienteId++;
			lista.add(estacion);
			return estacion;
		}
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(estacion.getId())) {
				lista.set(i, estacion);
				return estacion;
			}
		}
		lista.add(estacion);
		return estacion;
	}

	/**
	 * Busca una estacion por su identificador.
	 *
	 * @param id el identificador a buscar
	 * @return el registro encontrado, o null si no existe
	 */
	public Estacion buscarPorId(Long id) {
		for (Estacion estacion : lista) {
			if (estacion.getId().equals(id)) {
				return estacion;
			}
		}
		return null;
	}

	/**
	 * Obtiene todos los registros.
	 *
	 * @return una copia de la lista de registros
	 */
	public List<Estacion> listar() {
		return new ArrayList<>(lista);
	}

	/**
	 * Elimina una estacion por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return true si se elimino, false si no existia
	 */
	public boolean eliminar(Long id) {
		Estacion estacion = buscarPorId(id);
		if (estacion == null)
			return false;
		lista.remove(estacion);
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
