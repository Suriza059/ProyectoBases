package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.EventoInfractorDTO;
import co.edu.unbosque.TransMilenio.entity.EventoInfractor;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.EventoInfractorRepository;
import co.edu.unbosque.TransMilenio.repository.EventoRepository;
import co.edu.unbosque.TransMilenio.repository.InfractorRepository;

/**
 * Logica de negocio de la relacion evento-infractor: crear, consultar, actualizar y eliminar. Convierte
 * entre EventoInfractorDTO y la entidad EventoInfractor, valida los datos con LanzadorDeExcepciones y
 * guarda mediante EventoInfractorRepository.
 *
 * <p>Reglas de validacion:
 * <ul>
 * <li>Unicidad: Esa pareja evento-infractor ya existe</li>
 * <li>Relacion: El evento no existe</li>
 * <li>Relacion: El infractor no existe</li>
 * <li>Dato obligatorio: El campo idEvento es obligatorio</li>
 * <li>Dato obligatorio: El campo idInfractor es obligatorio</li>
 * </ul>
 */
public class EventoInfractorService implements CRUDOPERATION<EventoInfractorDTO> {

	/**
	 * Repositorio de la relacion evento-infractor que usa esta clase.
	 */
	private EventoInfractorRepository eventoInfractorRepository;
	/**
	 * Repositorio del evento que usa esta clase.
	 */
	private EventoRepository eventoRepository;
	/**
	 * Repositorio del infractor que usa esta clase.
	 */
	private InfractorRepository infractorRepository;

	/**
	 * Crea el servicio de la relacion evento-infractor.
	 *
	 * @param eventoInfractorRepository el repositorio de la relacion evento-infractor
	 * @param eventoRepository el repositorio del evento
	 * @param infractorRepository el repositorio del infractor
	 */
	public EventoInfractorService(EventoInfractorRepository eventoInfractorRepository, EventoRepository eventoRepository, InfractorRepository infractorRepository) {
		this.eventoInfractorRepository = eventoInfractorRepository;
		this.eventoRepository = eventoRepository;
		this.infractorRepository = infractorRepository;
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
	private void validar(EventoInfractorDTO data) {
		if (data.getIdEvento() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idEvento es obligatorio");
		if (!eventoRepository.existe(data.getIdEvento()))
			LanzadorDeExcepciones.relacionNoExiste("El evento no existe");
		if (data.getIdInfractor() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idInfractor es obligatorio");
		if (!infractorRepository.existe(data.getIdInfractor()))
			LanzadorDeExcepciones.relacionNoExiste("El infractor no existe");
	}

	/**
	 * Convierte el DTO en entidad.
	 *
	 * @param dto el DTO con los datos
	 * @return la entidad con los mismos datos
	 */
	private EventoInfractor aEntity(EventoInfractorDTO dto) {
		EventoInfractor entity = new EventoInfractor();
		entity.setId(dto.getId());
		entity.setIdEvento(dto.getIdEvento());
		entity.setIdInfractor(dto.getIdInfractor());
		return entity;
	}

	/**
	 * Convierte la entidad en DTO.
	 *
	 * @param entity la entidad a convertir
	 * @return el DTO con los mismos datos
	 */
	private EventoInfractorDTO aDTO(EventoInfractor entity) {
		EventoInfractorDTO dto = new EventoInfractorDTO();
		dto.setId(entity.getId());
		dto.setIdEvento(entity.getIdEvento());
		dto.setIdInfractor(entity.getIdInfractor());
		return dto;
	}

	/**
	 * Crea una relacion evento-infractor nueva. Valida los datos y el sistema le asigna el id.
	 *
	 * @param data los datos de la relacion evento-infractor a crear
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroDuplicadoException si ya existe un registro
	 *     con los mismos datos unicos
	 * @throws co.edu.unbosque.TransMilenio.exception.DatoInvalidoException si falta un dato obligatorio o
	 *     un dato no es valido
	 * @throws co.edu.unbosque.TransMilenio.exception.RelacionNoExisteException si una llave foranea apunta
	 *     a un registro que no existe
	 */
	@Override
	public void create(EventoInfractorDTO data) {
		if (eventoInfractorRepository.buscarPorIdEventoYIdInfractor(data.getIdEvento(), data.getIdInfractor()) != null)
			LanzadorDeExcepciones.registroDuplicado("Esa pareja evento-infractor ya existe");
		validar(data);
		EventoInfractor entity = aEntity(data);
		entity.setId(null);
		eventoInfractorRepository.guardar(entity);
	}

	/**
	 * Obtiene todos los registros como DTO.
	 *
	 * @return la lista de DTO, vacia si no hay registros
	 */
	@Override
	public List<EventoInfractorDTO> getAll() {
		List<EventoInfractorDTO> dtoList = new ArrayList<>();
		for (EventoInfractor e : eventoInfractorRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	/**
	 * Elimina una relacion evento-infractor por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException si el registro no existe
	 */
	@Override
	public void deleteById(Long id) {
		if (!eventoInfractorRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("La pareja evento-infractor");
		eventoInfractorRepository.eliminar(id);
	}

	/**
	 * Cuenta los registros.
	 *
	 * @return la cantidad de registros
	 */
	@Override
	public long count() {
		return eventoInfractorRepository.contar();
	}

	/**
	 * Indica si existe un registro con ese identificador.
	 *
	 * @param id el identificador a comprobar
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		return eventoInfractorRepository.existe(id);
	}

	/**
	 * Actualiza una relacion evento-infractor existente. Valida los datos igual que al crear.
	 *
	 * @param id el identificador del registro a actualizar
	 * @param data los datos nuevos
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException si el registro no existe
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroDuplicadoException si ya existe un registro
	 *     con los mismos datos unicos
	 * @throws co.edu.unbosque.TransMilenio.exception.DatoInvalidoException si falta un dato obligatorio o
	 *     un dato no es valido
	 * @throws co.edu.unbosque.TransMilenio.exception.RelacionNoExisteException si una llave foranea apunta
	 *     a un registro que no existe
	 */
	@Override
	public void updateById(Long id, EventoInfractorDTO data) {
		if (!eventoInfractorRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("La pareja evento-infractor");
		validar(data);
		EventoInfractor otro = eventoInfractorRepository.buscarPorIdEventoYIdInfractor(data.getIdEvento(), data.getIdInfractor());
		if (otro != null && !otro.getId().equals(id))
			LanzadorDeExcepciones.registroDuplicado("Esa pareja evento-infractor ya existe");
		EventoInfractor entity = aEntity(data);
		entity.setId(id);
		eventoInfractorRepository.guardar(entity);
	}
}
