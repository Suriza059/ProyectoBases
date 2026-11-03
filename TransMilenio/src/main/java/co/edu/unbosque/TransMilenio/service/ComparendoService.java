package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.ComparendoDTO;
import co.edu.unbosque.TransMilenio.entity.Comparendo;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.ComparendoRepository;
import co.edu.unbosque.TransMilenio.repository.EventoInfractorRepository;

/**
 * Logica de negocio del comparendo: crear, consultar, actualizar y eliminar. Convierte entre ComparendoDTO
 * y la entidad Comparendo, valida los datos con LanzadorDeExcepciones y guarda mediante
 * ComparendoRepository.
 *
 * <p>Reglas de validacion:
 * <ul>
 * <li>Relacion: La pareja evento-infractor no existe</li>
 * <li>Dato obligatorio: El campo idEventoInfractor es obligatorio</li>
 * </ul>
 */
public class ComparendoService implements CRUDOPERATION<ComparendoDTO> {

	/**
	 * Repositorio del comparendo que usa esta clase.
	 */
	private ComparendoRepository comparendoRepository;
	/**
	 * Repositorio de la relacion evento-infractor que usa esta clase.
	 */
	private EventoInfractorRepository eventoInfractorRepository;

	/**
	 * Crea el servicio del comparendo.
	 *
	 * @param comparendoRepository el repositorio del comparendo
	 * @param eventoInfractorRepository el repositorio de la relacion evento-infractor
	 */
	public ComparendoService(ComparendoRepository comparendoRepository, EventoInfractorRepository eventoInfractorRepository) {
		this.comparendoRepository = comparendoRepository;
		this.eventoInfractorRepository = eventoInfractorRepository;
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
	private void validar(ComparendoDTO data) {
		if (data.getIdEventoInfractor() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idEventoInfractor es obligatorio");
		if (!eventoInfractorRepository.existe(data.getIdEventoInfractor()))
			LanzadorDeExcepciones.relacionNoExiste("La pareja evento-infractor no existe");
	}

	/**
	 * Convierte el DTO en entidad.
	 *
	 * @param dto el DTO con los datos
	 * @return la entidad con los mismos datos
	 */
	private Comparendo aEntity(ComparendoDTO dto) {
		Comparendo entity = new Comparendo();
		entity.setId(dto.getId());
		entity.setFecha(dto.getFecha());
		entity.setValor(dto.getValor());
		entity.setEstado(dto.getEstado());
		entity.setIdEventoInfractor(dto.getIdEventoInfractor());
		return entity;
	}

	/**
	 * Convierte la entidad en DTO.
	 *
	 * @param entity la entidad a convertir
	 * @return el DTO con los mismos datos
	 */
	private ComparendoDTO aDTO(Comparendo entity) {
		ComparendoDTO dto = new ComparendoDTO();
		dto.setId(entity.getId());
		dto.setFecha(entity.getFecha());
		dto.setValor(entity.getValor());
		dto.setEstado(entity.getEstado());
		dto.setIdEventoInfractor(entity.getIdEventoInfractor());
		return dto;
	}

	/**
	 * Crea un comparendo nuevo. Valida los datos y el sistema le asigna el id. El estado inicial es
	 * Pendiente.
	 *
	 * @param data los datos del comparendo a crear
	 * @throws co.edu.unbosque.TransMilenio.exception.DatoInvalidoException si falta un dato obligatorio o
	 *     un dato no es valido
	 * @throws co.edu.unbosque.TransMilenio.exception.RelacionNoExisteException si una llave foranea apunta
	 *     a un registro que no existe
	 */
	@Override
	public void create(ComparendoDTO data) {
		validar(data);
		Comparendo entity = aEntity(data);
		entity.setId(null);
		entity.setEstado("Pendiente");
		comparendoRepository.guardar(entity);
	}

	/**
	 * Obtiene todos los registros como DTO.
	 *
	 * @return la lista de DTO, vacia si no hay registros
	 */
	@Override
	public List<ComparendoDTO> getAll() {
		List<ComparendoDTO> dtoList = new ArrayList<>();
		for (Comparendo e : comparendoRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	/**
	 * Elimina un comparendo por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException si el registro no existe
	 */
	@Override
	public void deleteById(Long id) {
		if (!comparendoRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El comparendo");
		comparendoRepository.eliminar(id);
	}

	/**
	 * Cuenta los registros.
	 *
	 * @return la cantidad de registros
	 */
	@Override
	public long count() {
		return comparendoRepository.contar();
	}

	/**
	 * Indica si existe un registro con ese identificador.
	 *
	 * @param id el identificador a comprobar
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		return comparendoRepository.existe(id);
	}

	/**
	 * Actualiza un comparendo existente. Valida los datos igual que al crear.
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
	public void updateById(Long id, ComparendoDTO data) {
		if (!comparendoRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El comparendo");
		validar(data);
		Comparendo entity = aEntity(data);
		entity.setId(id);
		comparendoRepository.guardar(entity);
	}
}
