package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.TroncalDTO;
import co.edu.unbosque.TransMilenio.entity.Troncal;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.TroncalRepository;

/**
 * Logica de negocio de la troncal: crear, consultar, actualizar y eliminar. Convierte entre TroncalDTO y la
 * entidad Troncal, valida los datos con LanzadorDeExcepciones y guarda mediante TroncalRepository.
 *
 * <p>No tiene reglas de validacion adicionales.
 */
public class TroncalService implements CRUDOPERATION<TroncalDTO> {

	/**
	 * Repositorio de la troncal que usa esta clase.
	 */
	private TroncalRepository troncalRepository;

	/**
	 * Crea el servicio de la troncal.
	 *
	 * @param troncalRepository el repositorio de la troncal
	 */
	public TroncalService(TroncalRepository troncalRepository) {
		this.troncalRepository = troncalRepository;
	}

	/**
	 * Convierte el DTO en entidad.
	 *
	 * @param dto el DTO con los datos
	 * @return la entidad con los mismos datos
	 */
	private Troncal aEntity(TroncalDTO dto) {
		Troncal entity = new Troncal();
		entity.setId(dto.getId());
		entity.setNombre(dto.getNombre());
		return entity;
	}

	/**
	 * Convierte la entidad en DTO.
	 *
	 * @param entity la entidad a convertir
	 * @return el DTO con los mismos datos
	 */
	private TroncalDTO aDTO(Troncal entity) {
		TroncalDTO dto = new TroncalDTO();
		dto.setId(entity.getId());
		dto.setNombre(entity.getNombre());
		return dto;
	}

	/**
	 * Crea una troncal nueva. Valida los datos y el sistema le asigna el id.
	 *
	 * @param data los datos de la troncal a crear
	 */
	@Override
	public void create(TroncalDTO data) {
		Troncal entity = aEntity(data);
		entity.setId(null);
		troncalRepository.guardar(entity);
	}

	/**
	 * Obtiene todos los registros como DTO.
	 *
	 * @return la lista de DTO, vacia si no hay registros
	 */
	@Override
	public List<TroncalDTO> getAll() {
		List<TroncalDTO> dtoList = new ArrayList<>();
		for (Troncal e : troncalRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	/**
	 * Elimina una troncal por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException si el registro no existe
	 */
	@Override
	public void deleteById(Long id) {
		if (!troncalRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("La troncal");
		troncalRepository.eliminar(id);
	}

	/**
	 * Cuenta los registros.
	 *
	 * @return la cantidad de registros
	 */
	@Override
	public long count() {
		return troncalRepository.contar();
	}

	/**
	 * Indica si existe un registro con ese identificador.
	 *
	 * @param id el identificador a comprobar
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		return troncalRepository.existe(id);
	}

	/**
	 * Actualiza una troncal existente. Valida los datos igual que al crear.
	 *
	 * @param id el identificador del registro a actualizar
	 * @param data los datos nuevos
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException si el registro no existe
	 */
	@Override
	public void updateById(Long id, TroncalDTO data) {
		if (!troncalRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("La troncal");
		Troncal entity = aEntity(data);
		entity.setId(id);
		troncalRepository.guardar(entity);
	}
}
