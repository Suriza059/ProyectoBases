package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.AgresionDTO;
import co.edu.unbosque.TransMilenio.entity.Agresion;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.AgresionRepository;
import co.edu.unbosque.TransMilenio.repository.EventoRepository;
import co.edu.unbosque.TransMilenio.repository.GuardaRepository;

/**
 * Logica de negocio de la agresion: crear, consultar, actualizar y eliminar. Convierte entre AgresionDTO y
 * la entidad Agresion, valida los datos con LanzadorDeExcepciones y guarda mediante AgresionRepository.
 *
 * <p>Reglas de validacion:
 * <ul>
 * <li>Relacion: El evento no existe</li>
 * <li>Relacion: El guarda no existe</li>
 * <li>Dato obligatorio: El campo idEvento es obligatorio</li>
 * <li>Dato obligatorio: El campo idGuarda es obligatorio</li>
 * </ul>
 */
public class AgresionService implements CRUDOPERATION<AgresionDTO> {

	/**
	 * Repositorio de la agresion que usa esta clase.
	 */
	private AgresionRepository agresionRepository;
	/**
	 * Repositorio del evento que usa esta clase.
	 */
	private EventoRepository eventoRepository;
	/**
	 * Repositorio del guarda que usa esta clase.
	 */
	private GuardaRepository guardaRepository;

	/**
	 * Crea el servicio de la agresion.
	 *
	 * @param agresionRepository el repositorio de la agresion
	 * @param eventoRepository el repositorio del evento
	 * @param guardaRepository el repositorio del guarda
	 */
	public AgresionService(AgresionRepository agresionRepository, EventoRepository eventoRepository, GuardaRepository guardaRepository) {
		this.agresionRepository = agresionRepository;
		this.eventoRepository = eventoRepository;
		this.guardaRepository = guardaRepository;
	}

	/**
	 * Comprueba que las llaves foraneas obligatorias esten presentes y que apunten a registros que existen.
	 *
	 * @param data los datos a validar
	 * @throws co.edu.unbosque.TransMilenio.exception.DatoInvalidoException si falta un dato obligatorio o
	 *     un dato no es valido
	 * @throws co.edu.unbosque.TransMilenio.exception.RelacionNoExisteException si una llave foranea apunta
	 *     a un registro que no existe
	 */
	private void validar(AgresionDTO data) {
		if (data.getIdEvento() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idEvento es obligatorio");
		if (!eventoRepository.existe(data.getIdEvento()))
			LanzadorDeExcepciones.relacionNoExiste("El evento no existe");
		if (data.getIdGuarda() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idGuarda es obligatorio");
		if (!guardaRepository.existe(data.getIdGuarda()))
			LanzadorDeExcepciones.relacionNoExiste("El guarda no existe");
	}

	/**
	 * Convierte el DTO en entidad.
	 *
	 * @param dto el DTO con los datos
	 * @return la entidad con los mismos datos
	 */
	private Agresion aEntity(AgresionDTO dto) {
		Agresion entity = new Agresion();
		entity.setId(dto.getId());
		entity.setFecha(dto.getFecha());
		entity.setGravedad(dto.getGravedad());
		entity.setDescripcion(dto.getDescripcion());
		entity.setIdEvento(dto.getIdEvento());
		entity.setIdGuarda(dto.getIdGuarda());
		return entity;
	}

	/**
	 * Convierte la entidad en DTO.
	 *
	 * @param entity la entidad a convertir
	 * @return el DTO con los mismos datos
	 */
	private AgresionDTO aDTO(Agresion entity) {
		AgresionDTO dto = new AgresionDTO();
		dto.setId(entity.getId());
		dto.setFecha(entity.getFecha());
		dto.setGravedad(entity.getGravedad());
		dto.setDescripcion(entity.getDescripcion());
		dto.setIdEvento(entity.getIdEvento());
		dto.setIdGuarda(entity.getIdGuarda());
		return dto;
	}

	/**
	 * Crea una agresion nueva. Valida los datos y el sistema le asigna el id.
	 *
	 * @param data los datos de la agresion a crear
	 * @throws co.edu.unbosque.TransMilenio.exception.DatoInvalidoException si falta un dato obligatorio o
	 *     un dato no es valido
	 * @throws co.edu.unbosque.TransMilenio.exception.RelacionNoExisteException si una llave foranea apunta
	 *     a un registro que no existe
	 */
	@Override
	public void create(AgresionDTO data) {
		validar(data);
		Agresion entity = aEntity(data);
		entity.setId(null);
		agresionRepository.guardar(entity);
	}

	/**
	 * Obtiene todos los registros como DTO.
	 *
	 * @return la lista de DTO, vacia si no hay registros
	 */
	@Override
	public List<AgresionDTO> getAll() {
		List<AgresionDTO> dtoList = new ArrayList<>();
		for (Agresion e : agresionRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	/**
	 * Elimina una agresion por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException si el registro no existe
	 */
	@Override
	public void deleteById(Long id) {
		if (!agresionRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("La agresion");
		agresionRepository.eliminar(id);
	}

	/**
	 * Cuenta los registros.
	 *
	 * @return la cantidad de registros
	 */
	@Override
	public long count() {
		return agresionRepository.contar();
	}

	/**
	 * Indica si existe un registro con ese identificador.
	 *
	 * @param id el identificador a comprobar
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		return agresionRepository.existe(id);
	}

	/**
	 * Actualiza una agresion existente. Valida los datos igual que al crear.
	 *
	 * @param id el identificador del registro a actualizar
	 * @param data los datos nuevos
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException si el registro no existe
	 * @throws co.edu.unbosque.TransMilenio.exception.DatoInvalidoException si falta un dato obligatorio o
	 *     un dato no es valido
	 * @throws co.edu.unbosque.TransMilenio.exception.RelacionNoExisteException si una llave foranea apunta
	 *     a un registro que no existe
	 */
	@Override
	public void updateById(Long id, AgresionDTO data) {
		if (!agresionRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("La agresion");
		validar(data);
		Agresion entity = aEntity(data);
		entity.setId(id);
		agresionRepository.guardar(entity);
	}
}
