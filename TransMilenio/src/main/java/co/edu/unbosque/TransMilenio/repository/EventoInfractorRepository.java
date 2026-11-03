package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.EventoInfractor;

/**
 * Repositorio de la relacion evento-infractor. Guarda los registros en una lista en memoria (los datos se
 * pierden al reiniciar la aplicacion). Aqui es donde se conecta la base de datos: solo hay que cambiar el
 * cuerpo de sus metodos, el resto del back no cambia.
 *
 * <p>Los ids se asignan con un contador (siguienteId), empezando en 1.
 */
public class EventoInfractorRepository {

	/**
	 * Lista en memoria donde se guardan los registros.
	 */
	private ArrayList<EventoInfractor> lista = new ArrayList<>();
	/**
	 * Siguiente id que se asignara a un registro nuevo.
	 */
	private long siguienteId = 1;

	/**
	 * Guarda el registro. Si no tiene id, le asigna uno nuevo y lo agrega; si ya tiene id, reemplaza el
	 * registro existente.
	 *
	 * @param eventoInfractor la relacion evento-infractor a guardar
	 * @return el registro guardado
	 */
	public EventoInfractor guardar(EventoInfractor eventoInfractor) {
		if (eventoInfractor.getId() == null) {
			eventoInfractor.setId(siguienteId);
			siguienteId++;
			lista.add(eventoInfractor);
			return eventoInfractor;
		}
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(eventoInfractor.getId())) {
				lista.set(i, eventoInfractor);
				return eventoInfractor;
			}
		}
		lista.add(eventoInfractor);
		return eventoInfractor;
	}

	/**
	 * Busca una relacion evento-infractor por su identificador.
	 *
	 * @param id el identificador a buscar
	 * @return el registro encontrado, o null si no existe
	 */
	public EventoInfractor buscarPorId(Long id) {
		for (EventoInfractor eventoInfractor : lista) {
			if (eventoInfractor.getId().equals(id)) {
				return eventoInfractor;
			}
		}
		return null;
	}

	/**
	 * Busca una relacion evento-infractor por sus datos unicos (IdEventoYIdInfractor). Se usa para impedir
	 * registros repetidos.
	 *
	 * @param idEvento el identificador del evento (llave foranea)
	 * @param idInfractor el identificador del infractor (llave foranea)
	 * @return el registro encontrado, o null si no existe
	 */
	public EventoInfractor buscarPorIdEventoYIdInfractor(Long idEvento, Long idInfractor) {
		for (EventoInfractor eventoInfractor : lista) {
			if (eventoInfractor.getIdEvento() != null && eventoInfractor.getIdEvento().equals(idEvento) && eventoInfractor.getIdInfractor() != null && eventoInfractor.getIdInfractor().equals(idInfractor)) {
				return eventoInfractor;
			}
		}
		return null;
	}

	/**
	 * Obtiene todos los registros.
	 *
	 * @return una copia de la lista de registros
	 */
	public List<EventoInfractor> listar() {
		return new ArrayList<>(lista);
	}

	/**
	 * Elimina una relacion evento-infractor por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return true si se elimino, false si no existia
	 */
	public boolean eliminar(Long id) {
		EventoInfractor eventoInfractor = buscarPorId(id);
		if (eventoInfractor == null)
			return false;
		lista.remove(eventoInfractor);
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
