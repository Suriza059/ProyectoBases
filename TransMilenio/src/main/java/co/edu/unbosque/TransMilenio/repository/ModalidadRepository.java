package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.Modalidad;

public class ModalidadRepository {

	// Por ahora guarda en memoria. Aqui va la conexion a la base de datos hecha a mano.
	private ArrayList<Modalidad> lista = new ArrayList<>();
	private long siguienteId = 1;

	public Modalidad guardar(Modalidad modalidad) {
		if (modalidad.getId() == null) {
			modalidad.setId(siguienteId);
			siguienteId++;
			lista.add(modalidad);
			return modalidad;
		}
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(modalidad.getId())) {
				lista.set(i, modalidad);
				return modalidad;
			}
		}
		lista.add(modalidad);
		return modalidad;
	}

	public Modalidad buscarPorId(Long id) {
		for (Modalidad modalidad : lista) {
			if (modalidad.getId().equals(id)) {
				return modalidad;
			}
		}
		return null;
	}

	public List<Modalidad> listar() {
		return new ArrayList<>(lista);
	}

	public boolean eliminar(Long id) {
		Modalidad modalidad = buscarPorId(id);
		if (modalidad == null)
			return false;
		lista.remove(modalidad);
		return true;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		return lista.size();
	}
}
