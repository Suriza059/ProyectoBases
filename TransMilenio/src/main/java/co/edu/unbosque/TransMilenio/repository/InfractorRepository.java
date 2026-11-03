package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.Infractor;

/**
 * Repositorio del infractor. Guarda los registros en una lista en memoria (los datos se pierden al
 * reiniciar la aplicacion). Aqui es donde se conecta la base de datos: solo hay que cambiar el cuerpo de
 * sus metodos, el resto del back no cambia.
 *
 * <p>Los ids se asignan con un contador (siguienteId), empezando en 1.
 */
public class InfractorRepository {

	/**
	 * Lista en memoria donde se guardan los registros.
	 */
	private ArrayList<Infractor> lista = new ArrayList<>();
	/**
	 * Siguiente id que se asignara a un registro nuevo.
	 */
	private long siguienteId = 1;

	/**
	 * Guarda el registro. Si no tiene id, le asigna uno nuevo y lo agrega; si ya tiene id, reemplaza el
	 * registro existente.
	 *
	 * @param infractor el infractor a guardar
	 * @return el registro guardado
	 */
	public Infractor guardar(Infractor infractor) {
		if (infractor.getId() == null) {
			infractor.setId(siguienteId);
			siguienteId++;
			lista.add(infractor);
			return infractor;
		}
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(infractor.getId())) {
				lista.set(i, infractor);
				return infractor;
			}
		}
		lista.add(infractor);
		return infractor;
	}

	/**
	 * Busca un infractor por su identificador.
	 *
	 * @param id el identificador a buscar
	 * @return el registro encontrado, o null si no existe
	 */
	public Infractor buscarPorId(Long id) {
		for (Infractor infractor : lista) {
			if (infractor.getId().equals(id)) {
				return infractor;
			}
		}
		return null;
	}

	/**
	 * Busca un infractor por sus datos unicos (TipoDocumentoYNumeroDocumento). Se usa para impedir
	 * registros repetidos.
	 *
	 * @param tipoDocumento el tipo de documento del infractor (por ejemplo, CC)
	 * @param numeroDocumento el numero de documento del infractor
	 * @return el registro encontrado, o null si no existe
	 */
	public Infractor buscarPorTipoDocumentoYNumeroDocumento(String tipoDocumento, String numeroDocumento) {
		for (Infractor infractor : lista) {
			if (infractor.getTipoDocumento() != null && infractor.getTipoDocumento().equals(tipoDocumento) && infractor.getNumeroDocumento() != null && infractor.getNumeroDocumento().equals(numeroDocumento)) {
				return infractor;
			}
		}
		return null;
	}

	/**
	 * Obtiene todos los registros.
	 *
	 * @return una copia de la lista de registros
	 */
	public List<Infractor> listar() {
		return new ArrayList<>(lista);
	}

	/**
	 * Elimina un infractor por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return true si se elimino, false si no existia
	 */
	public boolean eliminar(Long id) {
		Infractor infractor = buscarPorId(id);
		if (infractor == null)
			return false;
		lista.remove(infractor);
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
