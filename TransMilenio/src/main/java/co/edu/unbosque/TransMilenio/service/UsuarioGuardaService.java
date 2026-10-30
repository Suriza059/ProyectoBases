package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.UsuarioGuardaDTO;
import co.edu.unbosque.TransMilenio.entity.UsuarioGuarda;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.GuardaRepository;
import co.edu.unbosque.TransMilenio.repository.UsuarioGuardaRepository;
import co.edu.unbosque.TransMilenio.repository.UsuarioRepository;

public class UsuarioGuardaService implements CRUDOPERATION<UsuarioGuardaDTO> {

	private UsuarioGuardaRepository usuarioGuardaRepository;
	private UsuarioRepository usuarioRepository;
	private GuardaRepository guardaRepository;

	public UsuarioGuardaService(UsuarioGuardaRepository usuarioGuardaRepository, UsuarioRepository usuarioRepository, GuardaRepository guardaRepository) {
		this.usuarioGuardaRepository = usuarioGuardaRepository;
		this.usuarioRepository = usuarioRepository;
		this.guardaRepository = guardaRepository;
	}

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

	private UsuarioGuarda aEntity(UsuarioGuardaDTO dto) {
		UsuarioGuarda entity = new UsuarioGuarda();
		entity.setIdUsuario(dto.getIdUsuario());
		entity.setFechaActivacion(dto.getFechaActivacion());
		entity.setIdGuarda(dto.getIdGuarda());
		return entity;
	}

	private UsuarioGuardaDTO aDTO(UsuarioGuarda entity) {
		UsuarioGuardaDTO dto = new UsuarioGuardaDTO();
		dto.setIdUsuario(entity.getIdUsuario());
		dto.setFechaActivacion(entity.getFechaActivacion());
		dto.setIdGuarda(entity.getIdGuarda());
		return dto;
	}

	@Override
	public void create(UsuarioGuardaDTO data) {
		validar(data);
		if (usuarioGuardaRepository.existe(data.getIdUsuario()))
			LanzadorDeExcepciones.registroDuplicado("Ese usuario ya tiene un guarda activado");
		UsuarioGuarda entity = aEntity(data);
		usuarioGuardaRepository.guardar(entity);
	}

	@Override
	public List<UsuarioGuardaDTO> getAll() {
		List<UsuarioGuardaDTO> dtoList = new ArrayList<>();
		for (UsuarioGuarda e : usuarioGuardaRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	@Override
	public void deleteById(Long id) {
		if (!usuarioGuardaRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El guarda activado");
		usuarioGuardaRepository.eliminar(id);
	}

	@Override
	public long count() {
		return usuarioGuardaRepository.contar();
	}

	@Override
	public boolean exist(Long id) {
		return usuarioGuardaRepository.existe(id);
	}

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
