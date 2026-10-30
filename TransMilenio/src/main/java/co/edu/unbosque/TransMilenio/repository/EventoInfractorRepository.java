package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.EventoInfractor;

public class EventoInfractorRepository {

	// Por ahora guarda en memoria. Aqui va la conexion a la base de datos hecha a mano.
	private ArrayList<EventoInfractor> lista = new ArrayList<>();

	public EventoInfractor guardar(EventoInfractor eventoInfractor) {
		lista.add(eventoInfractor);
		return eventoInfractor;
	}

	public EventoInfractor buscar(Long idEvento, Long idInfractor) {
		for (EventoInfractor e : lista) {
			if (e.getIdEvento().equals(idEvento) && e.getIdInfractor().equals(idInfractor)) {
				return e;
			}
		}
		return null;
	}

	public List<EventoInfractor> listar() {
		return new ArrayList<>(lista);
	}

	public boolean eliminar(Long idEvento, Long idInfractor) {
		EventoInfractor e = buscar(idEvento, idInfractor);
		if (e == null)
			return false;
		lista.remove(e);
		return true;
	}

	public boolean existe(Long idEvento, Long idInfractor) {
		return buscar(idEvento, idInfractor) != null;
	}

	public long contar() {
		return lista.size();
	}
}
