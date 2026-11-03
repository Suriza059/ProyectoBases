package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.UsuarioDTO;
import co.edu.unbosque.TransMilenio.entity.Usuario;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.UsuarioRepository;
import co.edu.unbosque.TransMilenio.security.PasswordUtil;

public class UsuarioService implements CRUDOPERATION<UsuarioDTO> {

	private UsuarioRepository usuarioRepository;
	private ConfirmacionService confirmacionService;

	public UsuarioService(UsuarioRepository usuarioRepository, ConfirmacionService confirmacionService) {
		this.usuarioRepository = usuarioRepository;
		this.confirmacionService = confirmacionService;
	}

	public void crearAdministradorInicial() {
		if (usuarioRepository.contar() == 0) {
			Usuario admin = new Usuario();
			admin.setRol("Administrador");
			admin.setNombreUsuario("admin");
			admin.setContrasena(PasswordUtil.encriptar("admin123"));
			admin.setCorreoConfirmado("S");
			usuarioRepository.guardar(admin);
		}
	}

	private void validarRol(String rol) {
		if (rol == null || !(rol.equals("Administrador") || rol.equals("Analista") || rol.equals("Guarda")))
			LanzadorDeExcepciones.datoInvalido("El rol debe ser Administrador, Analista o Guarda");
	}

	private void validarNombre(String nombreUsuario) {
		if (nombreUsuario == null || nombreUsuario.isBlank())
			LanzadorDeExcepciones.datoInvalido("El campo nombreUsuario es obligatorio");
	}

	private void validarCorreo(String correo) {
		if (correo == null || correo.isBlank())
			LanzadorDeExcepciones.datoInvalido("El campo correo es obligatorio");
		if (!correo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"))
			LanzadorDeExcepciones.datoInvalido("El correo no tiene un formato valido");
	}

	private UsuarioDTO aDTO(Usuario entity) {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setId(entity.getId());
		dto.setRol(entity.getRol());
		dto.setNombreUsuario(entity.getNombreUsuario());
		dto.setCorreo(entity.getCorreo());
		dto.setCorreoConfirmado(entity.getCorreoConfirmado());
		return dto;
	}

	@Override
	public void create(UsuarioDTO data) {
		validarNombre(data.getNombreUsuario());
		if (data.getContrasena() == null || data.getContrasena().isBlank())
			LanzadorDeExcepciones.datoInvalido("El campo contrasena es obligatorio");
		validarRol(data.getRol());
		validarCorreo(data.getCorreo());
		if (usuarioRepository.buscarPorNombreUsuario(data.getNombreUsuario()) != null)
			LanzadorDeExcepciones.registroDuplicado("Ya existe un usuario con ese nombre");
		if (usuarioRepository.buscarPorCorreo(data.getCorreo()) != null)
			LanzadorDeExcepciones.registroDuplicado("Ya existe un usuario con ese correo");
		Usuario entity = new Usuario();
		entity.setRol(data.getRol());
		entity.setNombreUsuario(data.getNombreUsuario());
		entity.setContrasena(PasswordUtil.encriptar(data.getContrasena()));
		entity.setCorreo(data.getCorreo());
		entity.setCorreoConfirmado("N");
		usuarioRepository.guardar(entity);
		confirmacionService.enviarConfirmacion(entity);
	}

	@Override
	public List<UsuarioDTO> getAll() {
		List<UsuarioDTO> dtoList = new ArrayList<>();
		for (Usuario e : usuarioRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	@Override
	public void deleteById(Long id) {
		if (!usuarioRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El usuario");
		usuarioRepository.eliminar(id);
	}

	@Override
	public long count() {
		return usuarioRepository.contar();
	}

	@Override
	public boolean exist(Long id) {
		return usuarioRepository.existe(id);
	}

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

		boolean correoNuevo = false;
		if (data.getCorreo() == null || data.getCorreo().isBlank()) {
			entity.setCorreo(actual.getCorreo());
			entity.setCorreoConfirmado(actual.getCorreoConfirmado());
		} else {
			validarCorreo(data.getCorreo());
			Usuario otroCorreo = usuarioRepository.buscarPorCorreo(data.getCorreo());
			if (otroCorreo != null && !otroCorreo.getId().equals(id))
				LanzadorDeExcepciones.registroDuplicado("Ya existe un usuario con ese correo");
			entity.setCorreo(data.getCorreo());
			correoNuevo = actual.getCorreo() == null || !actual.getCorreo().equalsIgnoreCase(data.getCorreo());
			entity.setCorreoConfirmado(correoNuevo ? "N" : actual.getCorreoConfirmado());
		}
		usuarioRepository.guardar(entity);
		if (correoNuevo)
			confirmacionService.enviarConfirmacion(entity);
	}
}