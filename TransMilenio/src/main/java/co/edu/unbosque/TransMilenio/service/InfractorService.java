package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.InfractorDTO;
import co.edu.unbosque.TransMilenio.entity.Infractor;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.InfractorRepository;

/**
 * Logica de negocio del infractor: crear, consultar, actualizar y eliminar. Convierte entre InfractorDTO y
 * la entidad Infractor, valida los datos con LanzadorDeExcepciones y guarda mediante InfractorRepository.
 *
 * <p>Reglas de validacion:
 * <ul>
 * <li>Unicidad: Ya existe un infractor con ese documento</li>
 * </ul>
 */
public class InfractorService implements CRUDOPERATION<InfractorDTO> {

	/**
	 * Repositorio del infractor que usa esta clase.
	 */
	private InfractorRepository infractorRepository;

	/**
	 * Crea el servicio del infractor.
	 *
	 * @param infractorRepository el repositorio del infractor
	 */
	public InfractorService(InfractorRepository infractorRepository) {
		this.infractorRepository = infractorRepository;
	}

	/**
	 * Convierte el DTO en entidad.
	 *
	 * @param dto el DTO con los datos
	 * @return la entidad con los mismos datos
	 */
	private Infractor aEntity(InfractorDTO dto) {
		Infractor entity = new Infractor();
		entity.setId(dto.getId());
		entity.setTipoDocumento(dto.getTipoDocumento());
		entity.setNumeroDocumento(dto.getNumeroDocumento());
		entity.setNombre(dto.getNombre());
		entity.setApellido(dto.getApellido());
		return entity;
	}

	/**
	 * Convierte la entidad en DTO.
	 *
	 * @param entity la entidad a convertir
	 * @return el DTO con los mismos datos
	 */
	private InfractorDTO aDTO(Infractor entity) {
		InfractorDTO dto = new InfractorDTO();
		dto.setId(entity.getId());
		dto.setTipoDocumento(entity.getTipoDocumento());
		dto.setNumeroDocumento(entity.getNumeroDocumento());
		dto.setNombre(entity.getNombre());
		dto.setApellido(entity.getApellido());
		return dto;
	}

	/**
	 * Crea un infractor nuevo. Valida los datos y el sistema le asigna el id.
	 *
	 * @param data los datos del infractor a crear
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroDuplicadoException si ya existe un registro
	 *     con los mismos datos unicos
	 */
	@Override
	public void create(InfractorDTO data) {
		if (infractorRepository.buscarPorTipoDocumentoYNumeroDocumento(data.getTipoDocumento(), data.getNumeroDocumento()) != null)
			LanzadorDeExcepciones.registroDuplicado("Ya existe un infractor con ese documento");
		Infractor entity = aEntity(data);
		entity.setId(null);
		infractorRepository.guardar(entity);
	}

	/**
	 * Obtiene todos los registros como DTO.
	 *
	 * @return la lista de DTO, vacia si no hay registros
	 */
	@Override
	public List<InfractorDTO> getAll() {
		List<InfractorDTO> dtoList = new ArrayList<>();
		for (Infractor e : infractorRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	/**
	 * Elimina un infractor por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException si el registro no existe
	 */
	@Override
	public void deleteById(Long id) {
		if (!infractorRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El infractor");
		infractorRepository.eliminar(id);
	}

	/**
	 * Cuenta los registros.
	 *
	 * @return la cantidad de registros
	 */
	@Override
	public long count() {
		return infractorRepository.contar();
	}

	/**
	 * Indica si existe un registro con ese identificador.
	 *
	 * @param id el identificador a comprobar
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		return infractorRepository.existe(id);
	}

	/**
	 * Actualiza un infractor existente. Valida los datos igual que al crear.
	 *
	 * @param id el identificador del registro a actualizar
	 * @param data los datos nuevos
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException si el registro no existe
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroDuplicadoException si ya existe un registro
	 *     con los mismos datos unicos
	 */
	@Override
	public void updateById(Long id, InfractorDTO data) {
		if (!infractorRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El infractor");
		Infractor otro = infractorRepository.buscarPorTipoDocumentoYNumeroDocumento(data.getTipoDocumento(), data.getNumeroDocumento());
		if (otro != null && !otro.getId().equals(id))
			LanzadorDeExcepciones.registroDuplicado("Ya existe un infractor con ese documento");
		Infractor entity = aEntity(data);
		entity.setId(id);
		infractorRepository.guardar(entity);
	}
}
