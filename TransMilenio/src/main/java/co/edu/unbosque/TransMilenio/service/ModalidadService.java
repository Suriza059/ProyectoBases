package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.ModalidadDTO;
import co.edu.unbosque.TransMilenio.entity.Modalidad;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.ModalidadRepository;

/**
 * Logica de negocio de la modalidad: crear, consultar, actualizar y eliminar. Convierte entre ModalidadDTO
 * y la entidad Modalidad, valida los datos con LanzadorDeExcepciones y guarda mediante ModalidadRepository.
 *
 * <p>No tiene reglas de validacion adicionales.
 */
public class ModalidadService implements CRUDOPERATION<ModalidadDTO> {

	/**
	 * Repositorio de la modalidad que usa esta clase.
	 */
	private ModalidadRepository modalidadRepository;

	/**
	 * Crea el servicio de la modalidad.
	 *
	 * @param modalidadRepository el repositorio de la modalidad
	 */
	public ModalidadService(ModalidadRepository modalidadRepository) {
		this.modalidadRepository = modalidadRepository;
	}

	/**
	 * Convierte el DTO en entidad.
	 *
	 * @param dto el DTO con los datos
	 * @return la entidad con los mismos datos
	 */
	private Modalidad aEntity(ModalidadDTO dto) {
		Modalidad entity = new Modalidad();
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
	private ModalidadDTO aDTO(Modalidad entity) {
		ModalidadDTO dto = new ModalidadDTO();
		dto.setId(entity.getId());
		dto.setNombre(entity.getNombre());
		return dto;
	}

	/**
	 * Crea una modalidad nueva. Valida los datos y el sistema le asigna el id.
	 *
	 * @param data los datos de la modalidad a crear
	 */
	@Override
	public void create(ModalidadDTO data) {
		Modalidad entity = aEntity(data);
		entity.setId(null);
		modalidadRepository.guardar(entity);
	}

	/**
	 * Obtiene todos los registros como DTO.
	 *
	 * @return la lista de DTO, vacia si no hay registros
	 */
	@Override
	public List<ModalidadDTO> getAll() {
		List<ModalidadDTO> dtoList = new ArrayList<>();
		for (Modalidad e : modalidadRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	/**
	 * Elimina una modalidad por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException si el registro no existe
	 */
	@Override
	public void deleteById(Long id) {
		if (!modalidadRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("La modalidad");
		modalidadRepository.eliminar(id);
	}

	/**
	 * Cuenta los registros.
	 *
	 * @return la cantidad de registros
	 */
	@Override
	public long count() {
		return modalidadRepository.contar();
	}

	/**
	 * Indica si existe un registro con ese identificador.
	 *
	 * @param id el identificador a comprobar
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		return modalidadRepository.existe(id);
	}

	/**
	 * Actualiza una modalidad existente. Valida los datos igual que al crear.
	 *
	 * @param id el identificador del registro a actualizar
	 * @param data los datos nuevos
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException si el registro no existe
	 */
	@Override
	public void updateById(Long id, ModalidadDTO data) {
		if (!modalidadRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("La modalidad");
		Modalidad entity = aEntity(data);
		entity.setId(id);
		modalidadRepository.guardar(entity);
	}
}
