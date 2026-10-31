package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.Agresion;

public class AgresionRepository {

	// Por ahora guarda en memoria. Aqui va la conexion a la base de datos hecha a mano.
	private ArrayList<Agresion> lista = new ArrayList<>();
	private long siguienteId = 1;

	public Agresion guardar(Agresion agresion) {
		if (agresion.getId() == null) {
			agresion.setId(siguienteId);
			siguienteId++;
			lista.add(agresion);
			return agresion;
		}
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(agresion.getId())) {
				lista.set(i, agresion);
				return agresion;
			}
		}
		lista.add(agresion);
		return agresion;
	}

	public Agresion buscarPorId(Long id) {
		for (Agresion agresion : lista) {
			if (agresion.getId().equals(id)) {
				return agresion;
			}
		}
		return null;
	}

	public List<Agresion> listar() {
		return new ArrayList<>(lista);
	}

	public boolean eliminar(Long id) {
		Agresion agresion = buscarPorId(id);
		if (agresion == null)
			return false;
		lista.remove(agresion);
		return true;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		return lista.size();
	}
}
