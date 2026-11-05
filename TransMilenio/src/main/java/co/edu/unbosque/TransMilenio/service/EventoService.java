package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.EventoDTO;
import co.edu.unbosque.TransMilenio.entity.Evento;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.EstacionRepository;
import co.edu.unbosque.TransMilenio.repository.EventoRepository;
import co.edu.unbosque.TransMilenio.repository.GuardaRepository;
import co.edu.unbosque.TransMilenio.repository.ModalidadRepository;
import co.edu.unbosque.TransMilenio.repository.TarifaRepository;

/**
 * Logica de negocio del evento: crear, consultar, actualizar y eliminar. Convierte entre EventoDTO y la
 * entidad Evento, valida los datos con LanzadorDeExcepciones y guarda mediante EventoRepository.
 *
 * <p>Reglas de validacion:
 * <ul>
 * <li>Relacion: La estacion no existe</li>
 * <li>Relacion: La tarifa no existe</li>
 * <li>Relacion: La modalidad no existe</li>
 * <li>Relacion: El guarda no existe</li>
 * <li>Dato obligatorio: El campo idEstacion es obligatorio</li>
 * <li>Dato obligatorio: El campo idTarifa es obligatorio</li>
 * <li>Dato obligatorio: El campo idModalidad es obligatorio</li>
 * <li>Dato obligatorio: El campo idGuarda es obligatorio</li>
 * </ul>
 */
public class EventoService implements CRUDOPERATION<EventoDTO> {

	/**
	 * Repositorio del evento que usa esta clase.
	 */
	private EventoRepository eventoRepository;
	/**
	 * Repositorio de la estacion que usa esta clase.
	 */
	private EstacionRepository estacionRepository;
	/**
	 * Repositorio de la tarifa que usa esta clase.
	 */
	private TarifaRepository tarifaRepository;
	/**
	 * Repositorio de la modalidad que usa esta clase.
	 */
	private ModalidadRepository modalidadRepository;
	/**
	 * Repositorio del guarda que usa esta clase.
	 */
	private GuardaRepository guardaRepository;

	/**
	 * Crea el servicio del evento.
	 *
	 * @param eventoRepository el repositorio del evento
	 * @param estacionRepository el repositorio de la estacion
	 * @param tarifaRepository el repositorio de la tarifa
	 * @param modalidadRepository el repositorio de la modalidad
	 * @param guardaRepository el repositorio del guarda
	 */
	public EventoService(EventoRepository eventoRepository, EstacionRepository estacionRepository, TarifaRepository tarifaRepository, ModalidadRepository modalidadRepository, GuardaRepository guardaRepository) {
		this.eventoRepository = eventoRepository;
		this.estacionRepository = estacionRepository;
		this.tarifaRepository = tarifaRepository;
		this.modalidadRepository = modalidadRepository;
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
	private void validar(EventoDTO data) {
		if (data.getIdEstacion() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idEstacion es obligatorio");
		if (!estacionRepository.existe(data.getIdEstacion()))
			LanzadorDeExcepciones.relacionNoExiste("La estacion no existe");
		if (data.getIdTarifa() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idTarifa es obligatorio");
		if (!tarifaRepository.existe(data.getIdTarifa()))
			LanzadorDeExcepciones.relacionNoExiste("La tarifa no existe");
		if (data.getIdModalidad() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idModalidad es obligatorio");
		if (!modalidadRepository.existe(data.getIdModalidad()))
			LanzadorDeExcepciones.relacionNoExiste("La modalidad no existe");
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
	private Evento aEntity(EventoDTO dto) {
		Evento entity = new Evento();
		entity.setId(dto.getId());
		entity.setFecha(dto.getFecha());
		entity.setHora(dto.getHora());
		entity.setNumeroPersonas(dto.getNumeroPersonas());
		entity.setIdEstacion(dto.getIdEstacion());
		entity.setIdTarifa(dto.getIdTarifa());
		entity.setIdModalidad(dto.getIdModalidad());
		entity.setIdGuarda(dto.getIdGuarda());
		return entity;
	}

	/**
	 * Convierte la entidad en DTO.
	 *
	 * @param entity la entidad a convertir
	 * @return el DTO con los mismos datos
	 */
	private EventoDTO aDTO(Evento entity) {
		EventoDTO dto = new EventoDTO();
		dto.setId(entity.getId());
		dto.setFecha(entity.getFecha());
		dto.setHora(entity.getHora());
		dto.setNumeroPersonas(entity.getNumeroPersonas());
		dto.setIdEstacion(entity.getIdEstacion());
		dto.setIdTarifa(entity.getIdTarifa());
		dto.setIdModalidad(entity.getIdModalidad());
		dto.setIdGuarda(entity.getIdGuarda());
		return dto;
	}

	/**
	 * Crea un evento nuevo. Valida los datos y el sistema le asigna el id.
	 *
	 * @param data los datos del evento a crear
	 * @throws co.edu.unbosque.TransMilenio.exception.DatoInvalidoException si falta un dato obligatorio o
	 *     un dato no es valido
	 * @throws co.edu.unbosque.TransMilenio.exception.RelacionNoExisteException si una llave foranea apunta
	 *     a un registro que no existe
	 */
	@Override
	public void create(EventoDTO data) {
		validar(data);
		Evento entity = aEntity(data);
		entity.setId(null);
		eventoRepository.guardar(entity);
	}

	/**
	 * Obtiene todos los registros como DTO.
	 *
	 * @return la lista de DTO, vacia si no hay registros
	 */
	@Override
	public List<EventoDTO> getAll() {
		List<EventoDTO> dtoList = new ArrayList<>();
		for (Evento e : eventoRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	/**
	 * Elimina un evento por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException si el registro no existe
	 */
	@Override
	public void deleteById(Long id) {
		if (!eventoRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El evento");
		eventoRepository.eliminar(id);
	}

	/**
	 * Cuenta los registros.
	 *
	 * @return la cantidad de registros
	 */
	@Override
	public long count() {
		return eventoRepository.contar();
	}

	/**
	 * Indica si existe un registro con ese identificador.
	 *
	 * @param id el identificador a comprobar
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		return eventoRepository.existe(id);
	}

	/**
	 * Actualiza un evento existente. Valida los datos igual que al crear.
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
	public void updateById(Long id, EventoDTO data) {
		if (!eventoRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El evento");
		validar(data);
		Evento entity = aEntity(data);
		entity.setId(id);
		eventoRepository.guardar(entity);
	}
}
