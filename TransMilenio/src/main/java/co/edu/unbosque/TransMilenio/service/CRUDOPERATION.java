package co.edu.unbosque.TransMilenio.service;

import java.util.List;

/**
 * Operaciones basicas (crear, consultar, actualizar y eliminar) que debe ofrecer cada service. El parametro
 * T es el DTO con el que trabaja el service.
 *
 * @param <T> el tipo de DTO con el que trabaja el service
 */
public interface CRUDOPERATION<T> {

	/**
	 * Crea un registro.
	 *
	 * @param data los datos del registro a crear
	 */
	public void create(T data);

	/**
	 * Obtiene todos los registros.
	 *
	 * @return la lista de registros, vacia si no hay ninguno
	 */
	public List<T> getAll();

	/**
	 * Elimina un registro.
	 *
	 * @param id el identificador del registro a eliminar
	 */
	public void deleteById(Long id);

	/**
	 * Cuenta los registros.
	 *
	 * @return la cantidad de registros
	 */
	public long count();

	/**
	 * Indica si existe un registro.
	 *
	 * @param id el identificador del registro
	 * @return true si existe, false si no
	 */
	public boolean exist(Long id);

	/**
	 * Actualiza un registro.
	 *
	 * @param id el identificador del registro a actualizar
	 * @param data los datos nuevos del registro
	 */
	public void updateById(Long id, T data);
}
