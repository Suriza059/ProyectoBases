package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.AgresionDTO;
import co.edu.unbosque.TransMilenio.entity.Agresion;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.AgresionRepository;
import co.edu.unbosque.TransMilenio.repository.EventoRepository;
import co.edu.unbosque.TransMilenio.repository.GuardaRepository;

public class AgresionService implements CRUDOPERATION<AgresionDTO> {

	private AgresionRepository agresionRepository;
	private GuardaRepository guardaRepository;
	private EventoRepository eventoRepository;

	public AgresionService(AgresionRepository agresionRepository, GuardaRepository guardaRepository, EventoRepository eventoRepository) {
		this.agresionRepository = agresionRepository;
		this.guardaRepository = guardaRepository;
		this.eventoRepository = eventoRepository;
	}

	private void validar(AgresionDTO data) {
		if (data.getIdGuarda() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idGuarda es obligatorio");
		if (!guardaRepository.existe(data.getIdGuarda()))
			LanzadorDeExcepciones.relacionNoExiste("El guarda no existe");
		if (data.getIdEvento() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idEvento es obligatorio");
		if (!eventoRepository.existe(data.getIdEvento()))
			LanzadorDeExcepciones.relacionNoExiste("El evento no existe");
	}

	private Agresion aEntity(AgresionDTO dto) {
		Agresion entity = new Agresion();
		entity.setId(dto.getId());
		entity.setFecha(dto.getFecha());
		entity.setGravedad(dto.getGravedad());
		entity.setDescripcion(dto.getDescripcion());
		entity.setIdGuarda(dto.getIdGuarda());
		entity.setIdEvento(dto.getIdEvento());
		return entity;
	}

	private AgresionDTO aDTO(Agresion entity) {
		AgresionDTO dto = new AgresionDTO();
		dto.setId(entity.getId());
		dto.setFecha(entity.getFecha());
		dto.setGravedad(entity.getGravedad());
		dto.setDescripcion(entity.getDescripcion());
		dto.setIdGuarda(entity.getIdGuarda());
		dto.setIdEvento(entity.getIdEvento());
		return dto;
	}

	@Override
	public void create(AgresionDTO data) {
		validar(data);
		Agresion entity = aEntity(data);
		entity.setId(null);
		agresionRepository.guardar(entity);
	}

	@Override
	public List<AgresionDTO> getAll() {
		List<AgresionDTO> dtoList = new ArrayList<>();
		for (Agresion e : agresionRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	@Override
	public void deleteById(Long id) {
		if (!agresionRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("La agresion");
		agresionRepository.eliminar(id);
	}

	@Override
	public long count() {
		return agresionRepository.contar();
	}

	@Override
	public boolean exist(Long id) {
		return agresionRepository.existe(id);
	}

	@Override
	public void updateById(Long id, AgresionDTO data) {
		if (!agresionRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("La agresion");
		validar(data);
		Agresion entity = aEntity(data);
		entity.setId(id);
		agresionRepository.guardar(entity);
	}
}
