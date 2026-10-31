package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.Guarda;

public class GuardaRepository {

	// Por ahora guarda en memoria. Aqui va la conexion a la base de datos hecha a mano.
	private ArrayList<Guarda> lista = new ArrayList<>();
	private long siguienteId = 1;

	public Guarda guardar(Guarda guarda) {
		if (guarda.getId() == null) {
			guarda.setId(siguienteId);
			siguienteId++;
			lista.add(guarda);
			return guarda;
		}
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(guarda.getId())) {
				lista.set(i, guarda);
				return guarda;
			}
		}
		lista.add(guarda);
		return guarda;
	}

	public Guarda buscarPorId(Long id) {
		for (Guarda guarda : lista) {
			if (guarda.getId().equals(id)) {
				return guarda;
			}
		}
		return null;
	}

	public List<Guarda> listar() {
		return new ArrayList<>(lista);
	}

	public boolean eliminar(Long id) {
		Guarda guarda = buscarPorId(id);
		if (guarda == null)
			return false;
		lista.remove(guarda);
		return true;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		return lista.size();
	}
}
