package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.UsuarioGuarda;

public class UsuarioGuardaRepository {

	// Por ahora guarda en memoria. Aqui va la conexion a la base de datos hecha a mano.
	private ArrayList<UsuarioGuarda> lista = new ArrayList<>();
	private long siguienteId = 1;

	public UsuarioGuarda guardar(UsuarioGuarda usuarioGuarda) {
		if (usuarioGuarda.getId() == null) {
			usuarioGuarda.setId(siguienteId);
			siguienteId++;
			lista.add(usuarioGuarda);
			return usuarioGuarda;
		}
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(usuarioGuarda.getId())) {
				lista.set(i, usuarioGuarda);
				return usuarioGuarda;
			}
		}
		lista.add(usuarioGuarda);
		return usuarioGuarda;
	}

	public UsuarioGuarda buscarPorId(Long id) {
		for (UsuarioGuarda usuarioGuarda : lista) {
			if (usuarioGuarda.getId().equals(id)) {
				return usuarioGuarda;
			}
		}
		return null;
	}

	public List<UsuarioGuarda> listar() {
		return new ArrayList<>(lista);
	}

	public boolean eliminar(Long id) {
		UsuarioGuarda usuarioGuarda = buscarPorId(id);
		if (usuarioGuarda == null)
			return false;
		lista.remove(usuarioGuarda);
		return true;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		return lista.size();
	}
}
