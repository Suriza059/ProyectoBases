package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.TarifaDTO;
import co.edu.unbosque.TransMilenio.entity.Tarifa;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.TarifaRepository;

public class TarifaService implements CRUDOPERATION<TarifaDTO> {

	private TarifaRepository tarifaRepository;

	public TarifaService(TarifaRepository tarifaRepository) {
		this.tarifaRepository = tarifaRepository;
	}

	private Tarifa aEntity(TarifaDTO dto) {
		Tarifa entity = new Tarifa();
		entity.setId(dto.getId());
		entity.setFechaInicio(dto.getFechaInicio());
		entity.setFechaFin(dto.getFechaFin());
		entity.setValor(dto.getValor());
		return entity;
	}

	private TarifaDTO aDTO(Tarifa entity) {
		TarifaDTO dto = new TarifaDTO();
		dto.setId(entity.getId());
		dto.setFechaInicio(entity.getFechaInicio());
		dto.setFechaFin(entity.getFechaFin());
		dto.setValor(entity.getValor());
		return dto;
	}

	@Override
	public void create(TarifaDTO data) {
		Tarifa entity = aEntity(data);
		entity.setId(null);
		tarifaRepository.guardar(entity);
	}

	@Override
	public List<TarifaDTO> getAll() {
		List<TarifaDTO> dtoList = new ArrayList<>();
		for (Tarifa e : tarifaRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	@Override
	public void deleteById(Long id) {
		if (!tarifaRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("La tarifa");
		tarifaRepository.eliminar(id);
	}

	@Override
	public long count() {
		return tarifaRepository.contar();
	}

	@Override
	public boolean exist(Long id) {
		return tarifaRepository.existe(id);
	}

	@Override
	public void updateById(Long id, TarifaDTO data) {
		if (!tarifaRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("La tarifa");
		Tarifa entity = aEntity(data);
		entity.setId(id);
		tarifaRepository.guardar(entity);
	}
}
