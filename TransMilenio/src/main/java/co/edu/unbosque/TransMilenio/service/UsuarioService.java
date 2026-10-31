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

	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	public void crearAdministradorInicial() {
		if (usuarioRepository.contar() == 0) {
			UsuarioDTO dto = new UsuarioDTO();
			dto.setRol("Administrador");
			dto.setNombreUsuario("admin");
			dto.setContrasena("admin123");
			create(dto);
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

	private UsuarioDTO aDTO(Usuario entity) {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setId(entity.getId());
		dto.setRol(entity.getRol());
		dto.setNombreUsuario(entity.getNombreUsuario());
		return dto;
	}

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
		usuarioRepository.guardar(entity);
	}
}
