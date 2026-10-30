package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.AnalistaDTO;
import co.edu.unbosque.TransMilenio.entity.Analista;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.AnalistaRepository;
import co.edu.unbosque.TransMilenio.repository.UsuarioRepository;

public class AnalistaService implements CRUDOPERATION<AnalistaDTO> {

	private AnalistaRepository analistaRepository;
	private UsuarioRepository usuarioRepository;

	public AnalistaService(AnalistaRepository analistaRepository, UsuarioRepository usuarioRepository) {
		this.analistaRepository = analistaRepository;
		this.usuarioRepository = usuarioRepository;
	}

	private void validar(AnalistaDTO data) {
		if (data.getIdUsuario() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idUsuario es obligatorio");
		if (!usuarioRepository.existe(data.getIdUsuario()))
			LanzadorDeExcepciones.relacionNoExiste("El usuario no existe");
	}

	private Analista aEntity(AnalistaDTO dto) {
		Analista entity = new Analista();
		entity.setId(dto.getId());
		entity.setAreaAnalisis(dto.getAreaAnalisis());
		entity.setIdUsuario(dto.getIdUsuario());
		return entity;
	}

	private AnalistaDTO aDTO(Analista entity) {
		AnalistaDTO dto = new AnalistaDTO();
		dto.setId(entity.getId());
		dto.setAreaAnalisis(entity.getAreaAnalisis());
		dto.setIdUsuario(entity.getIdUsuario());
		return dto;
	}

	@Override
	public void create(AnalistaDTO data) {
		validar(data);
		Analista entity = aEntity(data);
		entity.setId(null);
		analistaRepository.guardar(entity);
	}

	@Override
	public List<AnalistaDTO> getAll() {
		List<AnalistaDTO> dtoList = new ArrayList<>();
		for (Analista e : analistaRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	@Override
	public void deleteById(Long id) {
		if (!analistaRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El analista");
		analistaRepository.eliminar(id);
	}

	@Override
	public long count() {
		return analistaRepository.contar();
	}

	@Override
	public boolean exist(Long id) {
		return analistaRepository.existe(id);
	}

	@Override
	public void updateById(Long id, AnalistaDTO data) {
		if (!analistaRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El analista");
		validar(data);
		Analista entity = aEntity(data);
		entity.setId(id);
		analistaRepository.guardar(entity);
	}
}
