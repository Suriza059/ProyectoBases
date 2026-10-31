package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.UsuarioDTO;
import co.edu.unbosque.TransMilenio.entity.Usuario;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.UsuarioRepository;

public class UsuarioService implements CRUDOPERATION<UsuarioDTO> {

	private UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	private Usuario aEntity(UsuarioDTO dto) {
		Usuario entity = new Usuario();
		entity.setId(dto.getId());
		entity.setRol(dto.getRol());
		return entity;
	}

	private UsuarioDTO aDTO(Usuario entity) {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setId(entity.getId());
		dto.setRol(entity.getRol());
		return dto;
	}

	@Override
	public void create(UsuarioDTO data) {
		Usuario entity = aEntity(data);
		entity.setId(null);
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
		if (!usuarioRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El usuario");
		Usuario entity = aEntity(data);
		entity.setId(id);
		usuarioRepository.guardar(entity);
	}
}
