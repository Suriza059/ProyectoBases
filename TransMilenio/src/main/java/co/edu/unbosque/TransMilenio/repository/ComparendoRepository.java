package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.Comparendo;

public class ComparendoRepository {

	// Por ahora guarda en memoria. Aqui va la conexion a la base de datos hecha a mano.
	private ArrayList<Comparendo> lista = new ArrayList<>();
	private long siguienteId = 1;

	public Comparendo guardar(Comparendo comparendo) {
		if (comparendo.getId() == null) {
			comparendo.setId(siguienteId);
			siguienteId++;
			lista.add(comparendo);
			return comparendo;
		}
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(comparendo.getId())) {
				lista.set(i, comparendo);
				return comparendo;
			}
		}
		lista.add(comparendo);
		return comparendo;
	}

	public Comparendo buscarPorId(Long id) {
		for (Comparendo comparendo : lista) {
			if (comparendo.getId().equals(id)) {
				return comparendo;
			}
		}
		return null;
	}

	public List<Comparendo> listar() {
		return new ArrayList<>(lista);
	}

	public boolean eliminar(Long id) {
		Comparendo comparendo = buscarPorId(id);
		if (comparendo == null)
			return false;
		lista.remove(comparendo);
		return true;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		return lista.size();
	}
}
