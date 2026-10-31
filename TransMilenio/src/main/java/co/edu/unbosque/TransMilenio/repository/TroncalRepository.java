package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.Troncal;

public class TroncalRepository {

	// Por ahora guarda en memoria. Aqui va la conexion a la base de datos hecha a mano.
	private ArrayList<Troncal> lista = new ArrayList<>();
	private long siguienteId = 1;

	public Troncal guardar(Troncal troncal) {
		if (troncal.getId() == null) {
			troncal.setId(siguienteId);
			siguienteId++;
			lista.add(troncal);
			return troncal;
		}
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(troncal.getId())) {
				lista.set(i, troncal);
				return troncal;
			}
		}
		lista.add(troncal);
		return troncal;
	}

	public Troncal buscarPorId(Long id) {
		for (Troncal troncal : lista) {
			if (troncal.getId().equals(id)) {
				return troncal;
			}
		}
		return null;
	}

	public List<Troncal> listar() {
		return new ArrayList<>(lista);
	}

	public boolean eliminar(Long id) {
		Troncal troncal = buscarPorId(id);
		if (troncal == null)
			return false;
		lista.remove(troncal);
		return true;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		return lista.size();
	}
}
