package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.Administrador;

public class AdministradorRepository {

	// Por ahora guarda en memoria. Aqui va la conexion a la base de datos hecha a mano.
	private ArrayList<Administrador> lista = new ArrayList<>();

	public Administrador guardar(Administrador administrador) {
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getIdUsuario().equals(administrador.getIdUsuario())) {
				lista.set(i, administrador);
				return administrador;
			}
		}
		lista.add(administrador);
		return administrador;
	}

	public Administrador buscarPorId(Long id) {
		for (Administrador administrador : lista) {
			if (administrador.getIdUsuario().equals(id)) {
				return administrador;
			}
		}
		return null;
	}

	public List<Administrador> listar() {
		return new ArrayList<>(lista);
	}

	public boolean eliminar(Long id) {
		Administrador administrador = buscarPorId(id);
		if (administrador == null)
			return false;
		lista.remove(administrador);
		return true;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		return lista.size();
	}
}
