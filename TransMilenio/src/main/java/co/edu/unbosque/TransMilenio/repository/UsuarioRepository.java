package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.Usuario;

public class UsuarioRepository {

	// Por ahora guarda en memoria. Aqui va la conexion a la base de datos hecha a mano.
	private ArrayList<Usuario> lista = new ArrayList<>();
	private long siguienteId = 1;

	public Usuario guardar(Usuario usuario) {
		if (usuario.getId() == null) {
			usuario.setId(siguienteId);
			siguienteId++;
			lista.add(usuario);
			return usuario;
		}
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(usuario.getId())) {
				lista.set(i, usuario);
				return usuario;
			}
		}
		lista.add(usuario);
		return usuario;
	}

	public Usuario buscarPorId(Long id) {
		for (Usuario usuario : lista) {
			if (usuario.getId().equals(id)) {
				return usuario;
			}
		}
		return null;
	}

	public List<Usuario> listar() {
		return new ArrayList<>(lista);
	}

	public boolean eliminar(Long id) {
		Usuario usuario = buscarPorId(id);
		if (usuario == null)
			return false;
		lista.remove(usuario);
		return true;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		return lista.size();
	}
}
