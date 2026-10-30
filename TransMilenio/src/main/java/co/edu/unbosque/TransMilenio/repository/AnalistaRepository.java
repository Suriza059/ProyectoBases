package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.Analista;

public class AnalistaRepository {

	// Por ahora guarda en memoria. Aqui va la conexion a la base de datos hecha a mano.
	private ArrayList<Analista> lista = new ArrayList<>();
	private long siguienteId = 1;

	public Analista guardar(Analista analista) {
		if (analista.getId() == null) {
			analista.setId(siguienteId);
			siguienteId++;
			lista.add(analista);
			return analista;
		}
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(analista.getId())) {
				lista.set(i, analista);
				return analista;
			}
		}
		lista.add(analista);
		return analista;
	}

	public Analista buscarPorId(Long id) {
		for (Analista analista : lista) {
			if (analista.getId().equals(id)) {
				return analista;
			}
		}
		return null;
	}

	public List<Analista> listar() {
		return new ArrayList<>(lista);
	}

	public boolean eliminar(Long id) {
		Analista analista = buscarPorId(id);
		if (analista == null)
			return false;
		lista.remove(analista);
		return true;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		return lista.size();
	}
}
