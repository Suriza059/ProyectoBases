package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.UsuarioGuarda;

public class UsuarioGuardaRepository {

	// Por ahora guarda en memoria. Aqui va la conexion a la base de datos hecha a mano.
	private ArrayList<UsuarioGuarda> lista = new ArrayList<>();

	public UsuarioGuarda guardar(UsuarioGuarda usuarioGuarda) {
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getIdUsuario().equals(usuarioGuarda.getIdUsuario())) {
				lista.set(i, usuarioGuarda);
				return usuarioGuarda;
			}
		}
		lista.add(usuarioGuarda);
		return usuarioGuarda;
	}

	public UsuarioGuarda buscarPorId(Long id) {
		for (UsuarioGuarda usuarioGuarda : lista) {
			if (usuarioGuarda.getIdUsuario().equals(id)) {
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
