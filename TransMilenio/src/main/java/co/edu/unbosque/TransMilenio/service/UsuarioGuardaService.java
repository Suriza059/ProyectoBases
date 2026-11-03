package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.UsuarioGuardaDTO;
import co.edu.unbosque.TransMilenio.entity.UsuarioGuarda;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.GuardaRepository;
import co.edu.unbosque.TransMilenio.repository.UsuarioGuardaRepository;
import co.edu.unbosque.TransMilenio.repository.UsuarioRepository;

/**
 * Logica de negocio de la activacion de guarda: crear, consultar, actualizar y eliminar. Convierte entre
 * UsuarioGuardaDTO y la entidad UsuarioGuarda, valida los datos con LanzadorDeExcepciones y guarda mediante
 * UsuarioGuardaRepository.
 *
 * <p>Reglas de validacion:
 * <ul>
 * <li>Unicidad: Ese usuario ya tiene un guarda activado</li>
 * <li>Relacion: El usuario no existe</li>
 * <li>Relacion: El guarda no existe</li>
 * <li>Dato obligatorio: El campo idUsuario es obligatorio</li>
 * <li>Dato obligatorio: El campo idGuarda es obligatorio</li>
 * </ul>
 */
public class UsuarioGuardaService implements CRUDOPERATION<UsuarioGuardaDTO> {

	/**
	 * Repositorio de la activacion de guarda que usa esta clase.
	 */
	private UsuarioGuardaRepository usuarioGuardaRepository;
	/**
	 * Repositorio del usuario que usa esta clase.
	 */
	private UsuarioRepository usuarioRepository;
	/**
	 * Repositorio del guarda que usa esta clase.
	 */
	private GuardaRepository guardaRepository;

	/**
	 * Crea el servicio de la activacion de guarda.
	 *
	 * @param usuarioGuardaRepository el repositorio de la activacion de guarda
	 * @param usuarioRepository el repositorio del usuario
	 * @param guardaRepository el repositorio del guarda
	 */
	public UsuarioGuardaService(UsuarioGuardaRepository usuarioGuardaRepository, UsuarioRepository usuarioRepository, GuardaRepository guardaRepository) {
		this.usuarioGuardaRepository = usuarioGuardaRepository;
		this.usuarioRepository = usuarioRepository;
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
	private void validar(UsuarioGuardaDTO data) {
		if (data.getIdUsuario() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idUsuario es obligatorio");
		if (!usuarioRepository.existe(data.getIdUsuario()))
			LanzadorDeExcepciones.relacionNoExiste("El usuario no existe");
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
	private UsuarioGuarda aEntity(UsuarioGuardaDTO dto) {
		UsuarioGuarda entity = new UsuarioGuarda();
		entity.setIdUsuario(dto.getIdUsuario());
		entity.setFechaActivacion(dto.getFechaActivacion());
		entity.setIdGuarda(dto.getIdGuarda());
		return entity;
	}

	/**
	 * Convierte la entidad en DTO.
	 *
	 * @param entity la entidad a convertir
	 * @return el DTO con los mismos datos
	 */
	private UsuarioGuardaDTO aDTO(UsuarioGuarda entity) {
		UsuarioGuardaDTO dto = new UsuarioGuardaDTO();
		dto.setIdUsuario(entity.getIdUsuario());
		dto.setFechaActivacion(entity.getFechaActivacion());
		dto.setIdGuarda(entity.getIdGuarda());
		return dto;
	}

	/**
	 * Crea una activacion de guarda nueva para un usuario. Valida los datos y que ese usuario no tenga ya
	 * el registro.
	 *
	 * @param data los datos de la activacion de guarda a crear
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroDuplicadoException si ya existe un registro
	 *     con los mismos datos unicos
	 * @throws co.edu.unbosque.TransMilenio.exception.DatoInvalidoException si falta un dato obligatorio o
	 *     un dato no es valido
	 * @throws co.edu.unbosque.TransMilenio.exception.RelacionNoExisteException si una llave foranea apunta
	 *     a un registro que no existe
	 */
	@Override
	public void create(UsuarioGuardaDTO data) {
		validar(data);
		if (usuarioGuardaRepository.existe(data.getIdUsuario()))
			LanzadorDeExcepciones.registroDuplicado("Ese usuario ya tiene un guarda activado");
		UsuarioGuarda entity = aEntity(data);
		usuarioGuardaRepository.guardar(entity);
	}

	/**
	 * Obtiene todos los registros como DTO.
	 *
	 * @return la lista de DTO, vacia si no hay registros
	 */
	@Override
	public List<UsuarioGuardaDTO> getAll() {
		List<UsuarioGuardaDTO> dtoList = new ArrayList<>();
		for (UsuarioGuarda e : usuarioGuardaRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	/**
	 * Elimina una activacion de guarda por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException si el registro no existe
	 */
	@Override
	public void deleteById(Long id) {
		if (!usuarioGuardaRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El guarda activado");
		usuarioGuardaRepository.eliminar(id);
	}

	/**
	 * Cuenta los registros.
	 *
	 * @return la cantidad de registros
	 */
	@Override
	public long count() {
		return usuarioGuardaRepository.contar();
	}

	/**
	 * Indica si existe un registro con ese identificador.
	 *
	 * @param id el identificador a comprobar
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		return usuarioGuardaRepository.existe(id);
	}

	/**
	 * Actualiza una activacion de guarda existente. Valida los datos igual que al crear.
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
	public void updateById(Long id, UsuarioGuardaDTO data) {
		if (!usuarioGuardaRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El guarda activado");
		data.setIdUsuario(id);
		validar(data);
		UsuarioGuarda entity = aEntity(data);
		entity.setIdUsuario(id);
		usuarioGuardaRepository.guardar(entity);
	}
}
