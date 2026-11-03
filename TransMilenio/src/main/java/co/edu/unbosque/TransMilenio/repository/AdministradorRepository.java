package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.Administrador;

/**
 * Repositorio del administrador. Guarda los registros en una lista en memoria (los datos se pierden al
 * reiniciar la aplicacion). Aqui es donde se conecta la base de datos: solo hay que cambiar el cuerpo de
 * sus metodos, el resto del back no cambia.
 *
 * <p>Esta entidad no genera su propio id: la llave es el id del usuario, que lo envia quien crea el
 * registro.
 */
public class AdministradorRepository {

	/**
	 * Lista en memoria donde se guardan los registros.
	 */
	private ArrayList<Administrador> lista = new ArrayList<>();

	/**
	 * Guarda el registro. Si ya existe uno con el mismo id de usuario lo reemplaza; si no, lo agrega.
	 *
	 * @param administrador el administrador a guardar
	 * @return el registro guardado
	 */
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

	/**
	 * Busca un administrador por su identificador (el id del usuario).
	 *
	 * @param id el identificador a buscar
	 * @return el registro encontrado, o null si no existe
	 */
	public Administrador buscarPorId(Long id) {
		for (Administrador administrador : lista) {
			if (administrador.getIdUsuario().equals(id)) {
				return administrador;
			}
		}
		return null;
	}

	/**
	 * Obtiene todos los registros.
	 *
	 * @return una copia de la lista de registros
	 */
	public List<Administrador> listar() {
		return new ArrayList<>(lista);
	}

	/**
	 * Elimina un administrador por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return true si se elimino, false si no existia
	 */
	public boolean eliminar(Long id) {
		Administrador administrador = buscarPorId(id);
		if (administrador == null)
			return false;
		lista.remove(administrador);
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
