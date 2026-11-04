package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.UsuarioDTO;
import co.edu.unbosque.TransMilenio.entity.Usuario;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.UsuarioRepository;
import co.edu.unbosque.TransMilenio.security.PasswordUtil;

/**
 * Servicio de usuarios. Ademas de la logica de negocio comun, cifra la contrasena al guardarla, valida que
 * el rol sea Administrador, Analista o Guarda, impide nombres de usuario repetidos y nunca devuelve la
 * contrasena en los DTO.
 */
public class UsuarioService implements CRUDOPERATION<UsuarioDTO> {

	/**
	 * Repositorio del usuario que usa esta clase.
	 */
	private UsuarioRepository usuarioRepository;

	/**
	 * Crea el servicio del usuario.
	 *
	 * @param usuarioRepository el repositorio del usuario
	 */
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	/**
	 * Crea el primer administrador (usuario admin, contrasena admin123) si todavia no hay ningun usuario.
	 * Sin el, nadie podria iniciar sesion para crear a los demas. Conviene cambiar esa contrasena.
	 */
	public void crearAdministradorInicial() {
		if (usuarioRepository.contar() == 0) {
			UsuarioDTO dto = new UsuarioDTO();
			dto.setRol("Administrador");
			dto.setNombreUsuario("admin");
			dto.setContrasena("admin123");
			create(dto);
		}
	}

	/**
	 * Comprueba que el rol sea Administrador, Analista o Guarda.
	 *
	 * @param rol el rol a validar
	 * @throws co.edu.unbosque.TransMilenio.exception.DatoInvalidoException si falta un dato obligatorio o
	 *     un dato no es valido
	 */
	private void validarRol(String rol) {
		if (rol == null || !(rol.equals("Administrador") || rol.equals("Analista") || rol.equals("Guarda")))
			LanzadorDeExcepciones.datoInvalido("El rol debe ser Administrador, Analista o Guarda");
	}

	/**
	 * Comprueba que el nombre de usuario no este vacio.
	 *
	 * @param nombreUsuario el nombre de usuario a validar
	 * @throws co.edu.unbosque.TransMilenio.exception.DatoInvalidoException si falta un dato obligatorio o
	 *     un dato no es valido
	 */
	private void validarNombre(String nombreUsuario) {
		if (nombreUsuario == null || nombreUsuario.isBlank())
			LanzadorDeExcepciones.datoInvalido("El campo nombreUsuario es obligatorio");
	}

	/**
	 * Convierte la entidad en DTO. No copia la contrasena, para que nunca se devuelva.
	 *
	 * @param entity la entidad a convertir
	 * @return el DTO sin contrasena
	 */
	private UsuarioDTO aDTO(Usuario entity) {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setId(entity.getId());
		dto.setRol(entity.getRol());
		dto.setNombreUsuario(entity.getNombreUsuario());
		return dto;
	}

	/**
	 * Crea un usuario. Valida nombre, contrasena y rol, impide nombres repetidos y guarda la contrasena
	 * cifrada.
	 *
	 * @param data los datos del usuario (nombreUsuario, contrasena y rol)
	 * @throws co.edu.unbosque.TransMilenio.exception.DatoInvalidoException si falta un dato obligatorio o
	 *     un dato no es valido
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroDuplicadoException si ya existe un registro
	 *     con los mismos datos unicos
	 */
	@Override
	public void create(UsuarioDTO data) {
		validarNombre(data.getNombreUsuario());
		if (data.getContrasena() == null || data.getContrasena().isBlank())
			LanzadorDeExcepciones.datoInvalido("El campo contrasena es obligatorio");
		validarRol(data.getRol());
		if (usuarioRepository.buscarPorNombreUsuario(data.getNombreUsuario()) != null)
			LanzadorDeExcepciones.registroDuplicado("Ya existe un usuario con ese nombre");
		Usuario entity = new Usuario();
		entity.setRol(data.getRol());
		entity.setNombreUsuario(data.getNombreUsuario());
		entity.setContrasena(PasswordUtil.encriptar(data.getContrasena()));
		usuarioRepository.guardar(entity);
	}

	/**
	 * Obtiene todos los registros como DTO.
	 *
	 * @return la lista de DTO, vacia si no hay registros
	 */
	@Override
	public List<UsuarioDTO> getAll() {
		List<UsuarioDTO> dtoList = new ArrayList<>();
		for (Usuario e : usuarioRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	/**
	 * Elimina un usuario por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException si el registro no existe
	 */
	@Override
	public void deleteById(Long id) {
		if (!usuarioRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El usuario");
		usuarioRepository.eliminar(id);
	}

	/**
	 * Cuenta los registros.
	 *
	 * @return la cantidad de registros
	 */
	@Override
	public long count() {
		return usuarioRepository.contar();
	}

	/**
	 * Indica si existe un registro con ese identificador.
	 *
	 * @param id el identificador a comprobar
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		return usuarioRepository.existe(id);
	}

	/**
	 * Actualiza un usuario. Si no se envia una contrasena nueva, se conserva la anterior.
	 *
	 * @param id el identificador del usuario
	 * @param data los datos nuevos del usuario
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException si el registro no existe
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroDuplicadoException si ya existe un registro
	 *     con los mismos datos unicos
	 */
	@Override
	public void updateById(Long id, UsuarioDTO data) {
		Usuario actual = usuarioRepository.buscarPorId(id);
		if (actual == null)
			LanzadorDeExcepciones.registroNoExiste("El usuario");
		validarNombre(data.getNombreUsuario());
		validarRol(data.getRol());
		Usuario otro = usuarioRepository.buscarPorNombreUsuario(data.getNombreUsuario());
		if (otro != null && !otro.getId().equals(id))
			LanzadorDeExcepciones.registroDuplicado("Ya existe un usuario con ese nombre");
		Usuario entity = new Usuario();
		entity.setId(id);
		entity.setRol(data.getRol());
		entity.setNombreUsuario(data.getNombreUsuario());
		if (data.getContrasena() == null || data.getContrasena().isBlank()) {
			entity.setContrasena(actual.getContrasena());
		} else {
			entity.setContrasena(PasswordUtil.encriptar(data.getContrasena()));
		}
		usuarioRepository.guardar(entity);
	}
}
