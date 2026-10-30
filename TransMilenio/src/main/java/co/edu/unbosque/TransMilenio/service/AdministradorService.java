package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.AdministradorDTO;
import co.edu.unbosque.TransMilenio.entity.Administrador;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.AdministradorRepository;
import co.edu.unbosque.TransMilenio.repository.UsuarioRepository;

public class AdministradorService implements CRUDOPERATION<AdministradorDTO> {

	private AdministradorRepository administradorRepository;
	private UsuarioRepository usuarioRepository;

	public AdministradorService(AdministradorRepository administradorRepository, UsuarioRepository usuarioRepository) {
		this.administradorRepository = administradorRepository;
		this.usuarioRepository = usuarioRepository;
	}

	private void validar(AdministradorDTO data) {
		if (data.getIdUsuario() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idUsuario es obligatorio");
		if (!usuarioRepository.existe(data.getIdUsuario()))
			LanzadorDeExcepciones.relacionNoExiste("El usuario no existe");
	}

	private Administrador aEntity(AdministradorDTO dto) {
		Administrador entity = new Administrador();
		entity.setId(dto.getId());
		entity.setAreaGestion(dto.getAreaGestion());
		entity.setFechaGestion(dto.getFechaGestion());
		entity.setIdUsuario(dto.getIdUsuario());
		return entity;
	}

	private AdministradorDTO aDTO(Administrador entity) {
		AdministradorDTO dto = new AdministradorDTO();
		dto.setId(entity.getId());
		dto.setAreaGestion(entity.getAreaGestion());
		dto.setFechaGestion(entity.getFechaGestion());
		dto.setIdUsuario(entity.getIdUsuario());
		return dto;
	}

	@Override
	public void create(AdministradorDTO data) {
		validar(data);
		Administrador entity = aEntity(data);
		entity.setId(null);
		administradorRepository.guardar(entity);
	}

	@Override
	public List<AdministradorDTO> getAll() {
		List<AdministradorDTO> dtoList = new ArrayList<>();
		for (Administrador e : administradorRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	@Override
	public void deleteById(Long id) {
		if (!administradorRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El administrador");
		administradorRepository.eliminar(id);
	}

	@Override
	public long count() {
		return administradorRepository.contar();
	}

	@Override
	public boolean exist(Long id) {
		return administradorRepository.existe(id);
	}

	@Override
	public void updateById(Long id, AdministradorDTO data) {
		if (!administradorRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El administrador");
		validar(data);
		Administrador entity = aEntity(data);
		entity.setId(id);
		administradorRepository.guardar(entity);
	}
}
