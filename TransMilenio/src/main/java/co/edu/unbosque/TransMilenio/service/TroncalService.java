package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.TroncalDTO;
import co.edu.unbosque.TransMilenio.entity.Troncal;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.TroncalRepository;

public class TroncalService implements CRUDOPERATION<TroncalDTO> {

	private TroncalRepository troncalRepository;

	public TroncalService(TroncalRepository troncalRepository) {
		this.troncalRepository = troncalRepository;
	}

	private Troncal aEntity(TroncalDTO dto) {
		Troncal entity = new Troncal();
		entity.setId(dto.getId());
		entity.setNombre(dto.getNombre());
		return entity;
	}

	private TroncalDTO aDTO(Troncal entity) {
		TroncalDTO dto = new TroncalDTO();
		dto.setId(entity.getId());
		dto.setNombre(entity.getNombre());
		return dto;
	}

	@Override
	public void create(TroncalDTO data) {
		if (troncalRepository.buscarPorNombre(data.getNombre()) != null)
			LanzadorDeExcepciones.registroDuplicado("Ya existe una troncal con ese nombre");
		Troncal entity = aEntity(data);
		entity.setId(null);
		troncalRepository.guardar(entity);
	}

	@Override
	public List<TroncalDTO> getAll() {
		List<TroncalDTO> dtoList = new ArrayList<>();
		for (Troncal e : troncalRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	@Override
	public void deleteById(Long id) {
		if (!troncalRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("La troncal");
		troncalRepository.eliminar(id);
	}

	@Override
	public long count() {
		return troncalRepository.contar();
	}

	@Override
	public boolean exist(Long id) {
		return troncalRepository.existe(id);
	}

	@Override
	public void updateById(Long id, TroncalDTO data) {
		if (!troncalRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("La troncal");
		Troncal otro = troncalRepository.buscarPorNombre(data.getNombre());
		if (otro != null && !otro.getId().equals(id))
			LanzadorDeExcepciones.registroDuplicado("Ya existe una troncal con ese nombre");
		Troncal entity = aEntity(data);
		entity.setId(id);
		troncalRepository.guardar(entity);
	}
}
