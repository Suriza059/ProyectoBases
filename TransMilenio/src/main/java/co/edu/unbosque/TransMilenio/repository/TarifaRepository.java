package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.Tarifa;

public class TarifaRepository {

	// Por ahora guarda en memoria. Aqui va la conexion a la base de datos hecha a mano.
	private ArrayList<Tarifa> lista = new ArrayList<>();
	private long siguienteId = 1;

	public Tarifa guardar(Tarifa tarifa) {
		if (tarifa.getId() == null) {
			tarifa.setId(siguienteId);
			siguienteId++;
			lista.add(tarifa);
			return tarifa;
		}
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(tarifa.getId())) {
				lista.set(i, tarifa);
				return tarifa;
			}
		}
		lista.add(tarifa);
		return tarifa;
	}

	public Tarifa buscarPorId(Long id) {
		for (Tarifa tarifa : lista) {
			if (tarifa.getId().equals(id)) {
				return tarifa;
			}
		}
		return null;
	}

	public List<Tarifa> listar() {
		return new ArrayList<>(lista);
	}

	public boolean eliminar(Long id) {
		Tarifa tarifa = buscarPorId(id);
		if (tarifa == null)
			return false;
		lista.remove(tarifa);
		return true;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		return lista.size();
	}
}
