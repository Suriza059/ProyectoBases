package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.Evento;

public class EventoRepository {

	// Por ahora guarda en memoria. Aqui va la conexion a la base de datos hecha a mano.
	private ArrayList<Evento> lista = new ArrayList<>();
	private long siguienteId = 1;

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

	public Evento buscarPorId(Long id) {
		for (Evento evento : lista) {
			if (evento.getId().equals(id)) {
				return evento;
			}
		}
		return null;
	}

	public List<Evento> listar() {
		return new ArrayList<>(lista);
	}

	public boolean eliminar(Long id) {
		Evento evento = buscarPorId(id);
		if (evento == null)
			return false;
		lista.remove(evento);
		return true;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		return lista.size();
	}
}
