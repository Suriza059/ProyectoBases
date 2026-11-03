package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.RegistroCambios;

/**
 * Repositorio del registro de cambios. Guarda los registros en una lista en memoria (los datos se pierden
 * al reiniciar la aplicacion). Aqui es donde se conecta la base de datos: solo hay que cambiar el cuerpo de
 * sus metodos, el resto del back no cambia.
 *
 * <p>Los ids se asignan con un contador (siguienteId), empezando en 1.
 */
public class RegistroCambiosRepository {

	/**
	 * Lista en memoria donde se guardan los registros.
	 */
	private ArrayList<RegistroCambios> lista = new ArrayList<>();
	/**
	 * Siguiente id que se asignara a un registro nuevo.
	 */
	private long siguienteId = 1;

	/**
	 * Guarda el registro. Si no tiene id, le asigna uno nuevo y lo agrega; si ya tiene id, reemplaza el
	 * registro existente.
	 *
	 * @param registroCambios el registro de cambios a guardar
	 * @return el registro guardado
	 */
	public RegistroCambios guardar(RegistroCambios registroCambios) {
		if (registroCambios.getId() == null) {
			registroCambios.setId(siguienteId);
			siguienteId++;
			lista.add(registroCambios);
			return registroCambios;
		}
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(registroCambios.getId())) {
				lista.set(i, registroCambios);
				return registroCambios;
			}
		}
		lista.add(registroCambios);
		return registroCambios;
	}

	/**
	 * Busca un registro de cambios por su identificador.
	 *
	 * @param id el identificador a buscar
	 * @return el registro encontrado, o null si no existe
	 */
	public RegistroCambios buscarPorId(Long id) {
		for (RegistroCambios registroCambios : lista) {
			if (registroCambios.getId().equals(id)) {
				return registroCambios;
			}
		}
		return null;
	}

	/**
	 * Obtiene todos los registros.
	 *
	 * @return una copia de la lista de registros
	 */
	public List<RegistroCambios> listar() {
		return new ArrayList<>(lista);
	}

	/**
	 * Elimina un registro de cambios por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return true si se elimino, false si no existia
	 */
	public boolean eliminar(Long id) {
		RegistroCambios registroCambios = buscarPorId(id);
		if (registroCambios == null)
			return false;
		lista.remove(registroCambios);
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
