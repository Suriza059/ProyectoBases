package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.Pago;

/**
 * Repositorio del pago. Guarda los registros en una lista en memoria (los datos se pierden al reiniciar la
 * aplicacion). Aqui es donde se conecta la base de datos: solo hay que cambiar el cuerpo de sus metodos, el
 * resto del back no cambia.
 *
 * <p>Los ids se asignan con un contador (siguienteId), empezando en 1.
 */
public class PagoRepository {

	/**
	 * Lista en memoria donde se guardan los registros.
	 */
	private ArrayList<Pago> lista = new ArrayList<>();
	/**
	 * Siguiente id que se asignara a un registro nuevo.
	 */
	private long siguienteId = 1;

	/**
	 * Guarda el registro. Si no tiene id, le asigna uno nuevo y lo agrega; si ya tiene id, reemplaza el
	 * registro existente.
	 *
	 * @param pago el pago a guardar
	 * @return el registro guardado
	 */
	public Pago guardar(Pago pago) {
		if (pago.getId() == null) {
			pago.setId(siguienteId);
			siguienteId++;
			lista.add(pago);
			return pago;
		}
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(pago.getId())) {
				lista.set(i, pago);
				return pago;
			}
		}
		lista.add(pago);
		return pago;
	}

	/**
	 * Busca un pago por su identificador.
	 *
	 * @param id el identificador a buscar
	 * @return el registro encontrado, o null si no existe
	 */
	public Pago buscarPorId(Long id) {
		for (Pago pago : lista) {
			if (pago.getId().equals(id)) {
				return pago;
			}
		}
		return null;
	}

	/**
	 * Obtiene todos los registros.
	 *
	 * @return una copia de la lista de registros
	 */
	public List<Pago> listar() {
		return new ArrayList<>(lista);
	}

	/**
	 * Elimina un pago por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return true si se elimino, false si no existia
	 */
	public boolean eliminar(Long id) {
		Pago pago = buscarPorId(id);
		if (pago == null)
			return false;
		lista.remove(pago);
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
