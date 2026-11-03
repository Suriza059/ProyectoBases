package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.TarifaDTO;
import co.edu.unbosque.TransMilenio.entity.Tarifa;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.TarifaRepository;

/**
 * Logica de negocio de la tarifa: crear, consultar, actualizar y eliminar. Convierte entre TarifaDTO y la
 * entidad Tarifa, valida los datos con LanzadorDeExcepciones y guarda mediante TarifaRepository.
 *
 * <p>No tiene reglas de validacion adicionales.
 */
public class TarifaService implements CRUDOPERATION<TarifaDTO> {

	/**
	 * Repositorio de la tarifa que usa esta clase.
	 */
	private TarifaRepository tarifaRepository;

	/**
	 * Crea el servicio de la tarifa.
	 *
	 * @param tarifaRepository el repositorio de la tarifa
	 */
	public TarifaService(TarifaRepository tarifaRepository) {
		this.tarifaRepository = tarifaRepository;
	}

	/**
	 * Convierte el DTO en entidad.
	 *
	 * @param dto el DTO con los datos
	 * @return la entidad con los mismos datos
	 */
	private Tarifa aEntity(TarifaDTO dto) {
		Tarifa entity = new Tarifa();
		entity.setId(dto.getId());
		entity.setFechaInicio(dto.getFechaInicio());
		entity.setFechaFin(dto.getFechaFin());
		entity.setValor(dto.getValor());
		return entity;
	}

	/**
	 * Convierte la entidad en DTO.
	 *
	 * @param entity la entidad a convertir
	 * @return el DTO con los mismos datos
	 */
	private TarifaDTO aDTO(Tarifa entity) {
		TarifaDTO dto = new TarifaDTO();
		dto.setId(entity.getId());
		dto.setFechaInicio(entity.getFechaInicio());
		dto.setFechaFin(entity.getFechaFin());
		dto.setValor(entity.getValor());
		return dto;
	}

	/**
	 * Crea una tarifa nueva. Valida los datos y el sistema le asigna el id.
	 *
	 * @param data los datos de la tarifa a crear
	 */
	@Override
	public void create(TarifaDTO data) {
		Tarifa entity = aEntity(data);
		entity.setId(null);
		tarifaRepository.guardar(entity);
	}

	/**
	 * Obtiene todos los registros como DTO.
	 *
	 * @return la lista de DTO, vacia si no hay registros
	 */
	@Override
	public List<TarifaDTO> getAll() {
		List<TarifaDTO> dtoList = new ArrayList<>();
		for (Tarifa e : tarifaRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	/**
	 * Elimina una tarifa por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException si el registro no existe
	 */
	@Override
	public void deleteById(Long id) {
		if (!tarifaRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("La tarifa");
		tarifaRepository.eliminar(id);
	}

	/**
	 * Cuenta los registros.
	 *
	 * @return la cantidad de registros
	 */
	@Override
	public long count() {
		return tarifaRepository.contar();
	}

	/**
	 * Indica si existe un registro con ese identificador.
	 *
	 * @param id el identificador a comprobar
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		return tarifaRepository.existe(id);
	}

	/**
	 * Actualiza una tarifa existente. Valida los datos igual que al crear.
	 *
	 * @param id el identificador del registro a actualizar
	 * @param data los datos nuevos
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException si el registro no existe
	 */
	@Override
	public void updateById(Long id, TarifaDTO data) {
		if (!tarifaRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("La tarifa");
		Tarifa entity = aEntity(data);
		entity.setId(id);
		tarifaRepository.guardar(entity);
	}
}
