package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.GuardaDTO;
import co.edu.unbosque.TransMilenio.entity.Guarda;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.EstacionRepository;
import co.edu.unbosque.TransMilenio.repository.GuardaRepository;

/**
 * Logica de negocio del guarda: crear, consultar, actualizar y eliminar. Convierte entre GuardaDTO y la
 * entidad Guarda, valida los datos con LanzadorDeExcepciones y guarda mediante GuardaRepository.
 *
 * <p>Reglas de validacion:
 * <ul>
 * <li>Relacion: La estacion no existe</li>
 * <li>Relacion: El supervisor no existe</li>
 * <li>Dato obligatorio: El campo idEstacion es obligatorio</li>
 * </ul>
 */
public class GuardaService implements CRUDOPERATION<GuardaDTO> {

	/**
	 * Repositorio del guarda que usa esta clase.
	 */
	private GuardaRepository guardaRepository;
	/**
	 * Repositorio de la estacion que usa esta clase.
	 */
	private EstacionRepository estacionRepository;

	/**
	 * Crea el servicio del guarda.
	 *
	 * @param guardaRepository el repositorio del guarda
	 * @param estacionRepository el repositorio de la estacion
	 */
	public GuardaService(GuardaRepository guardaRepository, EstacionRepository estacionRepository) {
		this.guardaRepository = guardaRepository;
		this.estacionRepository = estacionRepository;
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
	private void validar(GuardaDTO data) {
		if (data.getIdEstacion() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idEstacion es obligatorio");
		if (!estacionRepository.existe(data.getIdEstacion()))
			LanzadorDeExcepciones.relacionNoExiste("La estacion no existe");
		if (data.getIdSupervisor() != null && !guardaRepository.existe(data.getIdSupervisor()))
			LanzadorDeExcepciones.relacionNoExiste("El supervisor no existe");
	}

	/**
	 * Convierte el DTO en entidad.
	 *
	 * @param dto el DTO con los datos
	 * @return la entidad con los mismos datos
	 */
	private Guarda aEntity(GuardaDTO dto) {
		Guarda entity = new Guarda();
		entity.setId(dto.getId());
		entity.setTurno(dto.getTurno());
		entity.setNombre(dto.getNombre());
		entity.setApellido(dto.getApellido());
		entity.setActivo(dto.getActivo());
		entity.setCargo(dto.getCargo());
		entity.setIdEstacion(dto.getIdEstacion());
		entity.setIdSupervisor(dto.getIdSupervisor());
		return entity;
	}

	/**
	 * Convierte la entidad en DTO.
	 *
	 * @param entity la entidad a convertir
	 * @return el DTO con los mismos datos
	 */
	private GuardaDTO aDTO(Guarda entity) {
		GuardaDTO dto = new GuardaDTO();
		dto.setId(entity.getId());
		dto.setTurno(entity.getTurno());
		dto.setNombre(entity.getNombre());
		dto.setApellido(entity.getApellido());
		dto.setActivo(entity.getActivo());
		dto.setCargo(entity.getCargo());
		dto.setIdEstacion(entity.getIdEstacion());
		dto.setIdSupervisor(entity.getIdSupervisor());
		return dto;
	}

	/**
	 * Crea un guarda nuevo. Valida los datos y el sistema le asigna el id.
	 *
	 * @param data los datos del guarda a crear
	 * @throws co.edu.unbosque.TransMilenio.exception.DatoInvalidoException si falta un dato obligatorio o
	 *     un dato no es valido
	 * @throws co.edu.unbosque.TransMilenio.exception.RelacionNoExisteException si una llave foranea apunta
	 *     a un registro que no existe
	 */
	@Override
	public void create(GuardaDTO data) {
		validar(data);
		Guarda entity = aEntity(data);
		entity.setId(null);
		guardaRepository.guardar(entity);
	}

	/**
	 * Obtiene todos los registros como DTO.
	 *
	 * @return la lista de DTO, vacia si no hay registros
	 */
	@Override
	public List<GuardaDTO> getAll() {
		List<GuardaDTO> dtoList = new ArrayList<>();
		for (Guarda e : guardaRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	/**
	 * Elimina un guarda por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException si el registro no existe
	 */
	@Override
	public void deleteById(Long id) {
		if (!guardaRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El guarda");
		guardaRepository.eliminar(id);
	}

	/**
	 * Cuenta los registros.
	 *
	 * @return la cantidad de registros
	 */
	@Override
	public long count() {
		return guardaRepository.contar();
	}

	/**
	 * Indica si existe un registro con ese identificador.
	 *
	 * @param id el identificador a comprobar
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		return guardaRepository.existe(id);
	}

	/**
	 * Actualiza un guarda existente. Valida los datos igual que al crear.
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
	public void updateById(Long id, GuardaDTO data) {
		if (!guardaRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El guarda");
		validar(data);
		Guarda entity = aEntity(data);
		entity.setId(id);
		guardaRepository.guardar(entity);
	}
}
