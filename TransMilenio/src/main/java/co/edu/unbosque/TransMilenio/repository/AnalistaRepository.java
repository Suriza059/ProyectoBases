package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.Analista;

public class AnalistaRepository {

	// Por ahora guarda en memoria. Aqui va la conexion a la base de datos hecha a mano.
	private ArrayList<Analista> lista = new ArrayList<>();

	public Analista guardar(Analista analista) {
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getIdUsuario().equals(analista.getIdUsuario())) {
				lista.set(i, analista);
				return analista;
			}
		}
		lista.add(analista);
		return analista;
	}

	public Analista buscarPorId(Long id) {
		for (Analista analista : lista) {
			if (analista.getIdUsuario().equals(id)) {
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
