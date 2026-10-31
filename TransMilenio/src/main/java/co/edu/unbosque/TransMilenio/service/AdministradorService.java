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
		entity.setIdUsuario(dto.getIdUsuario());
		entity.setAreaGestion(dto.getAreaGestion());
		entity.setFechaGestion(dto.getFechaGestion());
		return entity;
	}

	private AdministradorDTO aDTO(Administrador entity) {
		AdministradorDTO dto = new AdministradorDTO();
		dto.setIdUsuario(entity.getIdUsuario());
		dto.setAreaGestion(entity.getAreaGestion());
		dto.setFechaGestion(entity.getFechaGestion());
		return dto;
	}

	@Override
	public void create(AdministradorDTO data) {
		validar(data);
		if (administradorRepository.existe(data.getIdUsuario()))
			LanzadorDeExcepciones.registroDuplicado("Ese usuario ya es administrador");
		Administrador entity = aEntity(data);
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
		data.setIdUsuario(id);
		validar(data);
		Administrador entity = aEntity(data);
		entity.setIdUsuario(id);
		administradorRepository.guardar(entity);
	}
}
