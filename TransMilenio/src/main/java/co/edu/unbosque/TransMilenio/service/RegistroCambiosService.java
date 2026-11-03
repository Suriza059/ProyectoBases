package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.RegistroCambiosDTO;
import co.edu.unbosque.TransMilenio.entity.RegistroCambios;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.ComparendoRepository;
import co.edu.unbosque.TransMilenio.repository.RegistroCambiosRepository;
import co.edu.unbosque.TransMilenio.repository.UsuarioRepository;

/**
 * Logica de negocio del registro de cambios: crear, consultar, actualizar y eliminar. Convierte entre
 * RegistroCambiosDTO y la entidad RegistroCambios, valida los datos con LanzadorDeExcepciones y guarda
 * mediante RegistroCambiosRepository.
 *
 * <p>Reglas de validacion:
 * <ul>
 * <li>Relacion: El usuario no existe</li>
 * <li>Relacion: El comparendo no existe</li>
 * <li>Dato obligatorio: El campo idUsuario es obligatorio</li>
 * <li>Dato obligatorio: El campo idComparendo es obligatorio</li>
 * </ul>
 */
public class RegistroCambiosService implements CRUDOPERATION<RegistroCambiosDTO> {

	/**
	 * Repositorio del registro de cambios que usa esta clase.
	 */
	private RegistroCambiosRepository registroCambiosRepository;
	/**
	 * Repositorio del usuario que usa esta clase.
	 */
	private UsuarioRepository usuarioRepository;
	/**
	 * Repositorio del comparendo que usa esta clase.
	 */
	private ComparendoRepository comparendoRepository;

	/**
	 * Crea el servicio del registro de cambios.
	 *
	 * @param registroCambiosRepository el repositorio del registro de cambios
	 * @param usuarioRepository el repositorio del usuario
	 * @param comparendoRepository el repositorio del comparendo
	 */
	public RegistroCambiosService(RegistroCambiosRepository registroCambiosRepository, UsuarioRepository usuarioRepository, ComparendoRepository comparendoRepository) {
		this.registroCambiosRepository = registroCambiosRepository;
		this.usuarioRepository = usuarioRepository;
		this.comparendoRepository = comparendoRepository;
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
	private void validar(RegistroCambiosDTO data) {
		if (data.getIdUsuario() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idUsuario es obligatorio");
		if (!usuarioRepository.existe(data.getIdUsuario()))
			LanzadorDeExcepciones.relacionNoExiste("El usuario no existe");
		if (data.getIdComparendo() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idComparendo es obligatorio");
		if (!comparendoRepository.existe(data.getIdComparendo()))
			LanzadorDeExcepciones.relacionNoExiste("El comparendo no existe");
	}

	/**
	 * Convierte el DTO en entidad.
	 *
	 * @param dto el DTO con los datos
	 * @return la entidad con los mismos datos
	 */
	private RegistroCambios aEntity(RegistroCambiosDTO dto) {
		RegistroCambios entity = new RegistroCambios();
		entity.setId(dto.getId());
		entity.setFecha(dto.getFecha());
		entity.setCampoModificado(dto.getCampoModificado());
		entity.setValorAnterior(dto.getValorAnterior());
		entity.setValorNuevo(dto.getValorNuevo());
		entity.setIdUsuario(dto.getIdUsuario());
		entity.setIdComparendo(dto.getIdComparendo());
		return entity;
	}

	/**
	 * Convierte la entidad en DTO.
	 *
	 * @param entity la entidad a convertir
	 * @return el DTO con los mismos datos
	 */
	private RegistroCambiosDTO aDTO(RegistroCambios entity) {
		RegistroCambiosDTO dto = new RegistroCambiosDTO();
		dto.setId(entity.getId());
		dto.setFecha(entity.getFecha());
		dto.setCampoModificado(entity.getCampoModificado());
		dto.setValorAnterior(entity.getValorAnterior());
		dto.setValorNuevo(entity.getValorNuevo());
		dto.setIdUsuario(entity.getIdUsuario());
		dto.setIdComparendo(entity.getIdComparendo());
		return dto;
	}

	/**
	 * Crea un registro de cambios nuevo. Valida los datos y el sistema le asigna el id.
	 *
	 * @param data los datos del registro de cambios a crear
	 * @throws co.edu.unbosque.TransMilenio.exception.DatoInvalidoException si falta un dato obligatorio o
	 *     un dato no es valido
	 * @throws co.edu.unbosque.TransMilenio.exception.RelacionNoExisteException si una llave foranea apunta
	 *     a un registro que no existe
	 */
	@Override
	public void create(RegistroCambiosDTO data) {
		validar(data);
		RegistroCambios entity = aEntity(data);
		entity.setId(null);
		registroCambiosRepository.guardar(entity);
	}

	/**
	 * Obtiene todos los registros como DTO.
	 *
	 * @return la lista de DTO, vacia si no hay registros
	 */
	@Override
	public List<RegistroCambiosDTO> getAll() {
		List<RegistroCambiosDTO> dtoList = new ArrayList<>();
		for (RegistroCambios e : registroCambiosRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	/**
	 * Elimina un registro de cambios por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException si el registro no existe
	 */
	@Override
	public void deleteById(Long id) {
		if (!registroCambiosRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El registro de cambios");
		registroCambiosRepository.eliminar(id);
	}

	/**
	 * Cuenta los registros.
	 *
	 * @return la cantidad de registros
	 */
	@Override
	public long count() {
		return registroCambiosRepository.contar();
	}

	/**
	 * Indica si existe un registro con ese identificador.
	 *
	 * @param id el identificador a comprobar
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		return registroCambiosRepository.existe(id);
	}

	/**
	 * Actualiza un registro de cambios existente. Valida los datos igual que al crear.
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
	public void updateById(Long id, RegistroCambiosDTO data) {
		if (!registroCambiosRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El registro de cambios");
		validar(data);
		RegistroCambios entity = aEntity(data);
		entity.setId(id);
		registroCambiosRepository.guardar(entity);
	}
}
