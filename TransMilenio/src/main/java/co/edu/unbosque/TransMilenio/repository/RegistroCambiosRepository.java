package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.RegistroCambios;

public class RegistroCambiosRepository {

	// Por ahora guarda en memoria. Aqui va la conexion a la base de datos hecha a mano.
	private ArrayList<RegistroCambios> lista = new ArrayList<>();
	private long siguienteId = 1;

	public RegistroCambios guardar(RegistroCambios registroCambios) {
		if (registroCambios.getId() == null) {
			registroCambios.setId(siguienteId);
			siguienteId++;
			lista.add(registroCambios);
			return registroCambios;
		}
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(registroCambios.getId())) {
				lista.set(i, registroCambios);
				return registroCambios;
			}
		}
		lista.add(registroCambios);
		return registroCambios;
	}

	public RegistroCambios buscarPorId(Long id) {
		for (RegistroCambios registroCambios : lista) {
			if (registroCambios.getId().equals(id)) {
				return registroCambios;
			}
		}
		return null;
	}

	public List<RegistroCambios> listar() {
		return new ArrayList<>(lista);
	}

	public boolean eliminar(Long id) {
		RegistroCambios registroCambios = buscarPorId(id);
		if (registroCambios == null)
			return false;
		lista.remove(registroCambios);
		return true;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		return lista.size();
	}
}
