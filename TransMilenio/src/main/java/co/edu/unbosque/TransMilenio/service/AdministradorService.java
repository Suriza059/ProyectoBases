package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.AdministradorDTO;
import co.edu.unbosque.TransMilenio.entity.Administrador;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.AdministradorRepository;
import co.edu.unbosque.TransMilenio.repository.UsuarioRepository;

/**
 * Logica de negocio del administrador: crear, consultar, actualizar y eliminar. Convierte entre
 * AdministradorDTO y la entidad Administrador, valida los datos con LanzadorDeExcepciones y guarda mediante
 * AdministradorRepository.
 *
 * <p>Reglas de validacion:
 * <ul>
 * <li>Unicidad: Ese usuario ya es administrador</li>
 * <li>Relacion: El usuario no existe</li>
 * <li>Dato obligatorio: El campo idUsuario es obligatorio</li>
 * </ul>
 */
public class AdministradorService implements CRUDOPERATION<AdministradorDTO> {

	/**
	 * Repositorio del administrador que usa esta clase.
	 */
	private AdministradorRepository administradorRepository;
	/**
	 * Repositorio del usuario que usa esta clase.
	 */
	private UsuarioRepository usuarioRepository;

	/**
	 * Crea el servicio del administrador.
	 *
	 * @param administradorRepository el repositorio del administrador
	 * @param usuarioRepository el repositorio del usuario
	 */
	public AdministradorService(AdministradorRepository administradorRepository, UsuarioRepository usuarioRepository) {
		this.administradorRepository = administradorRepository;
		this.usuarioRepository = usuarioRepository;
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
	private void validar(AdministradorDTO data) {
		if (data.getIdUsuario() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idUsuario es obligatorio");
		if (!usuarioRepository.existe(data.getIdUsuario()))
			LanzadorDeExcepciones.relacionNoExiste("El usuario no existe");
	}

	/**
	 * Convierte el DTO en entidad.
	 *
	 * @param dto el DTO con los datos
	 * @return la entidad con los mismos datos
	 */
	private Administrador aEntity(AdministradorDTO dto) {
		Administrador entity = new Administrador();
		entity.setIdUsuario(dto.getIdUsuario());
		entity.setAreaGestion(dto.getAreaGestion());
		entity.setFechaGestion(dto.getFechaGestion());
		return entity;
	}

	/**
	 * Convierte la entidad en DTO.
	 *
	 * @param entity la entidad a convertir
	 * @return el DTO con los mismos datos
	 */
	private AdministradorDTO aDTO(Administrador entity) {
		AdministradorDTO dto = new AdministradorDTO();
		dto.setIdUsuario(entity.getIdUsuario());
		dto.setAreaGestion(entity.getAreaGestion());
		dto.setFechaGestion(entity.getFechaGestion());
		return dto;
	}

	/**
	 * Crea un administrador nuevo para un usuario. Valida los datos y que ese usuario no tenga ya el
	 * registro.
	 *
	 * @param data los datos del administrador a crear
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroDuplicadoException si ya existe un registro
	 *     con los mismos datos unicos
	 * @throws co.edu.unbosque.TransMilenio.exception.DatoInvalidoException si falta un dato obligatorio o
	 *     un dato no es valido
	 * @throws co.edu.unbosque.TransMilenio.exception.RelacionNoExisteException si una llave foranea apunta
	 *     a un registro que no existe
	 */
	@Override
	public void create(AdministradorDTO data) {
		validar(data);
		if (administradorRepository.existe(data.getIdUsuario()))
			LanzadorDeExcepciones.registroDuplicado("Ese usuario ya es administrador");
		Administrador entity = aEntity(data);
		administradorRepository.guardar(entity);
	}

	/**
	 * Obtiene todos los registros como DTO.
	 *
	 * @return la lista de DTO, vacia si no hay registros
	 */
	@Override
	public List<AdministradorDTO> getAll() {
		List<AdministradorDTO> dtoList = new ArrayList<>();
		for (Administrador e : administradorRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	/**
	 * Elimina un administrador por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException si el registro no existe
	 */
	@Override
	public void deleteById(Long id) {
		if (!administradorRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El administrador");
		administradorRepository.eliminar(id);
	}

	/**
	 * Cuenta los registros.
	 *
	 * @return la cantidad de registros
	 */
	@Override
	public long count() {
		return administradorRepository.contar();
	}

	/**
	 * Indica si existe un registro con ese identificador.
	 *
	 * @param id el identificador a comprobar
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		return administradorRepository.existe(id);
	}

	/**
	 * Actualiza un administrador existente. Valida los datos igual que al crear.
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
	public void updateById(Long id, AdministradorDTO data) {
		if (!administradorRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El administrador");
		data.setIdUsuario(id);
		validar(data);
		Administrador entity = aEntity(data);
		entity.setIdUsuario(id);
		administradorRepository.guardar(entity);
	}
}
