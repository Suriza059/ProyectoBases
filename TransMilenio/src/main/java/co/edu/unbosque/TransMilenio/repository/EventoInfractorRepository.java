package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.EventoInfractor;

public class EventoInfractorRepository {

	// Por ahora guarda en memoria. Aqui va la conexion a la base de datos hecha a mano.
	private ArrayList<EventoInfractor> lista = new ArrayList<>();
	private long siguienteId = 1;

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

	public EventoInfractor buscarPorId(Long id) {
		for (EventoInfractor eventoInfractor : lista) {
			if (eventoInfractor.getId().equals(id)) {
				return eventoInfractor;
			}
		}
		return null;
	}

	public EventoInfractor buscarPorIdEventoYIdInfractor(Long idEvento, Long idInfractor) {
		for (EventoInfractor eventoInfractor : lista) {
			if (eventoInfractor.getIdEvento() != null && eventoInfractor.getIdEvento().equals(idEvento) && eventoInfractor.getIdInfractor() != null && eventoInfractor.getIdInfractor().equals(idInfractor)) {
				return eventoInfractor;
			}
		}
		return null;
	}

	public List<EventoInfractor> listar() {
		return new ArrayList<>(lista);
	}

	public boolean eliminar(Long id) {
		EventoInfractor eventoInfractor = buscarPorId(id);
		if (eventoInfractor == null)
			return false;
		lista.remove(eventoInfractor);
		return true;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		return lista.size();
	}
}
