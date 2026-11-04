package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.Usuario;

/**
 * Repositorio del usuario. Guarda los registros en una lista en memoria (los datos se pierden al reiniciar
 * la aplicacion). Aqui es donde se conecta la base de datos: solo hay que cambiar el cuerpo de sus metodos,
 * el resto del back no cambia.
 *
 * <p>Los ids se asignan con un contador (siguienteId), empezando en 1.
 */
public class UsuarioRepository {

	/**
	 * Lista en memoria donde se guardan los registros.
	 */
	private ArrayList<Usuario> lista = new ArrayList<>();
	/**
	 * Siguiente id que se asignara a un registro nuevo.
	 */
	private long siguienteId = 1;

	/**
	 * Guarda el registro. Si no tiene id, le asigna uno nuevo y lo agrega; si ya tiene id, reemplaza el
	 * registro existente.
	 *
	 * @param usuario el usuario a guardar
	 * @return el registro guardado
	 */
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

	/**
	 * Busca un usuario por su identificador.
	 *
	 * @param id el identificador a buscar
	 * @return el registro encontrado, o null si no existe
	 */
	public Usuario buscarPorId(Long id) {
		for (Usuario usuario : lista) {
			if (usuario.getId().equals(id)) {
				return usuario;
			}
		}
		return null;
	}

	/**
	 * Busca un usuario por sus datos unicos (NombreUsuario). Se usa para impedir registros repetidos.
	 *
	 * @param nombreUsuario el nombre de usuario con el que inicia sesion (no se puede repetir)
	 * @return el registro encontrado, o null si no existe
	 */
	public Usuario buscarPorNombreUsuario(String nombreUsuario) {
		for (Usuario usuario : lista) {
			if (usuario.getNombreUsuario() != null && usuario.getNombreUsuario().equals(nombreUsuario)) {
				return usuario;
			}
		}
		return null;
	}

	/**
	 * Obtiene todos los registros.
	 *
	 * @return una copia de la lista de registros
	 */
	public List<Usuario> listar() {
		return new ArrayList<>(lista);
	}

	/**
	 * Elimina un usuario por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return true si se elimino, false si no existia
	 */
	public boolean eliminar(Long id) {
		Usuario usuario = buscarPorId(id);
		if (usuario == null)
			return false;
		lista.remove(usuario);
		return true;
	}

	/**
	 * Indica si existe un registro con ese identificador.
	 *
	 * @param id el identificador a comprobar
	 * @return true si existe, false si no
	 */
	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	/**
	 * Cuenta los registros guardados.
	 *
	 * @return la cantidad de registros
	 */
	public long contar() {
		return lista.size();
	}
}
