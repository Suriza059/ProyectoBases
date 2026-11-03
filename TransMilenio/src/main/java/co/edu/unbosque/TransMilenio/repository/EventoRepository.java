package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.Evento;

/**
 * Repositorio del evento. Guarda los registros en una lista en memoria (los datos se pierden al reiniciar
 * la aplicacion). Aqui es donde se conecta la base de datos: solo hay que cambiar el cuerpo de sus metodos,
 * el resto del back no cambia.
 *
 * <p>Los ids se asignan con un contador (siguienteId), empezando en 1.
 */
public class EventoRepository {

	/**
	 * Lista en memoria donde se guardan los registros.
	 */
	private ArrayList<Evento> lista = new ArrayList<>();
	/**
	 * Siguiente id que se asignara a un registro nuevo.
	 */
	private long siguienteId = 1;

	/**
	 * Guarda el registro. Si no tiene id, le asigna uno nuevo y lo agrega; si ya tiene id, reemplaza el
	 * registro existente.
	 *
	 * @param evento el evento a guardar
	 * @return el registro guardado
	 */
	public Evento guardar(Evento evento) {
		if (evento.getId() == null) {
			evento.setId(siguienteId);
			siguienteId++;
			lista.add(evento);
			return evento;
		}
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(evento.getId())) {
				lista.set(i, evento);
				return evento;
			}
		}
		lista.add(evento);
		return evento;
	}

	/**
	 * Busca un evento por su identificador.
	 *
	 * @param id el identificador a buscar
	 * @return el registro encontrado, o null si no existe
	 */
	public Evento buscarPorId(Long id) {
		for (Evento evento : lista) {
			if (evento.getId().equals(id)) {
				return evento;
			}
		}
		return null;
	}

	/**
	 * Obtiene todos los registros.
	 *
	 * @return una copia de la lista de registros
	 */
	public List<Evento> listar() {
		return new ArrayList<>(lista);
	}

	/**
	 * Elimina un evento por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return true si se elimino, false si no existia
	 */
	public boolean eliminar(Long id) {
		Evento evento = buscarPorId(id);
		if (evento == null)
			return false;
		lista.remove(evento);
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
