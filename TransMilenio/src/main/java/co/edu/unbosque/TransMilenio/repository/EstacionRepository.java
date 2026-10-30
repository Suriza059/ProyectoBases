package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.Estacion;

public class EstacionRepository {

	// Por ahora guarda en memoria. Aqui va la conexion a la base de datos hecha a mano.
	private ArrayList<Estacion> lista = new ArrayList<>();
	private long siguienteId = 1;

	public Estacion guardar(Estacion estacion) {
		if (estacion.getId() == null) {
			estacion.setId(siguienteId);
			siguienteId++;
			lista.add(estacion);
			return estacion;
		}
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(estacion.getId())) {
				lista.set(i, estacion);
				return estacion;
			}
		}
		lista.add(estacion);
		return estacion;
	}

	public Estacion buscarPorId(Long id) {
		for (Estacion estacion : lista) {
			if (estacion.getId().equals(id)) {
				return estacion;
			}
		}
		return null;
	}

	public Estacion buscarPorNombre(String nombre) {
		for (Estacion estacion : lista) {
			if (estacion.getNombre() != null && estacion.getNombre().equals(nombre)) {
				return estacion;
			}
		}
		return null;
	}

	public List<Estacion> listar() {
		return new ArrayList<>(lista);
	}

	public boolean eliminar(Long id) {
		Estacion estacion = buscarPorId(id);
		if (estacion == null)
			return false;
		lista.remove(estacion);
		return true;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		return lista.size();
	}
}
