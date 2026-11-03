package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.EstacionDTO;
import co.edu.unbosque.TransMilenio.entity.Estacion;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.EstacionRepository;
import co.edu.unbosque.TransMilenio.repository.TroncalRepository;

/**
 * Logica de negocio de la estacion: crear, consultar, actualizar y eliminar. Convierte entre EstacionDTO y
 * la entidad Estacion, valida los datos con LanzadorDeExcepciones y guarda mediante EstacionRepository.
 *
 * <p>Reglas de validacion:
 * <ul>
 * <li>Relacion: La troncal no existe</li>
 * <li>Dato obligatorio: El campo idTroncal es obligatorio</li>
 * </ul>
 */
public class EstacionService implements CRUDOPERATION<EstacionDTO> {

	/**
	 * Repositorio de la estacion que usa esta clase.
	 */
	private EstacionRepository estacionRepository;
	/**
	 * Repositorio de la troncal que usa esta clase.
	 */
	private TroncalRepository troncalRepository;

	/**
	 * Crea el servicio de la estacion.
	 *
	 * @param estacionRepository el repositorio de la estacion
	 * @param troncalRepository el repositorio de la troncal
	 */
	public EstacionService(EstacionRepository estacionRepository, TroncalRepository troncalRepository) {
		this.estacionRepository = estacionRepository;
		this.troncalRepository = troncalRepository;
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
	private void validar(EstacionDTO data) {
		if (data.getIdTroncal() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idTroncal es obligatorio");
		if (!troncalRepository.existe(data.getIdTroncal()))
			LanzadorDeExcepciones.relacionNoExiste("La troncal no existe");
	}

	/**
	 * Convierte el DTO en entidad.
	 *
	 * @param dto el DTO con los datos
	 * @return la entidad con los mismos datos
	 */
	private Estacion aEntity(EstacionDTO dto) {
		Estacion entity = new Estacion();
		entity.setId(dto.getId());
		entity.setNombre(dto.getNombre());
		entity.setActiva(dto.getActiva());
		entity.setIdTroncal(dto.getIdTroncal());
		return entity;
	}

	/**
	 * Convierte la entidad en DTO.
	 *
	 * @param entity la entidad a convertir
	 * @return el DTO con los mismos datos
	 */
	private EstacionDTO aDTO(Estacion entity) {
		EstacionDTO dto = new EstacionDTO();
		dto.setId(entity.getId());
		dto.setNombre(entity.getNombre());
		dto.setActiva(entity.getActiva());
		dto.setIdTroncal(entity.getIdTroncal());
		return dto;
	}

	/**
	 * Crea una estacion nueva. Valida los datos y el sistema le asigna el id.
	 *
	 * @param data los datos de la estacion a crear
	 * @throws co.edu.unbosque.TransMilenio.exception.DatoInvalidoException si falta un dato obligatorio o
	 *     un dato no es valido
	 * @throws co.edu.unbosque.TransMilenio.exception.RelacionNoExisteException si una llave foranea apunta
	 *     a un registro que no existe
	 */
	@Override
	public void create(EstacionDTO data) {
		validar(data);
		Estacion entity = aEntity(data);
		entity.setId(null);
		estacionRepository.guardar(entity);
	}

	/**
	 * Obtiene todos los registros como DTO.
	 *
	 * @return la lista de DTO, vacia si no hay registros
	 */
	@Override
	public List<EstacionDTO> getAll() {
		List<EstacionDTO> dtoList = new ArrayList<>();
		for (Estacion e : estacionRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	/**
	 * Elimina una estacion por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException si el registro no existe
	 */
	@Override
	public void deleteById(Long id) {
		if (!estacionRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("La estacion");
		estacionRepository.eliminar(id);
	}

	/**
	 * Cuenta los registros.
	 *
	 * @return la cantidad de registros
	 */
	@Override
	public long count() {
		return estacionRepository.contar();
	}

	/**
	 * Indica si existe un registro con ese identificador.
	 *
	 * @param id el identificador a comprobar
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		return estacionRepository.existe(id);
	}

	/**
	 * Actualiza una estacion existente. Valida los datos igual que al crear.
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
	public void updateById(Long id, EstacionDTO data) {
		if (!estacionRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("La estacion");
		validar(data);
		Estacion entity = aEntity(data);
		entity.setId(id);
		estacionRepository.guardar(entity);
	}
}
